package com.example.weddingcalculator.specialists;

import com.example.weddingcalculator.specialists.Person;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Repository {
    ArrayList<Person> getAllPerson() throws SQLException;
    void removePerson(Person person);

    void addPerson(Person person);
}
