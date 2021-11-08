package com.nomad.traficlightarduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();
    private BluetoothAdapter bluetoothAdapter;
    private final ArrayList<DeviceInfo> bluetoothList = new ArrayList<>();
    public final static int REQUEST_ENABLE_BT = 10011;
    private ListAdapter listAdapter;
    private Button btnOn;
    private Button btnOff;
    private RecyclerView recyclerView;

    final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                bluetoothList.add(new DeviceInfo(device.getName(), device.getAddress()));
                listAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        setLayout();
        checkBluetoothPermission();
        discover();
        listPairedDevices();
        addEventListener();
    }

    private void setLayout() {

    }

    private void addEventListener() {
        btnOn.setOnClickListener(view -> {
            bluetoothOn();
        });
        btnOff.setOnClickListener(view -> {
            bluetoothOff();
            listPairedDevices();
        });
        listAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String deviceName, String macAddress) {
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                intent.putExtra(SubActivity.DEVICE_NAME, deviceName);
                intent.putExtra(SubActivity.MAC_ADDRESS, macAddress);
                startActivity(intent);
            }
        });

    }

    private void initData() {
        btnOn = findViewById(R.id.btn_on);
        btnOff = findViewById(R.id.btn_off);
        recyclerView = findViewById(R.id.recycler_view);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void checkBluetoothPermission() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            Toast.makeText(getApplicationContext(), "Bluetooth turned on", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth is already on", Toast.LENGTH_SHORT).show();
        }
    }

    private void bluetoothOn() {
        if (!bluetoothAdapter.isEnabled()) {
            checkBluetoothPermission();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth is already on", Toast.LENGTH_SHORT).show();
        }
    }

    private void bluetoothOff() {
        bluetoothAdapter.disable(); // turn off
        Toast.makeText(getApplicationContext(), "Bluetooth turned Off", Toast.LENGTH_SHORT).show();
    }

    private void listPairedDevices() {
        bluetoothList.clear();
        // 페어링된 기기 확인
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (bluetoothAdapter.isEnabled()) {
            for (BluetoothDevice device : pairedDevices) {
                bluetoothList.add(new DeviceInfo(device.getName(), device.getAddress()));
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        listAdapter = new ListAdapter();
        listAdapter.setData(bluetoothList);
        recyclerView.setAdapter(listAdapter);
    }

    private void discover(){
        // Check if the device is already discovering
        if(bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
            Toast.makeText(getApplicationContext(),"Discovery stopped",Toast.LENGTH_SHORT).show();
        }
        else{
            if(bluetoothAdapter.isEnabled()) {
                bluetoothList.clear(); // clear items
                bluetoothAdapter.startDiscovery();
                Toast.makeText(getApplicationContext(), "Discovery started", Toast.LENGTH_SHORT).show();
                registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            }
            else{
                Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Bluetooth 활성화 됨", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Bluetooth 비 활성화", Toast.LENGTH_SHORT).show();
            }
            listPairedDevices();
        }
    }
}