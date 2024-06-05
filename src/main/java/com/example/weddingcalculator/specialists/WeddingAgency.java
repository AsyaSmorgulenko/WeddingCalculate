package com.example.weddingcalculator.specialists;

import com.example.weddingcalculator.dataBase.Repository;

import java.sql.SQLException;
import java.util.ArrayList;

public class WeddingAgency {
    private ArrayList<Person> list=new ArrayList<>();
    protected Repository repository;
    public WeddingAgency(Repository repository) throws SQLException {
        this.repository=repository;
        ArrayList<Person> list1 = repository.getAllPerson();
        if (!(list1==null))
            list.addAll(list1);
    }

    public int getCount(){
        return this.list.size();
    }
    public void add(Person person){
        list.add(person);
        repository.addPerson(person);
    }
    public ArrayList<Person> getAllPerson1() throws SQLException {
        return repository.getAllPerson();

    }

    public Person getPerson(int index){

        return list.get(index);
    }
    public void remove(int ind){
        Person person = this.list.get(ind);
        this.list.remove(ind);
        this.repository.removePerson(person);
    }

    /*public void deleteAll() throws SQLException {
        this.repository.clear();
    }*/
}
