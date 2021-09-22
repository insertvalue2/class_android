package com.example.myactivity_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Listener extends AppCompatActivity {

    TextView tvHello;
    int number = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener);

        tvHello = findViewById(R.id.tvHello);

        // 안드로이드 시스템이 리스너를 쫙 펼쳐 놓고 클릭 되었다고 알려 준다.
        tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", view.getId() + "");
                Log.d("TAG", view.getTag().toString());
                Log.d("TAG", "tvHello Clicked");
//                tvHello.setText("안녕하세요");
//                ImageView imageView = findViewById(R.id.image);
//                imageView.setImageResource(R.drawable.stroke);
//                number += 10;
                Log.d("TAG", number + "");
            }
        });

//        tvHello.setOnClickListener(view ->Log.d("TAG", "clicked"));
        // 람다 -> 익명함수에 좀 더 간결한 표현식 이다.
        // 리스너 종류 확인

        // 뷰를 조작하는 함수들

        // 리스너를 동시에 달았다고 하면 맨 마지막에 등록한 리스너가 동작하게 된다.
        tvHello.setText(R.string.app_name);
        tvHello.setText("안녕하세요");
        ImageView imageView = findViewById(R.id.image);
        imageView.setImageResource(R.drawable.stroke);

        // xml 에서 그리는 뷰는 정적이다.
        // 정적인 뷰 들을 동적인 뷰로 변경 하려면 xml 에서 가지고 와서
        // activity 에서 조작한다.

    } // end of onCreate



}