package com.example.myapplication.presentation;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.data.database.model.MovieLocal;

import com.example.myapplication.databinding.ItemMovieBinding;

import java.util.ArrayList;


public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.MovieDetailHolder> {
    private final OnRowClick onRowClick;
    private ArrayList<MovieLocal> movieList;

    public MovieDetailAdapter(OnRowClick onRowClick) {
        this.onRowClick = onRowClick;
    }

    public void setMovies(ArrayList<MovieLocal> movies) {
        this.movieList = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMovieBinding itemMovieBinding = ItemMovieBinding.inflate(layoutInflater, parent, false);
        return new MovieDetailHolder(itemMovieBinding, onRowClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDetailHolder holder, int position) {
        MovieLocal movie = movieList.get(position);
        Glide.with(holder.itemMovieBinding.getRoot()).load(movie.img_backdrop).into(holder.itemMovieBinding.imgBackDrop);
        holder.itemMovieBinding.setMovie(movie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieDetailHolder extends RecyclerView.ViewHolder {

        ItemMovieBinding itemMovieBinding;

        public MovieDetailHolder(@NonNull ItemMovieBinding itemMovieBinding, OnRowClick onRowClick) {
            super(itemMovieBinding.getRoot());
            this.itemMovieBinding = itemMovieBinding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRowClick != null) {
                        onRowClick.onMovieClicked(itemMovieBinding.getMovie());
                    }
                }
            });
        }
    }
}
