package com.example.myapplication.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.data.database.model.MovieLocal;
import com.example.myapplication.data.model.Movie;

import java.util.List;

@Dao
public interface MovieLocalDao{

        @Query("SELECT * FROM movie_local")
        List<MovieLocal> getAll();

        @Insert
        void insert(MovieLocal... movieLocal);

        @Query("SELECT id FROM movie_local LIMIT 1")
        int checkData();

}
