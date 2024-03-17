package com.example.myapplication;

public class MainModel {
    String name, date, type;
    int amount;


    MainModel(){


    }
    public MainModel(String name, String date, String type, int amount) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
