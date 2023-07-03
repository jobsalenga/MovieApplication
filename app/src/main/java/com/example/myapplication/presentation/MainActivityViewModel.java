package com.example.myapplication.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.data.database.model.MovieLocal;
import com.example.myapplication.data.model.Movie;
import com.example.myapplication.data.model.MovieRepository;
import com.example.myapplication.data.service.APICallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application.getApplicationContext());
        saveMovies();
    }

    public LiveData<ArrayList<MovieLocal>> getAllMovies() {
        return movieRepository.getMoviesLiveData();
    }

    public void saveMovies() {

        movieRepository.saveMovies(new APICallback() {
            @Override
            public void onResponse(boolean success){
                if (success){
                    getAllMovies();
                }
            }
        });
    }
}

