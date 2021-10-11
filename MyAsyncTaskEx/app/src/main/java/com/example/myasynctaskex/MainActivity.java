package com.example.myasynctaskex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}

// 이 클래스는 API 레벨 30에서 더 이상 사용되지 않습니다 .
// params --> doInBackground 사용할 타입
// progress --> onProgressUpdate
// result --> onPostExcuted
class BackgroundAsyncTask extends AsyncTask<Integer, Integer, Integer> {

    ProgressBar progressBar;
    TextView textView;

    @Override
    protected Integer doInBackground(Integer... integers) {
        return null;
    }


}