package com.example.myapplication.data.service;

import com.example.myapplication.data.model.Movie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApiService {
    @GET("/movies")
    Call<ArrayList<Movie>> getMovies();
}
