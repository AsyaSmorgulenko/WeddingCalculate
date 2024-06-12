package com.example.weddingcalculator.view;

import com.example.weddingcalculator.specialists.Decorator;
import com.example.weddingcalculator.specialists.EventHost;
import com.example.weddingcalculator.specialists.Person;
import com.example.weddingcalculator.specialists.Restaurant;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public interface Repository {
        ArrayList<Person> getAllPerson() throws SQLException;
        void removePerson(Person person);

        void addPerson(Person person);
        void editPeople(Person person,String type) throws SQLException;
        void setPerson(Person person) throws SQLException;

        ArrayList<String> getAllRestaurantName(String rezyltTextName) throws SQLException;

        ArrayList<String> getAllPhotographerName(String rezyltTextName) throws SQLException;

        ArrayList<String> getAllEventHostName(String rezyltTextName) throws SQLException;


        ArrayList<String> getAllDecoratorName(String rezyltTextName) throws SQLException;

        float getRestaurantPrice(String restaurantName) throws SQLException;

        float getPhotographerPrice(String photographerName) throws SQLException;

        float getEventHostPrice(String eventHostName) throws SQLException;

        float getDecoratorPrice(String decoratorName) throws SQLException;



}
