package com.example.weddingcalculator.specialists;

public abstract class Person {
    protected String name;
    protected float price;
    protected String contacts;
    protected Integer id;

    public Person(int id,String name,float price,String contacts){
        this.id=id;
        this.name=name;
        this.price=price;
        this.contacts=contacts;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public float getPrice(){
        return price;
    }
    public void setPrice(float price){
        this.price=price;
    }

    public String getContacts(){
        return contacts;
    }
    public void setContacts(String contacts){
        this.contacts=contacts;
    }
    public float getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
}
