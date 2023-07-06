package com.example.myapplication.data.model;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.data.database.AppDatabase;
import com.example.myapplication.data.database.MovieLocalDao;
import com.example.myapplication.data.database.model.MovieLocal;
import com.example.myapplication.data.service.APICallback;
import com.example.myapplication.data.service.MovieApiService;
import com.example.myapplication.data.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {


    public MovieRepository(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context, AppDatabase.class, "movie_local").build();
        movieLocalDao = db.movieLocalDao();
    }

    private ArrayList<Movie> movies = new ArrayList<>();
    private MutableLiveData<ArrayList<MovieLocal>> mutableLiveData = new MutableLiveData<>();
    private Application application;
    private Context context;

    private AppDatabase db;
    private MovieLocalDao movieLocalDao;


    public void saveMovies(final APICallback callback) {


        MovieApiService movieAPIService = RetrofitInstance.getService();

        Call<ArrayList<Movie>> call = movieAPIService.getMovies();

        call.enqueue(new Callback<ArrayList<Movie>>() {
            @Override
            public void onResponse(Call<ArrayList<Movie>> call, Response<ArrayList<Movie>> response) {
                movies = response.body();

                AppDatabase.databaseWriteExecutor.execute(() -> {
                    if (movieLocalDao.checkData() == 1) {
                        callback.onResponse(true);
                        return;
                    }
                    if (movies != null) {
                        for (Movie movie : movies) {
                            movieLocalDao.insert(new MovieLocal(movie.getTitle(), movie.getReleased(),
                                    movie.getImdbRating(), movie.getPlot(),
                                    movie.getImages().get(0), movie.getImages().get(1)));
                        }
                    }
                    callback.onResponse(true);
                });

            }

            @Override
            public void onFailure(Call<ArrayList<Movie>> call, Throwable t) {
                callback.onResponse(false);
            }
        });
    }


    public MutableLiveData<ArrayList<MovieLocal>> getMoviesLiveData() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            ArrayList<MovieLocal> convertedMovie = new ArrayList<>(movieLocalDao.getAll());
            mutableLiveData.postValue(convertedMovie);
        });

        return mutableLiveData;
    }


    public void addMovie(Movie movie, final APICallback callback) {


        MovieApiService movieAPIService = RetrofitInstance.getService();

        Call<Movie> call = movieAPIService.createPost(movie, "dGVzdDpzZWNyZXQ=");
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Movie movieFromServer = (Movie) response.body();
                            AppDatabase.databaseWriteExecutor.execute(() -> {
                                movieLocalDao.insert(new MovieLocal(movieFromServer.getTitle(), movieFromServer.getReleased(),
                                        movieFromServer.getImdbRating(), movieFromServer.getPlot(),
                                        movieFromServer.getImages().get(0), movieFromServer.getImages().get(1)));
                            });
                    callback.onResponse(true);
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                callback.onResponse(false);
            }
        });
    }
}
//using retrofit database
//    public MutableLiveData<List<Movie>> getMutableLiveData() {
//        MovieApiService movieAPIService = RetrofitInstance.getService();
//        Call<ArrayList<Movie>> call = movieAPIService.getMovies();
//        call.enqueue(new Callback<ArrayList<Movie>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Movie>> call, Response<ArrayList<Movie>> response) {
//                movies = response.body();
//                mutableLiveData.setValue(movies);
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Movie>> call, Throwable t) {
//
//            }
//        });
//        return mutableLiveData;
//    }



