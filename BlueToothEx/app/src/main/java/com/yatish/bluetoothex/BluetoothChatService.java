package com.yatish.bluetoothex;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by yatish on 19/12/17.
 */

public class BluetoothChatService {

    private static final String TAG = "BluetoothChatService";
    private static final String NAME_SECURE = "BluetoothChatSecure";
    private static final String NAME_INSECURE = "BluetoothChatInsecure";

    // Unique UUID for this application
    private static final UUID MY_UUID_SECURE =
            UUID.fromString("c0d46f24-e562-11e7-80c1-9a214cf093ae");
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("d1383b0c-e562-11e7-80c1-9a214cf093ae");

    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;

    private AcceptThread mSecureAcceptThread;
    private AcceptThread mInsecureAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;

    private int mState;
    private int mNewState;

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device


    public BluetoothChatService(Context context, Handler handler){

        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mHandler = handler;
        mState = STATE_NONE;
        mNewState = mState;
    }

    public synchronized int getState(){

        return mState;
    }

    public synchronized void start() {

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to listen on a BluetoothServerSocket
        if (mSecureAcceptThread == null) {
            mSecureAcceptThread = new AcceptThread(true);
            mSecureAcceptThread.start();
        }
        if (mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread(false);
            mInsecureAcceptThread.start();
        }
        // Update UI title
        updateUserInterfaceTitle();

    }



    public synchronized void connect(BluetoothDevice device, boolean secure) {

        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(device, secure);
        mConnectThread.start();
        // Update UI title
        updateUserInterfaceTitle();
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice
            device, final String socketType) {

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Cancel the accept thread because we only want to connect to one device
        if (mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }
        if (mInsecureAcceptThread != null) {
            mInsecureAcceptThread.cancel();
            mInsecureAcceptThread = null;
        }

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket, socketType);
        mConnectedThread.start();

        Message msg = mHandler.obtainMessage(Constants.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        // Update UI title
        updateUserInterfaceTitle();

    }

    public synchronized void stop() {
        Log.d("", "stop");

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        if (mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }

        if (mInsecureAcceptThread != null) {
            mInsecureAcceptThread.cancel();
            mInsecureAcceptThread = null;
        }
        mState = STATE_NONE;
        // Update UI title
        updateUserInterfaceTitle();
    }

    public void write(byte[] out){
        ConnectedThread r;
        synchronized (this){
            if (mState != STATE_CONNECTED) {
                return;
            }else{
                r = mConnectedThread;
            }

        }

        r.write(out);
    }


    private class AcceptThread extends Thread{

        BluetoothServerSocket mmServerSocket = null;
        private String mSocketType;

        public AcceptThread(boolean secure) {

            BluetoothServerSocket temp = null;

            mSocketType = secure ? "Secure" : "Insecure";

            try{
                if(secure){
                    temp = mAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, MY_UUID_SECURE);
                }else{
                    temp = mAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME_INSECURE,MY_UUID_INSECURE);
                }
            }catch (IOException e){
                Log.e("Error","listening error");
            }

            mmServerSocket = temp;
            mState = STATE_LISTEN;
        }

        @Override
        public void run() {

            BluetoothSocket socket = null;

            while (mState != STATE_CONNECTED ){

                try{
                    socket = mmServerSocket.accept();
                }catch (IOException e){
                    Log.e("Error","listening error");
                    break;
                }

                if(socket != null){
                    synchronized (BluetoothChatService.this){
                        switch (mState){

                            case STATE_LISTEN:

                            case STATE_CONNECTING:

                                connected(socket,socket.getRemoteDevice(),mSocketType);
                                break;
                            case STATE_NONE:
                            case STATE_CONNECTED:

                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    Log.e("", "Could not close unwanted socket", e);
                                }
                                break;



                        }
                    }
                }

            }

        }


        public void cancel(){
            try{
                mmServerSocket.close();
            }catch (IOException e){
                Log.e("Cancel","Error Cancel");
            }
        }
    }


    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private String mSocketType;

        public ConnectThread(BluetoothDevice device, boolean secure) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            mSocketType = secure ? "Secure" : "Insecure";

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                if (secure) {
                    tmp = device.createRfcommSocketToServiceRecord(
                            MY_UUID_SECURE);
                } else {
                    tmp = device.createInsecureRfcommSocketToServiceRecord(
                            MY_UUID_INSECURE);
                }
            } catch (IOException e) {
                Log.e(TAG, "Socket Type: " + mSocketType + "create() failed", e);
            }
            mmSocket = tmp;
            mState = STATE_CONNECTING;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectThread SocketType:" + mSocketType);
            setName("ConnectThread" + mSocketType);

            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() " + mSocketType +
                            " socket during connection failure", e2);
                }
                connectionFailed();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (BluetoothChatService.this) {
                mConnectThread = null;
            }

            // Start the connected thread
            connected(mmSocket, mmDevice, mSocketType);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect " + mSocketType + " socket failed", e);
            }
        }
    }


    private class ConnectedThread extends Thread{
        private final BluetoothSocket mSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket, String socketType) {
            mSocket = socket;
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try{
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            }catch (IOException e){

            }

            mmInStream = inputStream;
            mmOutStream = outputStream;
            mState = STATE_CONNECTED;

        }

        @Override
        public void run() {

            byte[] buffer = new byte[1024];
            int bytes;

            while (mState == STATE_CONNECTED){

                try{

                    bytes = mmInStream.read(buffer);

                    mHandler.obtainMessage(Constants.MESSAGE_READ, bytes, -1 , buffer).sendToTarget();

                }catch (IOException e){
                    Log.e("", "disconnected", e);
                    connectionLost();
                    break;
                }
            }
        }


        public void write(byte[] buffers){

            try{
                mmOutStream.write(buffers);
                mHandler.obtainMessage(Constants.MESSAGE_WRITE, -1, -1 , buffers).sendToTarget();
            }catch (IOException e){
                Log.e("", "Exception during write", e);
            }
        }

        public void cancel(){
            try{
                mSocket.close();
            }catch (IOException e){
                Log.e("", "Exception during ", e);
            }
        }
    }


    public void connectionFailed(){
        Message msg = mHandler.obtainMessage(Constants.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TOAST, "Unable to connect device");
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        mState = STATE_NONE;
        // Update UI title
        updateUserInterfaceTitle();

        // Start the service over to restart listening mode
        BluetoothChatService.this.start();
    }

    public void connectionLost(){
        Message msg = mHandler.obtainMessage(Constants.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TOAST, "Device connection was lost");
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        mState = STATE_NONE;
        // Update UI title
        updateUserInterfaceTitle();

        // Start the service over to restart listening mode
        BluetoothChatService.this.start();
    }

    private synchronized void updateUserInterfaceTitle() {
        mState = getState();
        Log.d("", "updateUserInterfaceTitle() " + mNewState + " -> " + mState);
        mNewState = mState;

        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(Constants.MESSAGE_STATE_CHANGE, mNewState, -1).sendToTarget();
    }


}
