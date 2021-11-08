package com.nomad.traficlightarduino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.UUID;

public class SubActivity extends AppCompatActivity {

    private final String TAG = SubActivity.class.getSimpleName();

    private String deviceName;
    private String macAddress;
    public static final String DEVICE_NAME = "intent_device_name";
    public static final String MAC_ADDRESS = "intent_mac_address";
    public final static int MESSAGE_READ = 2;
    private final static int CONNECTING_STATUS = 3;
    private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-10805F9B34FB"); // "random" unique identifier

    private BluetoothAdapter bluetoothAdapter;
    private Handler mHandler; // Our main handler that will receive callback notifications
    private ConnectedThread mConnectedThread; // bluetooth background worker thread to send and receive data
    private BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        if (getIntent() != null) {
            deviceName = getIntent().getStringExtra(DEVICE_NAME);
            macAddress = getIntent().getStringExtra(MAC_ADDRESS);
        } else {
            finish();
        }

        // Ask for location permission if not already allowed
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);


        bluetoothAdapter =  BluetoothAdapter.getDefaultAdapter();

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_READ) {
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    // todo
                    Log.d(TAG, "readMessage : " + readMessage);
                }
                // todo
                if (msg.what == CONNECTING_STATUS) {
                    if (msg.arg1 == 1) {
                        Log.d(TAG, "Connected to Device: " + msg.obj);
                    } else {
                        Log.d(TAG, "Connection Failed");
                    }
                }
            }
        };


        final BroadcastReceiver blReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // add the name to the list
                    Log.d(TAG, device.getName() + "\n" + device.getAddress());
//                    mBTArrayAdapter.notifyDataSetChanged();
                }
            }
        };

        registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean fail = false;

                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(macAddress);


                try {
                    mBTSocket = createBluetoothSocket(device);
                } catch (IOException e) {
                    fail = true;
                    e.printStackTrace();
                }

                try {
                    mBTSocket.connect();
                } catch (IOException e) {
                    try {
                        fail = true;
                        mBTSocket.close();
                        mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                .sendToTarget();
                    } catch (IOException e2) {
                        //insert code to deal with this
                        Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                }

                if(!fail) {
                    mConnectedThread = new ConnectedThread(mBTSocket, mHandler);
                    mConnectedThread.start();

                    mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, deviceName)
                            .sendToTarget();
                }
            }
        }).start();

    } // end of onCreate


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        if (requestCode == MainActivity.REQUEST_ENABLE_BT) {
            if (resultCode != RESULT_OK) {
                finish();
            }
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BT_MODULE_UUID);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
        }
        return  device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }

}