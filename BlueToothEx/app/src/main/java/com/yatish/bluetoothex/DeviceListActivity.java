package com.yatish.bluetoothex;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

public class DeviceListActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;

    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doDiscovery();
                v.setVisibility(View.GONE);

            }
        });

        ArrayAdapter<String> pairedDeviceAdapter = new ArrayAdapter<String>(this, R.layout.message);

        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,R.layout.message);

        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(pairedDeviceAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        ListView newDevices = (ListView) findViewById(R.id.new_devices);
        newDevices.setAdapter(mNewDevicesArrayAdapter);
        newDevices.setOnItemClickListener(mDeviceClickListener);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevice = mBluetoothAdapter.getBondedDevices();

        if(pairedDevice.size()>0){

            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for(BluetoothDevice device : pairedDevice) {
                pairedDeviceAdapter.add(device.getName()+"\n"+device.getAddress());
            }

        }else{
            String nodevice = getResources().getString(R.string.none_found).toString();
            pairedDeviceAdapter.add(nodevice);
        }

    }


    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            mBluetoothAdapter.cancelDiscovery();

            String info = ((TextView) view).getText().toString();
            String address = info.substring(info.length() - 17);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS,address);

            setResult(RESULT_OK, intent);
            finish();

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mBluetoothAdapter != null){
            mBluetoothAdapter.cancelDiscovery();
        }

        this.unregisterReceiver(mReceiver);
    }

    public void doDiscovery(){

        setTitle(R.string.scanning);

        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        if(mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
        }

        mBluetoothAdapter.startDiscovery();

    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if(BluetoothDevice.ACTION_FOUND.equals(action)){

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if(device.getBondState() != BluetoothDevice.BOND_BONDED){

                    mNewDevicesArrayAdapter.add(device.getName()+"\n"+device.getAddress());

                }

            }else  if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){

                setTitle(R.string.select_device);

                if(mNewDevicesArrayAdapter.getCount() == 0){
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    mNewDevicesArrayAdapter.add(noDevices);
                }

            }

        }
    };
}
