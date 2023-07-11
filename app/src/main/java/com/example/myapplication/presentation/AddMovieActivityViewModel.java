package com.example.myapplication.presentation;

import android.app.Application;
import android.text.BoringLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.data.database.model.MovieLocal;
import com.example.myapplication.data.model.Movie;
import com.example.myapplication.data.model.MovieRepository;

import java.util.ArrayList;
import java.util.List;

public class AddMovieActivityViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;

    private MutableLiveData<Boolean> _isMovieSaved = new MutableLiveData<Boolean>();

    public LiveData<Boolean> isMovieSaved() {

        return _isMovieSaved;
    }


    public AddMovieActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application.getApplicationContext());

    }

    public void saveMovies(String title, String yearReleased,
                                                      String rating, String plot, String imgbackdrop,
                                                      String poster) {

        List<String> images = new ArrayList<>();

        images.add(imgbackdrop);
        images.add(poster);

        Movie movie = new Movie(title, "", "", yearReleased,
                "", "", "",
                "", "",plot, "",
                "", "", "", "",
                rating,"", "", "", "",
                 images, "", true);

        movieRepository.addMovie(movie, success -> {
           _isMovieSaved.postValue(success);
        });
    }

}

