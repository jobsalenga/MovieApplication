package com.example.myapplication.presentation;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.ViewModelProvider;


import com.example.myapplication.R;
import com.example.myapplication.data.database.model.MovieLocal;

import com.example.myapplication.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements OnRowClick {

    private ActivityMainBinding activityMainBinding;
    private MovieDetailAdapter movieDetailAdapter;
    private MainActivityViewModel mainActivityViewModel;
    private int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        getPopularMovies();

        activityMainBinding.addButton.setOnClickListener(v -> {
            onAddButtonClicked();
        });


    }

    private void setUpRecyclerView() {
        if (movieDetailAdapter == null) {
            movieDetailAdapter = new MovieDetailAdapter(this);
            activityMainBinding.recyclerView.setAdapter(movieDetailAdapter);
        }
    }

    @Override
    public void onMovieClicked(MovieLocal movie) {
        orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            activityMainBinding.tvTitle.setText(movie.title);
            activityMainBinding.tvYearRelease.setText(movie.year_released);
            activityMainBinding.tvDescription.setText(movie.description);
            activityMainBinding.tvRating.setText(movie.rating);
        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        }

    }

    public void onAddButtonClicked() {

        Intent intent = new Intent(this, AddMovieActivity.class);
        startActivity(intent);

    }

    public void getPopularMovies() {

        mainActivityViewModel.getAllMovies().observe(this, moviesFromLiveData -> {
            setUpRecyclerView();
            movieDetailAdapter.setMovies(moviesFromLiveData);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getPopularMovies();
    }
}