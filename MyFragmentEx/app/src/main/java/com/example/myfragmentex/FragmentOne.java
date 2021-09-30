package com.example.myfragmentex;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOne extends Fragment {

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d("life_cycle", "F : onAttach");
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("life_cycle", "F : onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String data = getArguments().getString("keyHello");
            Log.d("life_cycle", "data : " + data);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("life_cycle", "F : onCreateView");
        // 중요 !!
        // 프래그먼트가 처음으로 그릴 때 호출 된다.
        // 여기서 뷰를 그려준다.
        // 프래그먼트를 액티비티  setContentView(R.layout.activity_main);
        // 를 호출해서 그리는 방식이과 다르다.
        // 메서드 매개변수에 Bundle savedInstanceState 외에 아래 추가적인 부분이 있다.

        // LayoutInflater inflater -> 뷰을 그려주는 역할 (리소스를 반한 : xml파일)
        // ViewGroup container --> 부모 뷰를 의미 한다. (위치)

        // 프래그먼트 데이터 받기 (xml viewComponent 주석처리하기)


        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("life_cycle", "F : onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("life_cycle", "F : onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d("life_cycle", "F : onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("life_cycle", "F : onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("life_cycle", "F : onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("life_cycle", "F : onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("life_cycle", "F : onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("life_cycle", "F : onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("life_cycle", "F : onDetach");
        super.onDetach();
    }


}