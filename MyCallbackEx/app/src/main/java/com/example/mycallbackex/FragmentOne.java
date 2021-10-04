package com.example.mycallbackex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

interface OnMyButtonCallback {
    void onMyClicked();
    void OnMyClickedValue(String result);
}

// 콜리 : 생성자로 사용하는 방법
// 객체 생성시 무조건 매개변수로 타입을 넘겨 받아서 사용한다.
public class FragmentOne extends Fragment {

    // 호출자
    OnMyButtonCallback onMyButtonCallback;

    public FragmentOne(OnMyButtonCallback onMyButtonCallback) {
        this.onMyButtonCallback = onMyButtonCallback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        Button button = v.findViewById(R.id.frButton1);
        button.setOnClickListener(view -> {
            Log.d("TAG", "button event listener");
            onMyButtonCallback.onMyClicked();
        });
        return v;
    }
}