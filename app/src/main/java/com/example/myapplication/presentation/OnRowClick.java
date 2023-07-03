package com.example.myapplication.presentation;

import android.view.View;

import com.example.myapplication.data.database.model.MovieLocal;
import com.example.myapplication.data.model.Movie;

public interface OnRowClick {
    void onMovieClicked(MovieLocal movie);
}
