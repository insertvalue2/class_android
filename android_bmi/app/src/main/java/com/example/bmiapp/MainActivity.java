package com.example.bmiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText heightEt = findViewById(R.id.heightEt);
        EditText weightEt = findViewById(R.id.weightEt);
        Button isOk = findViewById(R.id.isOk);

        isOk.setOnClickListener(view -> {
            if (heightEt.getText().length() < 1 || weightEt.getText().length() < 1) {
                Log.d(TAG, "is null");
                Toast.makeText(this, "빈 값이 있습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            int height = Integer.parseInt(heightEt.getText().toString());
            int weight = Integer.parseInt(weightEt.getText().toString());

            Intent intent =  new Intent(this, ResultActivity.class);
            intent.putExtra("height", height);
            intent.putExtra("weight", weight);
            startActivity(intent);
        });

    }
}