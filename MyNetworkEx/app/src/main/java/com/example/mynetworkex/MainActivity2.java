package com.example.mynetworkex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import requests.Post;
import responses.Todo;
import responses.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {

    Retrofit retrofit;
    RetrofitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView = findViewById(R.id.retrofit_tv);
        // json create object 1
        JSONObject objectJson = new JSONObject();
        try {
            objectJson.put("이름", "호랑이");
            objectJson.put("나이", "10");
            objectJson.put("직업", "동물");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        // json array 1
        JSONArray jsonObjectArray = new JSONArray();
        jsonObjectArray.put(objectJson);
        jsonObjectArray.put(objectJson);
        jsonObjectArray.put(objectJson);


        JSONObject jsonObj2 = new JSONObject();
        try {
            jsonObj2.put("arr", jsonObjectArray);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        System.out.println("objectJson : " + objectJson);
        System.out.println("jsonObjectArray: " + jsonObjectArray);
        System.out.println("jsonObj2:" + jsonObj2);


        // json parsing
        JSONObject obj;

        try {
            obj = new JSONObject(jsonObj2.toString());

            JSONArray array = obj.getJSONArray("arr");
            JSONObject dataObj = array.getJSONObject(0);

            String friendName = dataObj.getString("이름");
            String friendAge = dataObj.getString("나이");

            System.out.println("friendName:" + friendName);
            System.out.println("friendAge:" + friendAge);

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Retrofit retrofit = new Retrofit.Builder()
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())  // Gson
                .build();

//        RetrofitService service = retrofit.create(RetrofitService.class);
        service = retrofit.create(RetrofitService.class);
        service.getTodos().enqueue(new Callback<ArrayList<Todo>>() {
            @Override
            public void onResponse(Call<ArrayList<Todo>> call, Response<ArrayList<Todo>> response) {
                Log.d("TAG", response.isSuccessful() + "  <-------------");
                if (response.isSuccessful()) {
                    Todo todo = response.body().get(0);
                    Log.d("TAG", "TAGTAGt" + todo.userId);

                }

            }

            @Override
            public void onFailure(Call<ArrayList<Todo>> call, Throwable t) {

            }
        });


        service.getTodo(11).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                Log.d("TAG", " response body : " + response.body().userId);
                Log.d("TAG", " response code : " + response.code());
                Log.d("TAG", " response header : " + response.headers());
                // 인증정보가 조금 틀렸을 경우
                Log.d("TAG", " response errorBody : " + response.errorBody());

            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {

            }
        });

        createPost();

    }

    private void createPost() {

        Call<Post> call = service.createPost(30
                , "formUrlEncoded 적용한 제목 필드 부분"
                , "formUrlEncoded 적용한 내용 필드 부분");

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    Log.d("TAG", "status code : " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code : " + response.code() + "\n";
                content += "Id: " + postResponse.getId() + "\n";
                content += "User Id: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n";

                Log.d("TAG", "content : " + content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

}