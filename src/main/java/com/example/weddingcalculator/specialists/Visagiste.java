package com.example.weddingcalculator.specialists;

public class Visagiste extends Person{
    private String location;
    public Visagiste(int id, String name,String location, float price, String contacts) {
        super(id, name, price, contacts);
        this.location=location;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location=location;
    }
}
