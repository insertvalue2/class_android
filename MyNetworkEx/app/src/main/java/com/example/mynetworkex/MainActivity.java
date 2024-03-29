package com.example.mynetworkex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // json parsing
        JSONObject obj;

        try {
            obj = new JSONObject(objectJson.toString());

            JSONArray array = obj.getJSONArray("arr");
            JSONObject dataObj = array.getJSONObject(0);

            String friendName = dataObj.getString("이름");
            String friendAge = dataObj.getString("나이");
            System.out.println("friendName:"+friendName);
            System.out.println("friendAge:"+friendAge);

        } catch (JSONException e) {
            e.printStackTrace();
        }


//
//        FakeHttp fakeHttp = new FakeHttp();
//        Thread thread = new Thread(fakeHttp);
//        thread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String urlString = "http://mellowcode.org/json/students/";
                        //URL 객체로 변경해야 한다.
                        try {
                            URL url = new URL(urlString);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setRequestProperty("Content-Type", "application/json");
                            String buffer = "";
                            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                BufferedReader reader
                                        = new BufferedReader(
                                        new InputStreamReader(connection.getInputStream(),
                                                "UTF-8"
                                        )
                                );
//
//                StringBuffer sb = new StringBuffer();
//                String line = null;
//
//                if (statusCode == 200) {
//                    while ((line = reader.readLine()) != null) {
////					sb.append(line);
//                        sb.append(line + "\n");
//                    }
//                }
//
//                String str = sb.toString();
//                System.out.println(str);
//                System.out.println("-----------------");

                                buffer = reader.readLine();
                                Log.d("TAG", buffer);

                                // jsonObject
                                // jsonArray
                                Person[] person = new Gson().fromJson(buffer, Person[].class);

                                Type userListType = new TypeToken<ArrayList<Person>>() {
                                }.getType();
                                ArrayList<Person> personArrayList = new Gson().fromJson(buffer, userListType);

                                for (Person per: personArrayList) {

                                    Log.d("TAG", per.id + " : id");
                                    Log.d("TAG", per.name + " : name");
                                    Log.d("TAG", per.age + " : age");
                                    Log.d("TAG", per.intro + " : intro");
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                String urlString = "http://mellowcode.org/json/students/";
//                //URL 객체로 변경해야 한다.
//                try {
//                    URL url = new URL(urlString);
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("GET");
//                    connection.setRequestProperty("Content-Type", "application/json");
//                    String buffer = "";
//                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                        BufferedReader reader
//                                = new BufferedReader(
//                                new InputStreamReader(connection.getInputStream(),
//                                        "UTF-8"
//                                )
//                        );
////
////                StringBuffer sb = new StringBuffer();
////                String line = null;
////
////                if (statusCode == 200) {
////                    while ((line = reader.readLine()) != null) {
//////					sb.append(line);
////                        sb.append(line + "\n");
////                    }
////                }
////
////                String str = sb.toString();
////                System.out.println(str);
////                System.out.println("-----------------");
//
//                        buffer = reader.readLine();
//                        Log.d("TAG", buffer);
//
//                        // jsonObject
//                        // jsonArray
//                        Person[] person = new Gson().fromJson(buffer, Person[].class);
//
//                        Type userListType = new TypeToken<ArrayList<Person>>() {
//                        }.getType();
//                        ArrayList<Person> personArrayList = new Gson().fromJson(buffer, userListType);
//
//                        for (Person per: personArrayList) {
//
//                            Log.d("TAG", per.id + " : id");
//                            Log.d("TAG", per.name + " : name");
//                            Log.d("TAG", per.age + " : age");
//                            Log.d("TAG", per.intro + " : intro");
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

    }
}

class FakeHttp extends Thread {

    Context context;

    public FakeHttp(Context context) {
        this.context = context;
    }

    @Override
    public void run() {

        String urlString = "http://mellowcode.org/json/students/";
        //URL 객체로 변경해야 한다.
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            String buffer = "";
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader
                        = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(),
                                "UTF-8"
                        )
                );
//
//                StringBuffer sb = new StringBuffer();
//                String line = null;
//
//                if (statusCode == 200) {
//                    while ((line = reader.readLine()) != null) {
////					sb.append(line);
//                        sb.append(line + "\n");
//                    }
//                }
//
//                String str = sb.toString();
//                System.out.println(str);
//                System.out.println("-----------------");

                buffer = reader.readLine();
                Log.d("TAG", buffer);

                // jsonObject
                // jsonArray
                Person[] person = new Gson().fromJson(buffer, Person[].class);

                Type userListType = new TypeToken<ArrayList<Person>>() {
                }.getType();
                ArrayList<Person> personArrayList = new Gson().fromJson(buffer, userListType);

                for (Person per: personArrayList) {

                    Log.d("TAG", per.id + " : id");
                    Log.d("TAG", per.name + " : name");
                    Log.d("TAG", per.age + " : age");
                    Log.d("TAG", per.intro + " : intro");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}