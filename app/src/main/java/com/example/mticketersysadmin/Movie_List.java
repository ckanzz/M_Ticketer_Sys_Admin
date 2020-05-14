package com.example.mticketersysadmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mticketersysadmin.adapter.RecyclerViewAdapterMovie;
import com.example.mticketersysadmin.data.DataBaseHandler;
import com.example.mticketersysadmin.model.MovieData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Movie_List extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private DataBaseHandler dataBaseHandler;
    private List<MovieData> movieDataList;
    private RecyclerViewAdapterMovie recyclerViewAdapterMovie;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    private EditText MovieNameEdit;
    private EditText MovieAreaEdit;
    private EditText MovieTimeEdit;
    private EditText MovieSeatEdit;
    private EditText MoviePhoneEdit;
    private EditText MovieTimingsEdit;
    private EditText MovieHallEdit;
    private Button SaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__list);

        recyclerView = findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);

        dataBaseHandler = new DataBaseHandler(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieDataList = new ArrayList<>();
        movieDataList = dataBaseHandler.getAllMovies();

        recyclerViewAdapterMovie = new RecyclerViewAdapterMovie(this,movieDataList);
        recyclerView.setAdapter(recyclerViewAdapterMovie);
        recyclerViewAdapterMovie.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopup();
            }
        });




    }

    private void createPopup() {
        builder = new AlertDialog.Builder(this);
        View view1 = getLayoutInflater().inflate(R.layout.pop_up,null);

        MovieNameEdit = view1.findViewById(R.id.movie_name);
        MovieAreaEdit = view1.findViewById(R.id.area_code);
        MovieTimeEdit = view1.findViewById(R.id.slot_time);
        MovieSeatEdit = view1.findViewById(R.id.seats);
        MoviePhoneEdit = view1.findViewById(R.id.phone_number);
        MovieTimingsEdit = view1.findViewById(R.id.timing);
        MovieHallEdit = view1.findViewById(R.id.hall_name);
        SaveButton = view1.findViewById(R.id.save_Button);

        builder.setView(view1);
        alertDialog = builder.create();
        alertDialog.show();

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    saveMovie(view);
            }
        });



    }

    private void saveMovie(View view) {

        MovieData movieData = new MovieData();

        movieData.setMovie_name(MovieNameEdit.getText().toString().trim());
        movieData.setArea_Code(Integer.parseInt(MovieAreaEdit.getText().toString().trim()));
        movieData.setTime_Slot(Integer.parseInt(MovieTimeEdit.getText().toString().trim()));
        movieData.setSeats_Available(Integer.parseInt(MovieSeatEdit.getText().toString().trim()));
        movieData.setPhone_Number(MoviePhoneEdit.getText().toString().trim());
        movieData.setTiming(MovieTimingsEdit.getText().toString().trim());
        movieData.setHall_Name(MovieHallEdit.getText().toString().trim());

        dataBaseHandler.add_movie(movieData);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                startActivity(new Intent(Movie_List.this,Movie_List.class));
                finish();
            }
        },1400);

        Snackbar.make(view,"Item Saved",Snackbar.LENGTH_SHORT).show();

    }
}
