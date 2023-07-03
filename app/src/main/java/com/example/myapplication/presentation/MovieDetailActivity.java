package com.example.myapplication.presentation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.database.model.MovieLocal;
import com.example.myapplication.data.model.Movie;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.databinding.ActivityMovieDetailsBinding;

public class MovieDetailActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding activityMovieDetailsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMovieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);


        MovieLocal movie = (MovieLocal) getIntent().getSerializableExtra("movie");
        activityMovieDetailsBinding.setMovie(movie);
        Glide.with(this)
                .load(movie.img_backdrop)
                .into(activityMovieDetailsBinding.imgBackDrop);
        Glide.with(this)
                .load(movie.poster)
                .into(activityMovieDetailsBinding.imgPoster);

        activityMovieDetailsBinding.imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MovieDetailActivity.this, MainActivity.class));
            }
        });
    }


}
