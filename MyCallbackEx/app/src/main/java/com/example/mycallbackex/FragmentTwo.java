package com.example.mycallbackex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

// 콜리 : 메서드로 사용 하는 방법
public class FragmentTwo extends Fragment {

    OnMyButtonCallback onMyButtonCallback;

    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        Button button2 = view.findViewById(R.id.frButton2);
        button2.setOnClickListener(view1 -> {
            onMyButtonCallback.OnMyClickedValue("Fragment --> Activity 값 전달 구현");
        });

//        view.setOnClickListener(view1 -> {
//            onMyButtonCallback.OnMyClickedValue("Fragment --> Activity 값 전달 구현");
//        });

        return view;
    }

    // 메서드로 인터페이스 연결
    public void setOnMyButtonCallback(OnMyButtonCallback onMyButtonCallback) {
        this.onMyButtonCallback = onMyButtonCallback;
    }
}