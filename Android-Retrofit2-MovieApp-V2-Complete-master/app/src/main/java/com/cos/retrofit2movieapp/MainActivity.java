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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void rvDataSetting(){
        final YtsAdapter adapter = new YtsAdapter();

        //레트로핏
        YtsService movieService = YtsService.retrofit.create(YtsService.class);
        Call<YstData> call = movieService.repoContributors("rating", 20, 1);
        call.enqueue(new Callback<YstData>() {
            @Override
            public void onResponse(Call<YstData> call,
                                   Response<YstData> response) {
                YstData yts = response.body();

                adapter.addItems(yts.getData().getMovies());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<YstData> call, Throwable t) {
                Log.d(TAG,"request api error");
            }
        });
    }

}
