package com.example.weddingcalculator.specialists;

import com.example.weddingcalculator.specialists.Person;

public class Photographer extends Person {
    private String surname;

    public Photographer(int id, String name,String surname, float price,String contacts) {
        super(id,name, price, contacts);
        this.surname=surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
