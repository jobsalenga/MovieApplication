package com.example.myapplication.data.service;

import com.example.myapplication.data.model.Movie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MovieApiService {
    @GET("/movies")
    Call<ArrayList<Movie>> getMovies();

    @POST("add-movie")
    Call<Movie> createPost(@Body Movie movie, @Header("Authorization") String basicToken);
}
