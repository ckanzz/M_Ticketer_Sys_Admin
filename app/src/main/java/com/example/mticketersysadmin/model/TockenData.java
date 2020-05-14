package com.example.mticketersysadmin.model;

public class TockenData {
    private int id;
    private int movie_id;
    private int User_id;
    private int Seats;

    public TockenData(int movie_id, int user_id, int seats) {
        this.movie_id = movie_id;
        User_id = user_id;
        Seats = seats;
    }

    public TockenData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public int getSeats() {
        return Seats;
    }

    public void setSeats(int seats) {
        Seats = seats;
    }
}
