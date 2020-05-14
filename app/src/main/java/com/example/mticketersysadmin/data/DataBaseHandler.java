package com.example.mticketersysadmin.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.mticketersysadmin.Tocken_List;
import com.example.mticketersysadmin.model.MovieData;
import com.example.mticketersysadmin.model.TockenData;
import com.example.mticketersysadmin.model.UserData;
import com.example.mticketersysadmin.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {
    Context context;
    public DataBaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_MOVIE_TABLE = "CREATE TABLE " + Util.TABLE_NAME_MOVIE + "("
                + Util.KEY_ID_MOVIE + " INTEGER PRIMARY KEY,"
                + Util.KEY_NAME_MOVIE +" TEXT,"
                + Util.KEY_AREA_CODE_MOVIE + " INTEGER,"
                + Util.KEY_TIME_SLOT_MOVIE +" INTEGER,"
                + Util.KEY_SEATS_MOVIES + " INTEGER,"
                + Util.KEY_PHONE_NUMBERS_MOVIE + " TEXT,"
                + Util.KEY_TIMING_MOVIE + " TEXT,"
                + Util.KEY_HALL_NAME_MOVIE + " TEXT" + ")";

        String CREATE_USER_TABLE = "CREATE TABLE " + Util.TABLE_NAME_USERS + "("
                + Util.KEY_ID_USERS + " INTEGER PRIMARY KEY,"
                + Util.KEY_PHONE_NUMBER_USERS +" TEXT,"
                + Util.KEY_AREA_CODE_USERS + " INTEGER" + ")";

        String CREATE_TOCKEN_TABLE = "CREATE TABLE " + Util.TABLE_NAME_TOCKEN + "("
                + Util.KEY_ID_TOCKEN + " INTEGER PRIMARY KEY,"
                + Util.KEY_MOVIE_ID_TOCKEN +" INTEGER,"
                + Util.KEY_USER_ID_TOCKEN +" INTEGER,"
                + Util.KEY_NUMBER_OF_SEATS_TOCKEN + " INTEGER" + ")";

        sqLiteDatabase.execSQL(CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_TOCKEN_TABLE);

        Log.d("dbCHECK", "onCreate: TABLE CRATED");



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + Util.TABLE_NAME_USERS;
        String DROP_TABLE_MOVIE = "DROP TABLE IF EXISTS " + Util.TABLE_NAME_MOVIE;
        String DROP_TABLE_TOCKEN = "DROP TABLE IF EXISTS " + Util.TABLE_NAME_TOCKEN;
        sqLiteDatabase.execSQL(DROP_TABLE_USER, new String[]{Util.DATABASE_NAME});
        sqLiteDatabase.execSQL(DROP_TABLE_MOVIE, new String[]{Util.DATABASE_NAME});
        sqLiteDatabase.execSQL(DROP_TABLE_TOCKEN, new String[]{Util.DATABASE_NAME});
        onCreate(sqLiteDatabase);
    }
    //Function Related to Tocken
    public long add_tocken(TockenData tockenData){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util.KEY_MOVIE_ID_TOCKEN,tockenData.getMovie_id());
        values.put(Util.KEY_USER_ID_TOCKEN,tockenData.getUser_id());
        values.put(Util.KEY_NUMBER_OF_SEATS_TOCKEN,tockenData.getSeats());

        long id = sqLiteDatabase.insert(Util.TABLE_NAME_TOCKEN,null,values);
        Log.d("INSERTED", "add_user: ");
        sqLiteDatabase.close();
        return id;
    }

    public int tocken_id(int user_id){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME_TOCKEN,new String[]{Util.KEY_ID_TOCKEN,Util.KEY_MOVIE_ID_TOCKEN,Util.KEY_USER_ID_TOCKEN,
                Util.KEY_NUMBER_OF_SEATS_TOCKEN}
                ,Util.KEY_USER_ID_TOCKEN + "=?",new String[]{String.valueOf(user_id)},null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.KEY_ID_USERS)));

    }

    public List<TockenData> getAllTockens(){

        List<TockenData> tockenDataList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME_TOCKEN,new String[]{Util.KEY_ID_TOCKEN,Util.KEY_MOVIE_ID_TOCKEN,Util.KEY_USER_ID_TOCKEN,
                Util.KEY_NUMBER_OF_SEATS_TOCKEN}
                ,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                TockenData tockenData = new TockenData();
                tockenData.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.KEY_ID_TOCKEN))));
                tockenData.setMovie_id(cursor.getInt(cursor.getColumnIndex(Util.KEY_MOVIE_ID_TOCKEN)));
                tockenData.setUser_id(cursor.getInt(cursor.getColumnIndex(Util.KEY_USER_ID_TOCKEN)));
                tockenData.setSeats(cursor.getInt(cursor.getColumnIndex(Util.KEY_NUMBER_OF_SEATS_TOCKEN)));

                tockenDataList.add(tockenData);
            }while (cursor.moveToNext());
        }
        return tockenDataList;

    }

    public int getTockenCount(){
        String Getcount = "SELECT * FROM " + Util.TABLE_NAME_TOCKEN;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Getcount,null);

        return cursor.getCount();
    }


    //Functions related to users
    public void add_user(UserData userData){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(Util.KEY_ID_USERS,userData.getId());
        values.put(Util.KEY_AREA_CODE_USERS,userData.getArea_Code());
        values.put(Util.KEY_PHONE_NUMBER_USERS,userData.getPhone_Number());

        sqLiteDatabase.insert(Util.TABLE_NAME_USERS,null,values);
        Log.d("INSERTED", "add_user: ");
        sqLiteDatabase.close();
    }

    public int user_area(String phone_num){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int code = 0;
        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME_USERS, new String[]{Util.KEY_ID_USERS,Util.KEY_PHONE_NUMBER_USERS,Util.KEY_AREA_CODE_USERS},
                Util.KEY_PHONE_NUMBER_USERS + "=?", new String[]{String.valueOf(phone_num)},null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                code = cursor.getInt(cursor.getColumnIndex(Util.KEY_AREA_CODE_MOVIE));
            }while (cursor.moveToNext());
        }
        return code;
    }
    public boolean user_reg(String phone_num){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME_USERS, new String[]{Util.KEY_ID_USERS,Util.KEY_PHONE_NUMBER_USERS,Util.KEY_AREA_CODE_USERS},
                Util.KEY_PHONE_NUMBER_USERS + "=?", new String[]{String.valueOf(phone_num)},null,null,null,null);

        return cursor.getCount() > 0;
    }
    public List<UserData> getAllUsers(){

        List<UserData> userDataList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME_USERS,new String[]{Util.KEY_ID_USERS,Util.KEY_AREA_CODE_USERS,Util.KEY_PHONE_NUMBER_USERS}
        ,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                UserData userData = new UserData();
                userData.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.KEY_ID_USERS))));
                userData.setArea_Code(cursor.getInt(cursor.getColumnIndex(Util.KEY_AREA_CODE_USERS)));
                userData.setPhone_Number(cursor.getString(cursor.getColumnIndex(Util.KEY_PHONE_NUMBER_USERS)));

                userDataList.add(userData);
            }while (cursor.moveToNext());
        }
        return userDataList;

    }

    public void deleteUser(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(Util.TABLE_NAME_USERS,Util.KEY_ID_USERS +"=?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    public int getUserID(String phone){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME_USERS,new String[]{Util.KEY_ID_USERS,Util.KEY_AREA_CODE_USERS,Util.KEY_AREA_CODE_USERS}
                ,Util.KEY_PHONE_NUMBER_USERS + "=?",new String[]{phone},null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.KEY_ID_USERS)));
    }

    public int getUserCount(){

        String Getcount = "SELECT * FROM " + Util.TABLE_NAME_USERS;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Getcount,null);

        return cursor.getCount();

    }


    //Function related to movies

    public void add_movie(MovieData movieData){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(Util.KEY_ID_USERS,userData.getId());
        values.put(Util.KEY_AREA_CODE_MOVIE,movieData.getArea_Code());
        values.put(Util.KEY_PHONE_NUMBERS_MOVIE,movieData.getPhone_Number());
        values.put(Util.KEY_HALL_NAME_MOVIE,movieData.getHall_Name());
        values.put(Util.KEY_NAME_MOVIE,movieData.getMovie_name());
        values.put(Util.KEY_SEATS_MOVIES,movieData.getSeats_Available());
        values.put(Util.KEY_TIMING_MOVIE,movieData.getTiming());
        values.put(Util.KEY_TIME_SLOT_MOVIE,movieData.getTime_Slot());

        sqLiteDatabase.insert(Util.TABLE_NAME_MOVIE,null,values);
        Log.d("INSERTED", "add_movies: ");
        sqLiteDatabase.close();
    }

    public List<MovieData> getAllMovies(){

        List<MovieData> movieDataList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME_MOVIE,new String[]{Util.KEY_ID_MOVIE,Util.KEY_NAME_MOVIE,Util.KEY_AREA_CODE_MOVIE,
                        Util.KEY_TIME_SLOT_MOVIE,Util.KEY_SEATS_MOVIES,Util.KEY_PHONE_NUMBERS_MOVIE,Util.KEY_TIMING_MOVIE,Util.KEY_HALL_NAME_MOVIE}
                ,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                MovieData movieData = new MovieData();
                movieData.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.KEY_ID_MOVIE))));
                movieData.setArea_Code(cursor.getInt(cursor.getColumnIndex(Util.KEY_AREA_CODE_MOVIE)));
                movieData.setPhone_Number(cursor.getString(cursor.getColumnIndex(Util.KEY_PHONE_NUMBERS_MOVIE)));
                movieData.setHall_Name(cursor.getString(cursor.getColumnIndex(Util.KEY_HALL_NAME_MOVIE)));
                movieData.setTime_Slot(cursor.getInt(cursor.getColumnIndex(Util.KEY_TIME_SLOT_MOVIE)));
                movieData.setMovie_name(cursor.getString(cursor.getColumnIndex(Util.KEY_NAME_MOVIE)));
                movieData.setTiming(cursor.getString(cursor.getColumnIndex(Util.KEY_TIMING_MOVIE)));
                movieData.setSeats_Available(cursor.getInt(cursor.getColumnIndex(Util.KEY_SEATS_MOVIES)));

                movieDataList.add(movieData);
            }while (cursor.moveToNext());
        }
        return movieDataList;

    }

    public int getMoviecount(){
        String Getcount = "SELECT * FROM " + Util.TABLE_NAME_MOVIE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Getcount,null);

        return cursor.getCount();
    }

    public List<String> getMovieInArea(int AreaCode){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        List<String> Full_MSG = new ArrayList<>();
        int flag = 0;
        String MSG;
       // String finMSG = null;

        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME_MOVIE,new String[]{Util.KEY_ID_MOVIE,Util.KEY_NAME_MOVIE,Util.KEY_AREA_CODE_MOVIE,
                        Util.KEY_TIME_SLOT_MOVIE,Util.KEY_SEATS_MOVIES,Util.KEY_PHONE_NUMBERS_MOVIE,Util.KEY_TIMING_MOVIE,Util.KEY_HALL_NAME_MOVIE}
                ,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                    if(AreaCode == cursor.getInt(cursor.getColumnIndex(Util.KEY_AREA_CODE_MOVIE))){
                        MSG = "MOVIE ID: " + cursor.getString(cursor.getColumnIndex(Util.KEY_ID_MOVIE)) +  "\n"  + cursor.getString(cursor.getColumnIndex(Util.KEY_NAME_MOVIE))  +
                                "-" + cursor.getString(cursor.getColumnIndex(Util.KEY_TIMING_MOVIE)) + "-" + cursor.getString(cursor.getColumnIndex(Util.KEY_HALL_NAME_MOVIE)) +
                                "\n";
                        //finMSG = TextUtils.join("\n", Collections.singleton(MSG));
                        flag = 1;
                        Full_MSG.add(MSG);
                    }
            }while (cursor.moveToNext());
        }

        if(flag == 0){
            MSG = "NO MOVIE IN YOUR AREA CODE SORRY";
            Full_MSG.add(MSG);

        }

        return Full_MSG;

    }


    public void deleteMovie(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(Util.TABLE_NAME_MOVIE,Util.KEY_ID_MOVIE +"=?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    public int updateMovie(MovieData movieData){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_AREA_CODE_MOVIE,movieData.getArea_Code());
        values.put(Util.KEY_PHONE_NUMBERS_MOVIE,movieData.getPhone_Number());
        values.put(Util.KEY_HALL_NAME_MOVIE,movieData.getHall_Name());
        values.put(Util.KEY_NAME_MOVIE,movieData.getMovie_name());
        values.put(Util.KEY_SEATS_MOVIES,movieData.getSeats_Available());
        values.put(Util.KEY_TIMING_MOVIE,movieData.getTiming());
        values.put(Util.KEY_TIME_SLOT_MOVIE,movieData.getTime_Slot());

        return sqLiteDatabase.update(Util.TABLE_NAME_MOVIE,values,Util.KEY_ID_MOVIE + "=?",new String[]{String.valueOf(movieData.getId())});

    }

    public MovieData getMovie(int id){
        MovieData movieData = new MovieData();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME_MOVIE,new String[]{Util.KEY_ID_MOVIE,Util.KEY_NAME_MOVIE,Util.KEY_AREA_CODE_MOVIE,
                        Util.KEY_TIME_SLOT_MOVIE,Util.KEY_SEATS_MOVIES,Util.KEY_PHONE_NUMBERS_MOVIE,Util.KEY_TIMING_MOVIE,Util.KEY_HALL_NAME_MOVIE}
                ,Util.KEY_ID_MOVIE + "=?",new String[]{String.valueOf(id)},null,null,null);

        if(cursor != null) {
            cursor.moveToFirst();


            movieData.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.KEY_ID_MOVIE))));
            movieData.setArea_Code(cursor.getInt(cursor.getColumnIndex(Util.KEY_AREA_CODE_MOVIE)));
            movieData.setPhone_Number(cursor.getString(cursor.getColumnIndex(Util.KEY_PHONE_NUMBERS_MOVIE)));
            movieData.setHall_Name(cursor.getString(cursor.getColumnIndex(Util.KEY_HALL_NAME_MOVIE)));
            movieData.setTime_Slot(cursor.getInt(cursor.getColumnIndex(Util.KEY_TIME_SLOT_MOVIE)));
            movieData.setMovie_name(cursor.getString(cursor.getColumnIndex(Util.KEY_NAME_MOVIE)));
            movieData.setTiming(cursor.getString(cursor.getColumnIndex(Util.KEY_TIMING_MOVIE)));
            movieData.setSeats_Available(cursor.getInt(cursor.getColumnIndex(Util.KEY_SEATS_MOVIES)));
        }

        return movieData;

    }
    public int MovieSeatCount(int id){

        MovieData movieData = new MovieData();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(Util.TABLE_NAME_MOVIE,new String[]{Util.KEY_ID_MOVIE,Util.KEY_NAME_MOVIE,Util.KEY_AREA_CODE_MOVIE,
                        Util.KEY_TIME_SLOT_MOVIE,Util.KEY_SEATS_MOVIES,Util.KEY_PHONE_NUMBERS_MOVIE,Util.KEY_TIMING_MOVIE,Util.KEY_HALL_NAME_MOVIE}
                ,Util.KEY_ID_MOVIE + "=?",new String[]{String.valueOf(id)},null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor.getInt(cursor.getColumnIndex(Util.KEY_SEATS_MOVIES));

    }

    public void MovieSeatUpdate(int id , int seats){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Util.KEY_SEATS_MOVIES,seats);
        sqLiteDatabase.update(Util.TABLE_NAME_MOVIE,values,Util.KEY_ID_MOVIE + "=?",new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }
}
