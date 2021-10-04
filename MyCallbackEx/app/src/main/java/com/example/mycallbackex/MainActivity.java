package com.example.mycallbackex;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity implements OnMyButtonCallback {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.emptyTextView);
        createdFragmentOne();
        createFragmentTwo();
    }

    private void createdFragmentOne() {
        FragmentOne fragmentOne = new FragmentOne(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container1, fragmentOne);
        transaction.commit();
    }
    private void createFragmentTwo() {
        FragmentTwo fragmentTwo = new FragmentTwo();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container2, fragmentTwo);
        transaction.commit();

        // 메서드로 사용
        fragmentTwo.setOnMyButtonCallback(this);
    }

    @Override
    public void onMyClicked() {
        Log.d("TAG", "이벤트를 전달 받았습니다. 콜백됨... ");
    }

    @Override
    public void OnMyClickedValue(String result) {
        Log.d("TAG", "넘겨 받은 값 표시 : " + result);
        textView.setText(result);
    }
}

// 익명 내부 구현 객체 --> 변수에 담기
//public class MainActivity extends AppCompatActivity {
//
//    TextView textView;
//    OnMyButtonCallback onMyButtonCallback = new OnMyButtonCallback() {
//        @Override
//        public void onMyClicked() {
//            Log.d("TAG", "콜백 이벤트 확인1 ");
//        }
//
//        @Override
//        public void OnMyClickedValue(String result) {
//            Log.d("TAG", "콜백 이벤트 확인2 : " + result);
//            textView.setText(result);
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        textView = findViewById(R.id.emptyTextView);
//        createdFragmentOne();
//        createFragmentTwo();
//    }
//
//    private void createdFragmentOne() {
//        FragmentOne fragmentOne = new FragmentOne(onMyButtonCallback);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.container1, fragmentOne);
//        transaction.commit();
//    }
//    private void createFragmentTwo() {
//        FragmentTwo fragmentTwo = new FragmentTwo();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.container2, fragmentTwo);
//        transaction.commit();
//
//        // 메서드로 사용
//        fragmentTwo.setOnMyButtonCallback(this);
//    }
//}