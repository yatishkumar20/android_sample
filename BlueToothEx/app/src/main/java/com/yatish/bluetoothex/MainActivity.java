package com.yatish.bluetoothex;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private BluetoothAdapter mBluetoothAdapter;

    private ListView mConversationView;
    private EditText mOutEditText;
    private Button mSend, mSendFile;

    private String mConnectedDeviceName = null;

    private ArrayAdapter<String> mConversationArrayAdapter;

    private BluetoothChatService mChatService = null;

    private StringBuffer mOutStringBuffer;
    private static final int PICKFILE_RESULT_CODE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mConversationView = (ListView) findViewById(R.id.in);
        mOutEditText = (EditText) findViewById(R.id.edit_text_out);
        mSend = (Button) findViewById(R.id.button_send);
        mSendFile = (Button) findViewById(R.id.button_send_file);

       /* mSendFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/png");
                startActivityForResult(intent,PICKFILE_RESULT_CODE);

            }
        });*/
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!mBluetoothAdapter.isEnabled()){

            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT,REQUEST_ENABLE_BT);

        }else if (mChatService == null) {
            setupChat();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    public void setupChat(){

        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);

        mConversationView.setAdapter(mConversationArrayAdapter);

        mOutEditText.setOnEditorActionListener(mWriteListener);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView textView = (TextView) v.findViewById(R.id.edit_text_out);
                String message = mOutEditText.getText().toString();
                sendMessage(message);

            }
        });

        mChatService = new BluetoothChatService(this, mHandler);

        mOutStringBuffer = new StringBuffer("");


    }


    public void sendMessage(String message){

        if(mChatService.getState() != BluetoothChatService.STATE_CONNECTED){
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        if (message.length() > 0){
            byte[] send = message.getBytes();
            mChatService.write(send);
            mOutStringBuffer.setLength(0);

            mOutEditText.setText(mOutStringBuffer);
        }

    }

    private TextView.OnEditorActionListener mWriteListener
            = new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            // If the action is a key-up event on the return key, send the message
            if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                String message = view.getText().toString();
                sendMessage(message);
            }
            return true;
        }
    };

    private final Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case Constants.MESSAGE_STATE_CHANGE :
                    switch (msg.arg1){
                        case BluetoothChatService.STATE_NONE :
                            setStatus(R.string.title_not_connected);
                            break;

                        case BluetoothChatService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            mConversationArrayAdapter.clear();
                            break;

                        case BluetoothChatService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;

                        case BluetoothChatService.STATE_LISTEN:
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != this) {
                        Toast.makeText(MainActivity.this, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != this) {
                        Toast.makeText(MainActivity.this, msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;


            }

        }
    };

    private void setStatus(int resId) {
        if (null == this) {
            return;
        }
        final ActionBar actionBar = this.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(resId);
    }

    private void setStatus(CharSequence subTitle) {
        if (null == this) {
            return;
        }
        final ActionBar actionBar = this.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){

            case REQUEST_CONNECT_DEVICE_SECURE :
                if (resultCode == RESULT_OK) {
                    connectDevice(data, true);
                }

                break;

            case REQUEST_CONNECT_DEVICE_INSECURE :
                if (resultCode == RESULT_OK) {
                    connectDevice(data, false);
                }
                break;

            case REQUEST_ENABLE_BT :

                if(resultCode == RESULT_OK){

                    setupChat();

                }else{

                    Toast.makeText(this,"Bluetooth is not enabled", Toast.LENGTH_SHORT).show();
                }

                break;

            /*case PICKFILE_RESULT_CODE :

                String path = data.getData().getPath().toString();
                mOutEditText.setText(path);


                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.setType("image/png");
                File file = new File(path);

                i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

                PackageManager pm = getPackageManager();
                List<ResolveInfo> list = pm.queryIntentActivities(i, 0);
                if (list.size() > 0) {
                    String packageName = null;
                    String className = null;
                    boolean found = false;

                    for (ResolveInfo info : list) {
                        packageName = info.activityInfo.packageName;
                        if (packageName.equals("com.android.bluetooth")) {
                            className = info.activityInfo.name;
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        Toast.makeText(this, "Bluetooth not been found", Toast.LENGTH_LONG).show();
                    } else {
                        i.setClassName(packageName, className);
                        startActivity(i);
                    }
                }

                    break;*/



        }
    }

    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bluetooth_chat,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.secure_connect_scan :
                Intent securecon = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivityForResult(securecon,REQUEST_CONNECT_DEVICE_SECURE);
                return true;

            case R.id.insecure_connect_scan :
                Intent insecurecon = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivityForResult(insecurecon, REQUEST_CONNECT_DEVICE_INSECURE);
                return true;

            case R.id.discoverable :
                ensureDiscoverable();
                return true;

            default:

                return super.onOptionsItemSelected(item);


        }

    }

    public void ensureDiscoverable(){

        if(mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverable);
        }
    }
}
