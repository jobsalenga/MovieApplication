package com.example.myapplication.data.database.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "movie_local")
public class MovieLocal implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int ID;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "year_released")
    public String year_released;

    @ColumnInfo(name = "rating")
    public String rating;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "img_backdrop")
    public String img_backdrop;

    @ColumnInfo(name = "poster")
    public String poster;


    public MovieLocal(String title, String year_released, String rating, String description, String img_backdrop, String poster) {
        this.title = title;
        this.year_released = year_released;
        this.rating = rating;
        this.description = description;
        this.img_backdrop = img_backdrop;
        this.poster = poster;
    }
}
