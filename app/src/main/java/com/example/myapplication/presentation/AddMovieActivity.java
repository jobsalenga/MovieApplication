package com.example.myapplication.presentation;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.AddMoviesBinding;

import java.util.Calendar;


public class AddMovieActivity extends AppCompatActivity {

    private AddMovieActivityViewModel addMovieActivityViewModel;
    private DatePickerDialog datePickerDialog;
    private AddMoviesBinding addMoviesBinding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMoviesBinding = DataBindingUtil.setContentView(this, R.layout.add_movies);
        addMovieActivityViewModel = new ViewModelProvider(this).get(AddMovieActivityViewModel.class);
        onSubmitButtonClicked();
        observer();
    }

    public void onSubmitButtonClicked() {

        addMoviesBinding.submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String title = addMoviesBinding.inputName.getText().toString();
                String yearReleased = addMoviesBinding.inputYearReleased.getText().toString();
                String rating = addMoviesBinding.inputRating.getText().toString();
                String plot = addMoviesBinding.inputDescription.getText().toString();
                String imgbackdrop = addMoviesBinding.inputImageBackDrop.getText().toString();
                String imgposter = addMoviesBinding.inputPoster.getText().toString();
                if (!title.isEmpty() && !yearReleased.isEmpty() && !rating.isEmpty() && !imgbackdrop.isEmpty() & !imgposter.isEmpty()) {
                    addMovieActivityViewModel.saveMovies(title, yearReleased, rating, plot, imgbackdrop, imgposter);
                } else {
                    Toast.makeText(AddMovieActivity.this, "Item fields cannot be blank!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addMoviesBinding.addmovieBackbutton.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        addMoviesBinding.datePicker.setOnClickListener((v -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

            datePickerDialog = new DatePickerDialog(AddMovieActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month + 1;
                    String date = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);
                    Toast.makeText(AddMovieActivity.this, date , Toast.LENGTH_SHORT).show();
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }));
    }

    public void observer() {
        addMovieActivityViewModel.isMovieSaved().observe(this, isSaved -> {
            if (isSaved) {
                addMoviesBinding.inputName.setText("");
                addMoviesBinding.inputYearReleased.setText("");
                addMoviesBinding.inputRating.setText("");
                addMoviesBinding.inputDescription.setText("");
                addMoviesBinding.inputImageBackDrop.setText("");
                addMoviesBinding.inputPoster.setText("");
                Toast.makeText(this, "Movie added succesfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Network Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}