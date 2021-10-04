package com.example.myaddviewex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Item 하나를 들어갈 뷰를 만들어 준다.

        // 아이템 리스트 준비
        ArrayList<Car> carArrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            carArrayList.add(new Car(i + "번째 자동차", "engine" + i));
        }

        LinearLayout container = findViewById(R.id.addViewContainer);

        // inflater
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < carArrayList.size(); i++) {
            View itemView = inflater.inflate(R.layout.item_view, null);
            TextView carNameTv = itemView.findViewById(R.id.carName);
            TextView engineTv = itemView.findViewById(R.id.carEngine);
            carNameTv.setText(carArrayList.get(i).name);
            engineTv.setText(carArrayList.get(i).engine);
            container.addView(itemView);
        }
    }
}

class Car {
    String name;
    String engine;

    public Car(String name, String engine) {
        this.name = name;
        this.engine = engine;
    }
}