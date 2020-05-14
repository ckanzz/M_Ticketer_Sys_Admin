package com.example.mticketersysadmin.model;

public class MovieData {
    private int Id;
    private int Area_Code;
    private String Movie_name;
    private int Time_Slot;
    private String Timing;
    private int Seats_Available;
    private String Phone_Number;
    private String Hall_Name;

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getTiming() {
        return Timing;
    }

    public void setTiming(String timing) {
        Timing = timing;
    }

    public MovieData() {
    }

    public MovieData(int area_Code, String movie_name, int time_Slot, int seats_Available, String hall_Name,String phone_Number,String timing) {
        Area_Code = area_Code;
        Phone_Number = phone_Number;
        Movie_name = movie_name;
        Timing = timing;
        Time_Slot = time_Slot;
        Seats_Available = seats_Available;
        Hall_Name = hall_Name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getArea_Code() {
        return Area_Code;
    }

    public void setArea_Code(int area_Code) {
        Area_Code = area_Code;
    }

    public String getMovie_name() {
        return Movie_name;
    }

    public void setMovie_name(String movie_name) {
        Movie_name = movie_name;
    }

    public int getTime_Slot() {
        return Time_Slot;
    }

    public void setTime_Slot(int time_Slot) {
        Time_Slot = time_Slot;
    }

    public int getSeats_Available() {
        return Seats_Available;
    }

    public void setSeats_Available(int seats_Available) {
        Seats_Available = seats_Available;
    }

    public String getHall_Name() {
        return Hall_Name;
    }

    public void setHall_Name(String hall_Name) {
        Hall_Name = hall_Name;
    }
}
