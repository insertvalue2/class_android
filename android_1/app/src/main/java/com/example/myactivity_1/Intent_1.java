package com.example.myactivity_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class Intent_1 extends AppCompatActivity {

    final static String TAG = Intent_1.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent1);
        Button button = findViewById(R.id.button1);
        button.setOnClickListener(view -> {
            // 주변정보, 가리키는 대상
            Intent intent = new Intent(this, Intent_2.class);
            // 정보를 보내는 방법
            // key, value 방식 쌍으로 만들어 저장한다.
            intent.putExtra("number1", 1);
            intent.putExtra("number2", 2);
            // 전달만 하는 요청
            startActivity(intent);
            // 리턴을 받는 요청
//            startActivityForResult(intent, 10001);
        });

        //예제 3 암시적 인텐트 만들기
//        button.setOnClickListener(view -> {
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
//            startActivity(intent);
//        });
    }

    // intent 결과값을 받을 때 사용 방법
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Log.d(TAG, "요청에 종류 확인 ");
            Log.d(TAG, requestCode + "");
            Log.d(TAG, resultCode + "");
            Log.d(TAG, data + "");
            int result = data.getIntExtra("result", 0);
            Log.d(TAG, "result value : " + result);
            if(resultCode == RESULT_OK) {
                int sum = data.getIntExtra("result", 0);
                Log.d(TAG, "result value : " + sum);
            }
        }
    }
}