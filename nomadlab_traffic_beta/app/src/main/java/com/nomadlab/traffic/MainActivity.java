package com.nomadlab.traffic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.nomadlab.traffic.adapter.DeviceListAdapter;
import com.nomadlab.traffic.models.DeviceInfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import utils.LightType;
import utils.OnTrafficTouchListener;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private static final UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final static int REQUEST_CODE_BLUETOOTH = 1001;
    public final static int MESSAGE_READ = 2001;
    private final static int CONNECTING_STATUS = 3001;

    private Button btnOn;
    private Button btnOff;
    private Button btnPaired;
    private Button btnNewSearch;
    private SpinKitView spinKit;
    private CardView cvBluetoothStatus;
    private RecyclerView recyclerView;
    private final DeviceListAdapter deviceListAdapter = new DeviceListAdapter();
    private OnTrafficTouchListener onTrafficTouchListener;
    private BluetoothAdapter mBTAdapter;
    private final ArrayList<DeviceInfo> bluetoothDeviceList = new ArrayList<>();

    private Handler handler;
    private ConnectedBluetoothThread bluetoothThread;
    private BluetoothSocket bluetoothSocket = null;

    final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                bluetoothDeviceList.add(new DeviceInfo(device.getName(), device.getAddress()));
                deviceListAdapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initData();
        addEventListener();
        checkPermission();
        registeredHandler();
        listPairedDevices();
    }


    private void initData() {
        btnOn = findViewById(R.id.btn_on);
        btnOff = findViewById(R.id.btn_off);
        btnPaired = findViewById(R.id.btn_paired);
        btnNewSearch = findViewById(R.id.discover);
        recyclerView = findViewById(R.id.recycler_view);
        cvBluetoothStatus = findViewById(R.id.bluetoothStatusCV);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        spinKit = findViewById(R.id.spin_kit);

    }

    private void addEventListener() {

        btnOn.setOnClickListener(view -> {
            bluetoothOn();
        });

        btnOff.setOnClickListener(view -> {
            bluetoothOff();
        });

        btnPaired.setOnClickListener(view -> {
            listPairedDevices();
        });

        btnNewSearch.setOnClickListener(view -> {
            newSearchDevice();
        });

        onTrafficTouchListener = new OnTrafficTouchListener() {
            @Override
            public void onItemClickRed(String data) {
                if (bluetoothThread != null) {
                    bluetoothThread.write(data);
                }
            }

            @Override
            public void onItemClickYellow(String data) {
                if (bluetoothThread != null) {
                    bluetoothThread.write(data);
                }
            }

            @Override
            public void onItemClickGreen(String data) {
                if (bluetoothThread != null) {
                    bluetoothThread.write(data);
                }
            }

            @Override
            public void onFinish() {
                onBackPressed();
            }
        };
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }

    private void registeredHandler() {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == CONNECTING_STATUS) {
                    if(msg.what == MESSAGE_READ){
                        String readMessage = null;
                        try {
                            readMessage = new String((byte[]) msg.obj, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                    if (msg.arg1 == 1) {
                        // 초기 설정 light all off
                        if (bluetoothThread != null) {
                            bluetoothThread.write(LightType.AllOff.getValue());
                        }
                        Log.d(TAG, "Device check : " + msg.obj);
                    } else {
                        Log.d(TAG, "연결 실패");
                        Toast.makeText(getApplicationContext(), R.string.socket_failed, Toast.LENGTH_SHORT).show();
                    }
                    spinKit.setVisibility(View.GONE);
                }
            }
        };
    }

    private void bluetoothOn() {
        if (!mBTAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_CODE_BLUETOOTH);
            Toast.makeText(getApplicationContext(), R.string.bluetooth_on, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.bt_already_on, Toast.LENGTH_SHORT).show();
        }
        changeBluetoothStatusLayout();
    }

    private void bluetoothOff() {
        mBTAdapter.disable(); // turn off
        Toast.makeText(getApplicationContext(), R.string.bt_turn_off, Toast.LENGTH_SHORT).show();
        int color = ContextCompat.getColor(this, R.color.bluetooth_off);
        cvBluetoothStatus.setBackgroundTintList(ColorStateList.valueOf(color));
    }


    private void changeBluetoothStatusLayout() {
        int color;
        if (mBTAdapter.isEnabled()) {
            color = ContextCompat.getColor(this, R.color.bluetooth_on);
        } else {
            color = ContextCompat.getColor(this, R.color.bluetooth_off);
        }
        cvBluetoothStatus.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    private void listPairedDevices() {
        bluetoothDeviceList.clear();
        Set<BluetoothDevice> mPairedDevices = mBTAdapter.getBondedDevices();
        if (mBTAdapter.isEnabled()) {
            for (BluetoothDevice device : mPairedDevices) {
                bluetoothDeviceList.add(new DeviceInfo(device.getName(), device.getAddress()));
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.bluetooth_not_on, Toast.LENGTH_SHORT).show();
        }
        changeBluetoothStatusLayout();
        setupRecyclerView();
    }

    private void newSearchDevice() {
        if (mBTAdapter.isDiscovering()) {
            mBTAdapter.cancelDiscovery();
        } else {
            if (mBTAdapter.isEnabled()) {
                bluetoothDeviceList.clear(); // clear items
                mBTAdapter.startDiscovery();
                registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            } else {
                Toast.makeText(getApplicationContext(), R.string.bluetooth_not_on, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        deviceListAdapter.setData(bluetoothDeviceList);
        recyclerView.setAdapter(deviceListAdapter);
        deviceListAdapter.setOnItemClickListener((deviceName, macAddress) -> {
            spinKit.setVisibility(View.VISIBLE);
            if (!mBTAdapter.isEnabled()) {
                Toast.makeText(getBaseContext(), R.string.bluetooth_not_on, Toast.LENGTH_SHORT).show();
                return;
            }
            threadRun(deviceName, macAddress);
        });
    }

    private void createdFragment(String deviceName) {
        TrafficFragment fragment = TrafficFragment.newInstance(deviceName);
        fragment.setOnTrafficTouchListener(onTrafficTouchListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container_fl, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);
        if (requestCode == REQUEST_CODE_BLUETOOTH) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Enabled");
            } else {
                Log.d(TAG, "Disabled");
            }
            listPairedDevices();
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BT_UUID);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection", e);
        }
        return device.createRfcommSocketToServiceRecord(BT_UUID);
    }


    private void threadRun(String deviceName, String macAddress) {
        new Thread() {
            @Override
            public void run() {
                boolean fail = false;
                BluetoothDevice device = mBTAdapter.getRemoteDevice(macAddress);
                try {
                    bluetoothSocket = createBluetoothSocket(device);
                } catch (IOException e) {
                    fail = true;
                    Toast.makeText(getBaseContext(), R.string.socket_failed, Toast.LENGTH_SHORT).show();
                }

                try {
                    bluetoothSocket.connect();
                } catch (IOException e) {
                    try {
                        fail = true;
                        bluetoothSocket.close();
                        handler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                .sendToTarget();
                    } catch (IOException e2) {
                        Toast.makeText(getBaseContext(), R.string.socket_failed, Toast.LENGTH_SHORT).show();
                    }
                }

                if (!fail) {
                    bluetoothThread = new ConnectedBluetoothThread(bluetoothSocket, handler);
                    bluetoothThread.start();
                    handler.obtainMessage(CONNECTING_STATUS, 1, -1, deviceName)
                            .sendToTarget();

                    // 프래그먼트 생성
                    createdFragment(deviceName);
                }
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        if (bluetoothThread != null) {
            bluetoothThread.cancel();
        }
        super.onBackPressed();
    }
}// end of class