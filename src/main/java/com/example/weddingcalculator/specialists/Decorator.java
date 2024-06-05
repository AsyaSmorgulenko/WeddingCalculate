package com.example.weddingcalculator.specialists;

public class Decorator extends Person{
    private String viewOfFlowers;
    public Decorator(int id, String name,String viewOfFlowers, float price, String contacts) {
        super(id, name, price, contacts);
        this.viewOfFlowers=viewOfFlowers;
    }
    public String getViewOfFlowers() {
        return viewOfFlowers;
    }

    public void setViewOfFlowers(String viewOfFlowers) {
        this.viewOfFlowers=viewOfFlowers;
    }
}
