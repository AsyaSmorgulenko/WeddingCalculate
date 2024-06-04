package com.example.weddingcalculator.specialists;

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
            statement.execute("CREATE TABLE if not exists 'specialists'('id' INTEGER PRIMARY KEY, 'type' text);");
            statement.execute("CREATE TABLE if not exists 'photographer'('id' INTEGER PRIMARY KEY, 'name' text,'surname' text,'price' float,'contacts' text,foreign key (id) references specialists(id));");
            statement.execute("CREATE TABLE if not exists 'eventHost'('id' INTEGER PRIMARY KEY, 'name' text,'surname' text,'price' float,'contacts' text,foreign key (id) references specialists(id));");
            System.out.println("Таблицы успешно созданы");
            statement.execute("INSERT INTO 'specialists'('id','type') VALUES (45,'Photographer')");
            statement.execute("INSERT INTO 'specialists'('id','type') VALUES (46,'Photographer')");
            statement.execute("INSERT INTO 'specialists'('id','type') VALUES (32,'EventHost')");
            statement.execute("INSERT INTO 'specialists'('id','type') VALUES (33,'EventHost')");

            // Insert two rows into the 'photographer' table
            statement.execute("INSERT INTO 'photographer'('id','name', 'surname', 'price', 'contacts') VALUES (45,'John', 'Doe', 1000.0, 'johndoe@example.com')");
            statement.execute("INSERT INTO 'photographer'('id','name', 'surname', 'price', 'contacts') VALUES (46,'Jane', 'Smith', 1200.0, 'janesmith@example.com')");

            // Insert two rows into the 'eventHost' table
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
                "eventHost.id as idH,eventHost.name as nameH,eventHost.surname as surnameh,eventHost.price as priceH,eventHost.contacts as contactsh\n" +
                "FROM specialists\n" +
                "LEFT JOIN photographer ON photographer.id = specialists.id\n" +
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
                statement.executeUpdate("INSERT INTO photographer (id, name, surname, price, contacts) VALUES (" + person.hashCode() + ", '" + photographer.getName() + "', '" + photographer.getSurname() + "', " + photographer.getPrice() + ", '" + photographer.getContacts() + "')");
                statement.executeUpdate("insert into specialists values ("+person.hashCode()+", 'Photographer');");
                System.out.println("Фотограф записан");
            } else{
                EventHost eventHost = (EventHost) person;
                statement.executeUpdate("INSERT INTO eventhost (id, name, surname, price, contacts) VALUES (" + person.hashCode() + ", '" + eventHost.getName() + "', '" + eventHost.getSurname() + "', " + eventHost.getPrice() + ", '" + eventHost.getContacts() + "')");
                statement.executeUpdate("insert into specialists values ("+person.hashCode()+", 'EventHost');");
                System.out.println("Ведущий записан");
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
