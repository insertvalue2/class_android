package com.example.myactivity_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Calculator extends AppCompatActivity {

    TextView zero;
    TextView one;
    TextView two;
    TextView three;
    TextView four;
    TextView five;
    TextView six;
    TextView seven;
    TextView eight;
    TextView nine;
    TextView ca;
    TextView plus;
    TextView minus;
    TextView multiply;
    TextView divide;
    TextView equals;
    TextView result;
    // new --> old (newValue + oldValue)
    String newValue = "0";
    String oldValue = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        initData();
        addEventListener();
    }

    private void initData() {
        zero = findViewById(R.id.zero);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);

        ca = findViewById(R.id.ca);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        multiply = findViewById(R.id.multiply);
        divide = findViewById(R.id.divide);
        equals = findViewById(R.id.equals);
        result = findViewById(R.id.result);

    }

    private void addEventListener() {
        zero.setOnClickListener(view -> {
            newValue = newValue + "0";
            result.setText(newValue);
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newValue = newValue + "1";
                result.setText(newValue);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newValue = newValue + "2";
                result.setText(newValue);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newValue = newValue + "3";
                result.setText(newValue);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newValue = newValue + "4";
                result.setText(newValue);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newValue = newValue + "5";
                result.setText(newValue);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newValue = newValue + "6";
                result.setText(newValue);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newValue = newValue + "7";
                result.setText(newValue);
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newValue = newValue + "8";
                result.setText(newValue);
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newValue = newValue + "9";
                result.setText(newValue);
            }
        });
        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newValue = "0";
                oldValue = "0";
                result.setText(newValue);
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number1 = Integer.parseInt(oldValue);
                int number2 = Integer.parseInt(newValue);
                int sum = (number1 + number2);
                oldValue = String.valueOf(sum);
                newValue = "0";
                result.setText(oldValue);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oldValue.equals("0")) {
                    oldValue = newValue;
                    newValue = "0";
                } else {
                    int number1 = Integer.parseInt(oldValue);
                    int number2 = Integer.parseInt(newValue);
                    int sum = (number1 - number2);
                    oldValue = String.valueOf(sum);
                    newValue = "0";
                    result.setText(oldValue);
                }
            }
        });

        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oldValue.equals("0")) {
                    oldValue = newValue;
                    newValue = "0";
                } else {
                    int number1 = Integer.parseInt(oldValue);
                    int number2 = Integer.parseInt(newValue);
                    int sum = (number1 * number2);
                    oldValue = String.valueOf(sum);
                    newValue = "0";
                    result.setText(oldValue);
                }
            }
        });

        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number1 = Integer.parseInt(oldValue);
                int number2 = Integer.parseInt(newValue);
                if (number2 == 0) {
                    result.setText("0으로 나눌 수 없습니다.");
                    newValue = "0";
                    oldValue = "0";
                } else {
                    int sum = (number1 / number2);
                    oldValue = String.valueOf(sum);
                    newValue = "0";
                    result.setText(oldValue);
                }
            }
        });

    }

}