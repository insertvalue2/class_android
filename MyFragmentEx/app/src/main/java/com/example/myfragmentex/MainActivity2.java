package com.example.myfragmentex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button button = findViewById(R.id.button1);

        button.setOnClickListener(view -> {
            Fragment fragment = FragmentTwo.newInstance("key1", "value1");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragContainer, fragment);
            transaction.commit();
        });

        //
        new Thread(new Runnable(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        button.setBackgroundColor(Color.BLACK);
                    }
                });
                button.setBackgroundColor(Color.BLACK);
            }
        }).start();

    }
}