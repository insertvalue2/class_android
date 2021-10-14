package com.cos.retrofit2movieapp;

import java.util.List;

import lombok.Data;

@Data
public class YtsData {
    private String status;
    private String status_message;
    private MyData data;

    @Data
    class MyData {
        private int movie_count;
        private int limit;
        private int page_number;
        private List<Movie> movies;

        @Data
        class Movie{
            private String title;
            private float rating;
            private String medium_cover_image;
        }
    }
}
