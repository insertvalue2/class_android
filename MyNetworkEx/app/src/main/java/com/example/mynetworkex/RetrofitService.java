package com.example.mynetworkex;

import java.util.ArrayList;
import java.util.List;

import requests.Post;
import responses.Todo;
import responses.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

// 비즈니스 로직을 처리하는 부분은 요청사항에 따라 언제든지 변할 수 있고
// 변화에 대응하기 위해 확장을 염두하여 인터페이스로 구성
public interface RetrofitService {

    @GET("todos")
    Call<ArrayList<Todo>> getTodos();

    @GET("todos/{id}")
    Call<Todo> getTodo(@Path("id") int groupId);

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );




}
