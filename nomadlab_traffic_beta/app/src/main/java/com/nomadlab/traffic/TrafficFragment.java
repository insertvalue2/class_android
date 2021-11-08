package com.nomadlab.traffic;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import utils.LightType;
import utils.OnTrafficTouchListener;


public class TrafficFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String deviceName;
    private OnTrafficTouchListener onTrafficTouchListener;
    private CardView buttonRed;
    private CardView buttonYellow;
    private CardView buttonGreen;
    private Button buttonManual;
    private Button buttonAuto;
    private Button buttonEnd;

    private boolean redOn = false;
    private boolean yellowOn = false;
    private boolean greenOn = false;
    private boolean isAuto = false;

    public void setOnTrafficTouchListener(OnTrafficTouchListener onTrafficTouchListener) {
        this.onTrafficTouchListener = onTrafficTouchListener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deviceName = getArguments().getString(ARG_PARAM1);
        }
    }

    private TrafficFragment() {
    }

    public static TrafficFragment newInstance(String param1) {
        TrafficFragment fragment = new TrafficFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traffic, container, false);
        initData(view);
        addEventListener();

        return view;
    }

    private void initData(View view) {
        TextView deviceNameTv = view.findViewById(R.id.device_name);
        deviceNameTv.setText(deviceName);

        buttonRed = view.findViewById(R.id.btn_red);
        buttonYellow = view.findViewById(R.id.btn_yellow);
        buttonGreen = view.findViewById(R.id.btn_green);
        buttonManual = view.findViewById(R.id.btn_manual);
        buttonAuto = view.findViewById(R.id.btn_auto);
        buttonEnd = view.findViewById(R.id.btn_end);
    }

    private void addEventListener() {

        buttonRed.setOnClickListener(view -> {
            redToggle();
        });

        buttonYellow.setOnClickListener(view -> {
            yellowToggle();
        });

        buttonGreen.setOnClickListener(view -> {
            greenToggle();
        });

        buttonManual.setOnClickListener(view -> {
            isAuto = false;
            buttonManual.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(getContext(), R.color.warning))
            );
            buttonAuto.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(getContext(), R.color.white))
            );
        });

        buttonAuto.setOnClickListener(view -> {
            buttonManual.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(getContext(), R.color.white))
            );
            buttonAuto.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(getContext(), R.color.warning))
            );
            if (!isAuto) {
                runAutoEvent();
            }
            isAuto = true;
        });

        buttonEnd.setOnClickListener(view -> {
            onTrafficTouchListener.onFinish();
        });

    }

    private void runAutoEvent() {
        new Thread(() -> {
            while (isAuto) {
                redToggle();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                yellowToggle();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                greenToggle();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void redToggle() {
        redOn = !redOn;
        int color;
        if (redOn) {
            color = ContextCompat.getColor(getContext(), R.color.red_on);
            onTrafficTouchListener.onItemClickRed(LightType.RedOn.getValue());
        } else {
            color = ContextCompat.getColor(getContext(), R.color.red_off);
            onTrafficTouchListener.onItemClickRed(LightType.RedOff.getValue());
        }
        buttonRed.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    private void yellowToggle() {
        yellowOn = !yellowOn;
        int color;
        if (yellowOn) {
            color = ContextCompat.getColor(getContext(), R.color.yellow_on);
            onTrafficTouchListener.onItemClickYellow(LightType.YellowOn.getValue());
        } else {
            color = ContextCompat.getColor(getContext(), R.color.yellow_off);
            onTrafficTouchListener.onItemClickYellow(LightType.YellowOff.getValue());
        }
        buttonYellow.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    private void greenToggle() {
        greenOn = !greenOn;
        int color;
        if (greenOn) {
            color = ContextCompat.getColor(getContext(), R.color.green_on);
            onTrafficTouchListener.onItemClickGreen(LightType.GreenOn.getValue());
        } else {
            color = ContextCompat.getColor(getContext(), R.color.green_off);
            onTrafficTouchListener.onItemClickGreen(LightType.GreenOff.getValue());
        }
        buttonGreen.setBackgroundTintList(ColorStateList.valueOf(color));

    }



}