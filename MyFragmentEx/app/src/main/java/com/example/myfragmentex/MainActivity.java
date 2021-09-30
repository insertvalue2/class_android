package com.example.myfragmentex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("life_cycle", "onCreate");

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Fragment fragmentOne = new FragmentOne();
        // 프래그먼트에 데이터를 넣어 주는 방법
        // 번들이란것을 만들어서 끼워 넣어 주어야 한다.
        Bundle bundle = new Bundle();
        bundle.putString("keyHello", "안녕하세요");
        fragmentOne.setArguments(bundle);


        // 프래그먼트 동적으로 작동 방법
        button1.setOnClickListener(view -> {
            // androidx <-- 확장. 뒤에 나온 녀셕


//            Fragment fragmentOne = new FragmentOne();

            //코드로 프래그먼트 만들기 위해서는 프래그먼트 매니저가 필요 하다
            FragmentManager fragmentManager = getSupportFragmentManager();
            // Transaction 이 필요하다. : FragmentTransaction
            // 작업의 단위 -> 시작과 끝이 있다.
            // 시작
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // 할일
            transaction.replace(R.id.fragContainer, fragmentOne);
            // 실행해서 확인 ( 시작 -> 할일 ) : 끝을 실행 하지 않았다.
            // 끝
            transaction.commit();
            // commit --> 시간될때 해 (좀더 안정적이다)
            // commitNow --> 지금 당장해
            // commitAllowingStateLoss --> 위험하다. 커밋이 실행안될 수 있다.
        });

        // 프래그먼트 동작 -> 삭제하기
        button2.setOnClickListener(view -> {
//            fragmentOne = new FragmentOne();
            //코드로 프래그먼트 만들기 위해서는 프래그먼트 매니저가 필요 하다
            FragmentManager fragmentManager = getSupportFragmentManager();
            // Transaction 이 필요하다.
            // 작업의 단위 -> 시작과 끝이 있다.
            // 시작
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // 할일
//            transaction.remove(fragmentOne); // 컨테이너에서 지움
            transaction.detach(fragmentOne); // 완전히 사라짐
            // 실행해서 확인 ( 시작 -> 할일 ) : 끝을 실행 하지 않았다.
            // 끝
            transaction.commit();
            // commit --> 시간될때 해 (좀더 안정적이다)
            // commitNow --> 지금 당장해
            // commitAllowingStateLoss --> 위험하다. 커밋이 실행안될 수 있다.
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("life_cycle", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("life_cycle", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("life_cycle", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("life_cycle", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("life_cycle", "onDestroy");
    }

}