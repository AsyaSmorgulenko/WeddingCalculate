package com.example.weddingcalculator.calculate;

import com.example.weddingcalculator.Controller;
import com.example.weddingcalculator.Repository;
import com.example.weddingcalculator.dataBase.DBWorker;
import com.example.weddingcalculator.specialists.*;
import javafx.collections.FXCollections;

import java.sql.SQLException;
import java.util.List;
import static com.example.weddingcalculator.Controller.*;

public class Calculator {
    private Repository repository;
    private float priceRestaurant;
    private float priceBuffet=0;
    private float priceDecorator;
    private float pricePhotographer;
    private float priceEventHost;
    private float rezyltPriceMaterial;
    private float sum;
    private Integer numberOfPeople;
    private boolean flag=false;

    public Calculator() {
        this.repository = new DBWorker();
    }
    public float addPriceMaterial(String material) {
        float priceMaterial = 0;
        switch (material) {
            case "Серебро":
                priceMaterial = 6000;
                break;
            case "Желтое золото":
                priceMaterial = 30000;
                break;
            case "Красное золото":
                priceMaterial = 35000;
                break;
            case "Белое золото":
                priceMaterial = 40000;
                break;
        }
        rezyltPriceMaterial=priceMaterial;
        return priceMaterial;
    }
    public void setPriceDecorator(float priceDecorator) {
        this.priceDecorator = priceDecorator;
    }
    public float getPriceDecorator() {
        return priceDecorator;
    }
    public void setPriceRestaurant(float priceRestaurant) {
        this.priceRestaurant = priceRestaurant;
    }
    public float getPriceRestaurant() {
        return priceRestaurant;
    }
    public void setPriceEventHost(float priceEventHost) {
        this.priceEventHost = priceEventHost;
    }
    public float getPriceEventHost() {
        return priceEventHost;
    }
    public void setPricePhotographer(float pricePhotographer) {
        this.pricePhotographer = pricePhotographer;
    }
    public float getPricePhotographer() {
        return pricePhotographer;
    }
    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
    public float getNumberOfPeople() {
        return numberOfPeople;
    }
    public float getPriceBuffet(){
        return priceBuffet;
    }
    public void checkPriceBuffet(boolean flag) {
        if (flag){
            priceBuffet=1100;
        }
        else {
            priceBuffet=0;
        }
    }
    public float checkWedding(){
        sum=getNumberOfPeople()*getPriceRestaurant()+rezyltPriceMaterial+getPricePhotographer()+getPriceEventHost()+getPriceDecorator()+getPriceBuffet()*getNumberOfPeople();
        return sum;
    }
    public void getNameEventHost(){
        List<Person> eventHostsList;
        try {
            eventHostsList = repository.getAllPerson();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        eventHostNames = FXCollections.observableArrayList();
        for (Person person :  eventHostsList) {
            if (person instanceof  EventHost) {
                eventHostNames.add(person.getName());
            }
        }
    }
    public void getNameRestaurant(){
        List<Person> restaurantList;
        try {
            restaurantList = repository.getAllPerson();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        restaurantNames = FXCollections.observableArrayList();
        for (Person person : restaurantList) {
            if (person instanceof  Restaurant) {
                restaurantNames.add(person.getName());
            }
        }
    }
    public void getNamePhotographer(){
        List<Person> photographerList;
        try {
            photographerList = repository.getAllPerson();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        photographerNames = FXCollections.observableArrayList();
        for (Person person :  photographerList) {
            if (person instanceof  Photographer) {
                photographerNames.add(person.getName());
            }
        }
    }
    public void getNameDecorator(){
        List<Person> decoratorList;
        try {
            decoratorList = repository.getAllPerson();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        decoratorNames = FXCollections.observableArrayList();
        for (Person person:  decoratorList) {
            if (person instanceof  Decorator) {
                decoratorNames.add(person.getName());
            }
        }
    }

}
