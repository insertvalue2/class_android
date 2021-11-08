package com.nomad.traficlightarduino;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity2 extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();
    private BluetoothAdapter bluetoothAdapter;
    private Map<String, String> bluetoothMap;
    private final static int REQUEST_ENABLE_BT = 10011; // 블루투스 권한 요청 코드

    Button btnOn;
    Button btnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // 화면에 리스트를 그린다.
        initData(); // 객체 초기화
        checkBluetoothPermission(); // 권한 여부 확인

        // 리스트 가져 오기
        listPairedDevices();
        // on off test
        addEventListener();

    }

    private void addEventListener() {
        btnOn.setOnClickListener(view -> {
            bluetoothOn();
        });
        btnOff.setOnClickListener(view -> {
            bluetoothOff();
        });

    }

    private void initData() {
        btnOn = findViewById(R.id.btn_on);
        btnOff = findViewById(R.id.btn_off);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothMap = new HashMap<>();
    }

    private void checkBluetoothPermission() {
        if (bluetoothAdapter == null) {
            if (!bluetoothAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, REQUEST_ENABLE_BT);
            }
        }  else {
            // bluetooth 가 활성화가 안되어 있는 경우 On 처리
            bluetoothOn();
        }
    }

    private void bluetoothOn(){
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            Toast.makeText(getApplicationContext(),"Bluetooth turned on",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Bluetooth is already on", Toast.LENGTH_SHORT).show();
        }
    }

    private void bluetoothOff(){
        bluetoothAdapter.disable(); // turn off
        Toast.makeText(getApplicationContext(),"Bluetooth turned Off", Toast.LENGTH_SHORT).show();
    }

    private void listPairedDevices(){
        // 페어링된 기기 확인
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.d(TAG, "deviceName : " + deviceName);
                Log.d(TAG, "deviceHardwareAddress : " + deviceHardwareAddress);
            }
        }


//        mBTArrayAdapter.clear();
//        mPairedDevices = bluetoothAdapter.getBondedDevices();
//        if(bluetoothAdapter.isEnabled()) {
//            // put it's one to the adapter
//            for (BluetoothDevice device : mPairedDevices)
//                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//
//            Toast.makeText(getApplicationContext(), "Show Paired Devices", Toast.LENGTH_SHORT).show();
//        }
//        else
//            Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, Data);
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Bluetooth 활성화 됨", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Bluetooth 비 활성화", Toast.LENGTH_SHORT).show();
            }
        }
    }
}