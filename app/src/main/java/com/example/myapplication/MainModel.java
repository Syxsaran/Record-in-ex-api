package com.example.myapplication;

public class MainModel {
    String name, date, type;
    int amount;

    MainModel(){

    }
    //Constructor ที่ใช้ในการกำหนดค่าของแต่ละฟิลด์ของอ็อบเจกต์
    public MainModel(String name, String date, String type, int amount) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.amount = amount;
    }
    //Getters และ Setters สำหรับการเข้าถึงและกำหนดค่าของแต่ละฟิลด์
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
