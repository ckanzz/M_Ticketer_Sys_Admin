package com.example.mticketersysadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mticketersysadmin.data.DataBaseHandler;
import com.example.mticketersysadmin.model.MovieData;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private DataBaseHandler dataBaseHandler;
    private Button Movie_Button;
    private Button Tocken_Button;
    private Button User_Button;
    private Button Add_Movie;
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
        setContentView(R.layout.activity_main);

        //Getting all the permissions from the users

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},1000);
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},0);
        }
//        int permission_check = ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS);
//        if(permission_check == PackageManager.PERMISSION_GRANTED){
//            Toast.makeText(this,"Permission Granted !!",Toast.LENGTH_SHORT).show();
//        }else{
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
//        }
        dataBaseHandler = new DataBaseHandler(this);
        //MovieData movieData = new MovieData(23,"Lord of the Rings",1,10,"PVR VELLORE","9560205795",
          //      "9:00AM");
//        dataBaseHandler.deleteMovie(1);
//        dataBaseHandler.deleteMovie(2);
//        dataBaseHandler.deleteMovie(3);
//        dataBaseHandler.deleteMovie(4);
//        dataBaseHandler.deleteMovie(5);
//        dataBaseHandler.deleteMovie(6);
        //dataBaseHandler.add_movie(movieData);
        Movie_Button = findViewById(R.id.movie_button);
        Tocken_Button = findViewById(R.id.tocken_button);
        User_Button = findViewById(R.id.user_button);
        Add_Movie = findViewById(R.id.add_movie_button);

        Movie_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataBaseHandler.getMoviecount() > 0) {
                    startActivity(new Intent(MainActivity.this, Movie_List.class));
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"EMPTY DATABASE",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Tocken_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataBaseHandler.getTockenCount() > 0){
                    startActivity(new Intent(MainActivity.this, Tocken_List.class));
                    finish();
                }else{
                    Toast.makeText(MainActivity.this,"EMPTY DATABASE",Toast.LENGTH_SHORT).show();
                }
            }
        });
        User_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dataBaseHandler.getUserCount() > 0){
                    startActivity(new Intent(MainActivity.this, Users_List.class));
                    finish();
                }else{
                    Toast.makeText(MainActivity.this,"EMPTY DATABASE",Toast.LENGTH_SHORT).show();
                }

            }
        });

        Add_Movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopup();
            }

            private void createPopup() {
                builder = new AlertDialog.Builder(MainActivity.this);
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
                        startActivity(new Intent(MainActivity.this,Movie_List.class));
                        finish();
                    }
                },1400);

                Snackbar.make(view,"Item Saved",Snackbar.LENGTH_SHORT).show();
            }
        });

    }



    //Checking if the permission have been granted

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        switch (requestCode){
//            case 1000:
//                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    Toast.makeText(this,"Permission Granted !!",Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(this,"Permission Denied Cannot System Failure !!",Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 0:
//                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    Toast.makeText(this,"Permission Granted !!",Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(this,"Permission Denied Cannot System Failure !!",Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }

        if(requestCode == 1000){

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted !!",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Permission Denied Cannot System Failure !!",Toast.LENGTH_SHORT).show();
            }

        }

        if(requestCode == 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted !!",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Permission Denied Cannot System Failure !!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
