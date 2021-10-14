package com.cos.retrofit2movieapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class YtsAdapter extends RecyclerView.Adapter<YtsAdapter.MyViewHolder>{

    List<YtsData.MyData.Movie> list = new ArrayList<>();

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPoster;
        private TextView tvTitle;
        private TextView tvRating;
        private RatingBar ratingBar;

        public MyViewHolder(View movieItemView) {
            super(movieItemView);
            ivPoster = movieItemView.findViewById(R.id.iv_poster);
            tvTitle = movieItemView.findViewById(R.id.tv_title);
            tvRating = movieItemView.findViewById(R.id.tv_rating);
            ratingBar = movieItemView.findViewById(R.id.rating_bar);
        }

        public void setItem(YtsData.MyData.Movie movie){
            tvTitle.setText(movie.getTitle());
            tvRating.setText(movie.getRating()+"");
            Picasso.with(ivPoster.getContext())
                    .load(movie.getMedium_cover_image())
                    .into(ivPoster);
            ratingBar.setRating(movie.getRating()/2);
        }
    }

    public void addItem(YtsData.MyData.Movie movie){
        list.add(movie);
    }

    public void addItems(List<YtsData.MyData.Movie> list){
        this.list = list;
    }

    @NonNull
    @Override
    public YtsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View movieItemView = inflater.inflate(R.layout.card_item, viewGroup, false);

        return new MyViewHolder(movieItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull YtsAdapter.MyViewHolder myViewHolder, int i) {
        YtsData.MyData.Movie movie = list.get(i);
        myViewHolder.setItem(movie);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
