package com.cos.retrofit2movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvSetting();
        rvDataSetting();
    }

    private void rvSetting(){
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void rvDataSetting(){
        final YtsAdapter adapter = new YtsAdapter();

        //레트로핏
        YtsService movieService = YtsService.retrofit.create(YtsService.class);
        Call<YtsData> call = movieService.repoContributors("rating", 20, 1);
        call.enqueue(new Callback<YtsData>() {
            @Override
            public void onResponse(Call<YtsData> call,
                                   Response<YtsData> response) {
                YtsData yts = response.body();
                Log.d(TAG, yts.getData().toString());
                adapter.addItems(yts.getData().getMovies());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<YtsData> call, Throwable t) {
                Log.d(TAG,"request api error");
            }
        });
    }

}
