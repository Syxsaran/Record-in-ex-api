package com.example.myapplication;

public class MainModel {
    String name,date,type,lurl;


    MainModel(){


    }
    public MainModel(String name, String date, String type, String lurl) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.lurl = lurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLurl() {
        return lurl;
    }

    public void setLurl(String lurl) {
        this.lurl = lurl;
    }
}
