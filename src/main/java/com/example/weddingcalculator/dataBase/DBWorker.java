package com.example.weddingcalculator.dataBase;

import com.example.weddingcalculator.Repository;
import com.example.weddingcalculator.specialists.*;

import java.sql.*;
import java.util.ArrayList;

public class DBWorker implements Repository {
    private static String jdbUrl = "jdbc:sqlite:C:\\SQLLite\\sqlite-tools-win-x64-3450200\\weddingagency.db";
    private static Connection connection;;
    ResultSet resultSet = null;
    public static void initDB(){
        try {
            connection = DriverManager.getConnection(jdbUrl);
            createTable();
        }
        catch (SQLException e){
            System.out.println("Ошибка с подключением бд");
            e.printStackTrace();
        }
    }

    public Connection getDbConnection() {
        String jdbUrl = "jdbc:sqlite:C:\\SQLLite\\sqlite-tools-win-x64-3450200\\weddingagency.db";
        try {
            connection = DriverManager.getConnection(jdbUrl);
        } catch (SQLException e) {
            System.out.println("Ошибка с подключением бд");
            e.printStackTrace();
        }
        return connection;
    }

    public static void createTable(){
        try {
            Statement statement=connection.createStatement();
            statement.execute("DROP TABLE 'specialists'");
            statement.execute("DROP TABLE 'photographer'");
            statement.execute("DROP TABLE 'eventHost'");
            statement.execute("DROP TABLE 'visagist'");
            statement.execute("DROP TABLE 'decorator'");
            statement.execute("DROP TABLE 'restaurant'");
            statement.execute("CREATE TABLE if not exists 'specialists'('id' INTEGER PRIMARY KEY, 'type' text);");
            statement.execute("CREATE TABLE if not exists 'photographer'('id' INTEGER PRIMARY KEY, 'name' text,'surname' text,'price' float,'contacts' text,foreign key (id) references specialists(id));");
            statement.execute("CREATE TABLE if not exists 'eventHost'('id' INTEGER PRIMARY KEY, 'name' text,'surname' text,'price' float,'contacts' text,foreign key (id) references specialists(id));");
            statement.execute("CREATE TABLE if not exists 'visagist'('id' INTEGER PRIMARY KEY, 'name' text,'surname' text,'price' float,'contacts' text,foreign key (id) references specialists(id));");
            statement.execute("CREATE TABLE if not exists 'decorator'('id' INTEGER PRIMARY KEY, 'name' text,'surname' text,'price' float,'contacts' text,foreign key (id) references specialists(id));");
            statement.execute("CREATE TABLE if not exists 'restaurant'('id' INTEGER PRIMARY KEY, 'name' text,'surname' text,'price' float,'contacts' text,foreign key (id) references specialists(id));");
            System.out.println("Таблицы успешно созданы");
            statement.execute("INSERT INTO 'specialists'('id','type') VALUES (45,'Photographer')");
            statement.execute("INSERT INTO 'specialists'('id','type') VALUES (46,'Photographer')");
            statement.execute("INSERT INTO 'specialists'('id','type') VALUES (32,'EventHost')");
            statement.execute("INSERT INTO 'specialists'('id','type') VALUES (33,'EventHost')");
            statement.execute("INSERT INTO 'photographer'('id','name', 'surname', 'price', 'contacts') VALUES (45,'John', 'Doe', 1000.0, 'johndoe@example.com')");
            statement.execute("INSERT INTO 'photographer'('id','name', 'surname', 'price', 'contacts') VALUES (46,'Jane', 'Smith', 1200.0, 'janesmith@example.com')");
            statement.execute("INSERT INTO 'eventHost'('id','name', 'surname', 'price', 'contacts') VALUES (32,'Alice', 'Cooper', 1500.0, 'alicecooper@example.com')");
            statement.execute("INSERT INTO 'eventHost'('id','name', 'surname', 'price', 'contacts') VALUES (33,'Bob', 'Marley', 1800.0, 'bobmarley@example.com')");

            System.out.println("Data successfully inserted");
            statement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public ArrayList<Person> getAllPerson() throws SQLException {
        connection = DriverManager.getConnection(jdbUrl);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select count(id) from specialists;");
        int col = result.getInt(1);
        result.close();
        ArrayList<Person> list = new ArrayList<>();
        String query = "SELECT specialists.type, specialists.id, \n" +
                "photographer.id,photographer.name,photographer.surname,photographer.price,photographer.contacts, \n" +
                "eventHost.id as idH,eventHost.name as nameH,eventHost.surname as surnameh,eventHost.price as priceH,eventHost.contacts as contactsh,\n" +
                "visagist.id as idV,visagist.name as nameV,visagist.surname as surnameV,visagist.price as priceV,visagist.contacts as contactsV,\n"+
                "decorator.id as idD,decorator.name as nameD,decorator.surname as surnameD,decorator.price as priceD,decorator.contacts as contactsD,\n"+
                "restaurant.id as idR,restaurant.name as nameR,restaurant.surname as surnameR,restaurant.price as priceR,restaurant.contacts as contactsR\n"+
                "FROM specialists\n" +
                "LEFT JOIN photographer ON photographer.id = specialists.id\n" +
                "LEFT JOIN decorator ON decorator.id = specialists.id\n" +
                "LEFT JOIN visagist ON visagist.id = specialists.id\n" +
                "LEFT JOIN restaurant ON restaurant.id = specialists.id\n" +
                "LEFT JOIN eventHost ON eventHost.id = specialists.id;";

        ResultSet resultSet = statement.executeQuery(query);
        int i = 0;
        while (i < col) {
            resultSet.next();
            switch (resultSet.getString("type")) {
                case "Photographer":
                    list.add(new Photographer(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getFloat("price"),
                            resultSet.getString("contacts")));
                    break;
                case "EventHost":
                    list.add(new EventHost(resultSet.getInt("idh"),
                            resultSet.getString("nameh"),
                            resultSet.getString("surnameh"),
                            resultSet.getFloat("priceh"),
                            resultSet.getString("contactsh")));
                    break;
                case "Visagiste":
                    list.add(new Visagiste(resultSet.getInt("idV"),
                            resultSet.getString("nameV"),
                            resultSet.getString("surnameV"),
                            resultSet.getFloat("priceV"),
                            resultSet.getString("contactsV")));
                    break;
                case "Restaurant":
                    list.add(new Restaurant(resultSet.getInt("idR"),
                            resultSet.getString("nameR"),
                            resultSet.getString("surnameR"),
                            resultSet.getFloat("priceR"),
                            resultSet.getString("contactsR")));
                    break;
                case "Decorator":
                    list.add(new Decorator(resultSet.getInt("idD"),
                            resultSet.getString("nameD"),
                            resultSet.getString("surnameD"),
                            resultSet.getFloat("priceD"),
                            resultSet.getString("contactsD")));
                    break;
            }
            i++;
        }
        connection.close();
        statement.close();
        resultSet.close();
        return list;
    }
    @Override
    public void removePerson(Person person) {
        try {
            connection = DriverManager.getConnection(jdbUrl);
            Statement statement = connection.createStatement();
            if (person instanceof Photographer) {
                statement.executeUpdate("delete from photographer where id = " + person.getId());
                statement.executeUpdate("delete from specialists where id = " + person.getId());
            }
            else if (person instanceof Visagiste) {
                statement.executeUpdate("delete from visagist where id = " + person.getId());
                statement.executeUpdate("delete from specialists where id = " + person.getId());
            }
            else if (person instanceof Decorator) {
                statement.executeUpdate("delete from decorator where id = " + person.getId());
                statement.executeUpdate("delete from specialists where id = " + person.getId());
            }
            else if (person instanceof Restaurant) {
                statement.executeUpdate("delete from restaurant where id = " + person.getId());
                statement.executeUpdate("delete from specialists where id = " + person.getId());
            }
            else {
                statement.executeUpdate("delete from eventHost where id = " + person.getId());
                statement.executeUpdate("delete from specialists where id = " + person.getId());
            }
            System.out.println("Данные успешно удалены");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPerson(Person person) {
        try {
            connection = DriverManager.getConnection(jdbUrl);
            Statement statement = connection.createStatement();
            // Вставка данных в соответствующую таблицу специалиста
            if (person instanceof Photographer) {
                Photographer photographer = (Photographer) person;
                statement.executeUpdate("INSERT INTO photographer (id, name, surname, price, contacts) VALUES (" + photographer.getId() + ", '" + photographer.getName() + "', '" + photographer.getSurname() + "', " + photographer.getPrice() + ", '" + photographer.getContacts() + "')");
                statement.executeUpdate("insert into specialists values ("+photographer.getId()+", 'Photographer');");
                System.out.println("Фотограф записан");
            } else if (person instanceof EventHost){
                EventHost eventHost = (EventHost) person;
                statement.executeUpdate("INSERT INTO eventhost (id, name, surname, price, contacts) VALUES (" + eventHost.getId() + ", '" + eventHost.getName() + "', '" + eventHost.getSurname() + "', " + eventHost.getPrice() + ", '" + eventHost.getContacts() + "')");
                statement.executeUpdate("insert into specialists values ("+eventHost.getId()+", 'EventHost');");
                System.out.println("Ведущий записан");
            }
            else if (person instanceof Visagiste){
                Visagiste visagiste = (Visagiste) person;
                statement.executeUpdate("INSERT INTO visagist (id, name,surname, price, contacts) VALUES (" + visagiste.getId()+ ", '" + visagiste.getName() + "', '" + visagiste.getSurname() + "', " + visagiste.getPrice() + ", '" + visagiste.getContacts() + "')");
                statement.executeUpdate("insert into specialists values ("+visagiste.getId()+", 'Visagiste');");
                System.out.println("Визажист записан");
            }
            else if (person instanceof Decorator){
                Decorator decorator = (Decorator) person;
                statement.executeUpdate("INSERT INTO decorator (id, name, surname, price, contacts) VALUES (" + decorator.getId() + ", '" + decorator.getName() + "', '" + decorator.getSurname() + "', " + decorator.getPrice() + ", '" + decorator.getContacts() + "')");
                statement.executeUpdate("insert into specialists values ("+decorator.getId()+", 'Decorator');");
                System.out.println("Декоратор записан");
            }
            else{
                Restaurant restaurant = (Restaurant) person;
                statement.executeUpdate("INSERT INTO restaurant (id, name, surname, price, contacts) VALUES (" + restaurant.getId() + ", '" + restaurant.getName() + "', '" + restaurant.getSurname() + "', " + restaurant.getPrice() + ", '" + restaurant.getContacts() + "')");
                statement.executeUpdate("insert into specialists values ("+restaurant.getId()+", 'Restaurant');");
                System.out.println("Ресторан записан");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editPeople(Person person,String type) throws SQLException {
        String query = "UPDATE specialist SET ";
        switch (type) {
            case "Photographer":
                query += "price = ?, contacts = ? WHERE id = ?";
                break;
            case "EventHost":
                query += "price = ?, contacts = ? WHERE id = ?";
                break;
            case "Visagiste":
                query += "price = ?, contacts = ? WHERE id = ?";
                break;
            case "Restaurant":
                query += "price = ?, contacts = ? WHERE id = ?";
                break;
            case "Decorator":
                query += "price = ?, contacts = ? WHERE id = ?";
                break;
        }
        connection = DriverManager.getConnection(jdbUrl);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setFloat(1, person.getPrice());
        statement.setString(2, person.getContacts());
        statement.setInt(3, person.getId());
        statement.executeUpdate();
        connection.close();
        statement.close();
    }

    @Override
    public void setPerson(Person person) throws SQLException {
    }
    @Override
    public ArrayList<String> getAllRestaurantName(String rezyltTextName) throws SQLException {
        connection = DriverManager.getConnection(jdbUrl);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select count(id) from specialists where type = 'Restaurant';");
        int col = result.getInt(1);
        result.close();
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT name FROM restaurant";
        ResultSet resultSet = statement.executeQuery(query);
        int i = 0;
        while (i < col) {
            resultSet.next();
            list.add(resultSet.getString("name"));
            i++;
        }
        connection.close();
        statement.close();
        resultSet.close();
        return list;
    }
    @Override
    public ArrayList<String> getAllPhotographerName(String rezyltTextName) throws SQLException {
        connection = DriverManager.getConnection(jdbUrl);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select count(id) from specialists where type = 'Photographer';");
        int col = result.getInt(1);
        result.close();
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT name FROM photographer";
        ResultSet resultSet = statement.executeQuery(query);
        int i = 0;
        while (i < col) {
            resultSet.next();
            list.add(resultSet.getString("name"));
            i++;
        }
        connection.close();
        statement.close();
        resultSet.close();
        return list;
    }

    @Override
    public ArrayList<String> getAllEventHostName(String rezyltTextName) throws SQLException {
        connection = DriverManager.getConnection(jdbUrl);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select count(id) from specialists where type = 'EventHost';");
        int col = result.getInt(1);
        result.close();
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT name FROM eventHost";
        ResultSet resultSet = statement.executeQuery(query);
        int i = 0;
        while (i < col) {
            resultSet.next();
            list.add(resultSet.getString("name"));
            i++;
        }
        connection.close();
        statement.close();
        resultSet.close();
        return list;
    }
    @Override
    public ArrayList<String> getAllDecoratorName(String rezyltTextName) throws SQLException {
        connection = DriverManager.getConnection(jdbUrl);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select count(id) from specialists where type = 'Decorator';");
        int col = result.getInt(1);
        result.close();
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT name FROM decorator";
        ResultSet resultSet = statement.executeQuery(query);
        int i = 0;
        while (i < col) {
            resultSet.next();
            list.add(resultSet.getString("name"));
            i++;
        }
        connection.close();
        statement.close();
        resultSet.close();
        return list;
    }
    @Override
    public float getRestaurantPrice(String restaurantName) throws SQLException {
        float price = 0;
        try {
            Connection connection = DriverManager.getConnection(jdbUrl);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT price FROM restaurant WHERE name = '" + restaurantName + "'");
            if (resultSet.next()) {
                price = resultSet.getFloat("price");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }
    @Override
    public float getPhotographerPrice(String photographerName) throws SQLException {
        float price = 0;
        try {
            Connection connection = DriverManager.getConnection(jdbUrl);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT price FROM photographer WHERE name = '" + photographerName + "'");
            if (resultSet.next()) {
                price = resultSet.getFloat("price");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }
    @Override
    public float getEventHostPrice(String eventHostName) throws SQLException {
        float price = 0;
        try {
            Connection connection = DriverManager.getConnection(jdbUrl);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT price FROM eventHost WHERE name = '" + eventHostName + "'");
            if (resultSet.next()) {
                price = resultSet.getFloat("price");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }
    @Override
    public float getDecoratorPrice(String decoratorName) throws SQLException {
        float price = 0;
        try {
            Connection connection = DriverManager.getConnection(jdbUrl);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT price FROM decorator WHERE name = '" + decoratorName + "'");
            if (resultSet.next()) {
                price = resultSet.getFloat("price");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }
}
