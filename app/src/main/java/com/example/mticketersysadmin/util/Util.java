package com.example.mticketersysadmin.util;

public class Util {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mticket_db";
    public static final String TABLE_NAME_USERS = "users";
    public static final String TABLE_NAME_MOVIE = "movie";
    public static final String TABLE_NAME_TOCKEN = "tokens";
    //column names for users;

    public static final String KEY_ID_USERS = "Id";
    public static final String KEY_PHONE_NUMBER_USERS = "Phone_Number";
    public static final String KEY_AREA_CODE_USERS = "Area_Code";

    //column names for tocken

    public static final String KEY_ID_TOCKEN = "Id";
    public static final String KEY_MOVIE_ID_TOCKEN = "Movie_ID";
    public static final String KEY_USER_ID_TOCKEN = "User_ID";
    public static final String KEY_NUMBER_OF_SEATS_TOCKEN = "No_Seats";

    //column names for movies

    public static final String KEY_ID_MOVIE = "Id";
    public static final String KEY_AREA_CODE_MOVIE = "Area_code";
    public static final String KEY_PHONE_NUMBERS_MOVIE = "Phone_number";
    public static final String KEY_SEATS_MOVIES = "No_Of_Seats";
    public static final String KEY_TIME_SLOT_MOVIE = "Time_Slot";
    public static final String KEY_HALL_NAME_MOVIE = "Hall_Name";
    public static final String KEY_NAME_MOVIE = "Movie_Name";
    public static final String KEY_TIMING_MOVIE = "Timings";

}
