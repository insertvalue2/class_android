package com.nomadlab.traffic;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ConnectedBluetoothThread extends Thread {
    private final BluetoothSocket btSocket;
    private final InputStream btInStream;
    private final OutputStream btOutStream;
    private final Handler handler;
    public boolean threadHandler = true;

    public ConnectedBluetoothThread(BluetoothSocket socket, Handler handler) {
        btSocket = socket;
        this.handler = handler;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }

        btInStream = tmpIn;
        btOutStream = tmpOut;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int bytes;
        while (threadHandler) {
            try {
                bytes = btInStream.available();
                if (bytes != 0) {
                    buffer = new byte[1024];
                    SystemClock.sleep(50);
                    bytes = btInStream.available();
                    bytes = btInStream.read(buffer, 0, bytes);
                    handler.obtainMessage(MainActivity.MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * @param input
     * String 을 바이트로 변환 -> 아스키 코드로 보내야 합니다.
     * 1, 2, 3, 4, 5, 6, 7, 8
     */
    public void write(String input) {
        byte[] bytes = input.getBytes(); 
        try {
            btOutStream.write(bytes);
        } catch (IOException e) {
            Log.d("TAG", "TAG error");
        }
    }


    public void cancel() {
        try {
            btSocket.close();
        } catch (IOException e) {
        }
    }
}