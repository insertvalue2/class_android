package com.example.mylibraryex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ImageView imageView1 = findViewById(R.id.glideImageView1);

        ImageView imageView2 = findViewById(R.id.glideImageView2);
        ImageView imageView3 = findViewById(R.id.glideImageView3);
        ImageView imageView4 = findViewById(R.id.glideImageView4);
        ImageView imageView5 = findViewById(R.id.glideImageView5);

//        Glide.with(this)
//                .load(R.drawable.image)
//                .into(imageView);

//        Glide.with(this)
//                .load("https://picsum.photos/200/300")
//                .into(imageView);

        Glide.with(this)
                .load("https://picsum.photos/200/300")
                .circleCrop()
                .into(imageView1);

        Glide.with(this)
                .load("https://picsum.photos/seed/picsum/200/300")
                .circleCrop()
                .into(imageView2);

        Glide.with(this)
                .load("https://picsum.photos/200/300")
                .circleCrop()
                .into(imageView3);

        Glide.with(this)
                .load("https://picsum.photos/200/300")
                .circleCrop()
                .into(imageView4);

        Glide.with(this)
                .load("https://picsum.photos/200/300")
                .circleCrop()
                .into(imageView5);


    }
}