package com.example.electronicframeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button addPhotoButton;
    Button startPhotoFrameModeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    private void initData() {

        addPhotoButton = findViewById(R.id.addPhotoButton);
        startPhotoFrameModeButton = findViewById(R.id.startPhotoFrameModeButton);
    }

    private void initAddPhotoButton() {
        addPhotoButton.setOnClickListener(view -> {
            int isPermission = ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
            );

            if (isPermission == PackageManager.PERMISSION_GRANTED) {
                //Todo 권한이 잘 부여 되었을 때 갤러리에서 사진을 선택하는 기능

            } else {
                // 권한을 요청하는 팝업
                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE);
                // todo 교육용 팝업 확인 후 권한 팝업을 띄우는 기능
            }


        });
    }

    private void initStartPhotoFrameModeButton() {

    }


}