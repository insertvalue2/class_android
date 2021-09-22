package com.example.myactivity_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Intent_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent2);
        // 값을 꺼내는 방법
        int number1 = getIntent().getIntExtra("number1", 0);
        int number2 = getIntent().getIntExtra("number2", 0);
        Log.d("TAG", number1 + " < ----- ");
        Log.d("TAG", number2 + " < ----- ");
        int sum = number1 + number2;
        Button resultBtn = findViewById(R.id.resultBtn);
        resultBtn.setText(sum + "");

        // 결과값을 다시 돌려 줄 때
        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", sum);
                setResult(Activity.RESULT_OK, resultIntent);

                finish(); // activity 종료
            }
        });
    }
}