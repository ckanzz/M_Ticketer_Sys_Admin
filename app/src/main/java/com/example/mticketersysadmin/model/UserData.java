package com.example.mticketersysadmin.model;

public class UserData {
        private int id;
        private String Phone_Number;
        private int Area_Code;

    public UserData(String phone_Number, int area_Code) {
        Phone_Number = phone_Number;
        Area_Code = area_Code;
    }

    public UserData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public int getArea_Code() {
        return Area_Code;
    }

    public void setArea_Code(int area_Code) {
        Area_Code = area_Code;
    }
}
