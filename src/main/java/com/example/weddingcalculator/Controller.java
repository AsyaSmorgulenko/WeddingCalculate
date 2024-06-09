package com.example.weddingcalculator;

import com.example.weddingcalculator.calculate.Calculator;
import com.example.weddingcalculator.dataBase.DBWorker;
import com.example.weddingcalculator.specialists.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Repository repository;
    public Controller() {
        this.repository = new DBWorker();
    }
    @FXML
    private ComboBox<String> combox;
    @FXML
    private ComboBox<String> comboBoxMaterial;
    @FXML
    private ComboBox<String> comboBoxEventHost;
    @FXML
    private ComboBox<String> comboBoxDecorator;
    @FXML
    private TextField enteringOfPeople;
    @FXML
    public Button editButton;
    @FXML
    private Button addButton;
    @FXML
    private Button updateAll;
    @FXML
    public TableView<Person> TableSpecialists;
    @FXML
    public TableColumn<Person,Integer> idColumn;
    @FXML
    public TableColumn<Person,String> nameColumn;
    @FXML
    public TableColumn<Person,String> surnameColumn;
    @FXML
    public TableColumn<Person,Float> priceColumn;
    @FXML
    public TableColumn<Person,String> contactsColumn;
    @FXML
    private Button removeButton;
    @FXML
    private Button buttonRezylt;
    @FXML
    private CheckBox chooseBuffet;
    @FXML
    private TextArea getRezylt;
    AddWindow addWindow = new AddWindow();
    Calculator calculator=new Calculator();
    EditWindow editWindow=new EditWindow();
    public static ObservableList<Person> data;
    private ArrayList<Person> personList= new ArrayList<>();
    @FXML
    private ComboBox<String> comboBoxRestaurant;
    @FXML
    private ComboBox<String> comboBoxPhotographer;
    @FXML
    private CheckBox askaboutEvenHost;
    @FXML
    private CheckBox askaboutPhotographer;
    @FXML
    private CheckBox askaboutDecorator;
    ObservableList<String> list = FXCollections.observableArrayList("Фотограф", "Ведущий","Визажист","Декоратор","Ресторан","Все");
    ObservableList<String> materialList = FXCollections.observableArrayList("Серебро", "Желтое золото","Красное золото","Белое золото");
    public static ObservableList<String> restaurantNames = FXCollections.observableArrayList();
    public static ObservableList<String> photographerNames = FXCollections.observableArrayList();
    public static ObservableList<String> eventHostNames = FXCollections.observableArrayList();
    public static ObservableList<String> decoratorNames = FXCollections.observableArrayList();
    @FXML
    void chooseRestaurant(ActionEvent event) throws SQLException {
        String restaurantName = comboBoxRestaurant.getSelectionModel().getSelectedItem();
        float priceRestaurant=repository.getRestaurantPrice(restaurantName);
        calculator.setPriceRestaurant(priceRestaurant);
    }
    @FXML
    void chooseBuffet(ActionEvent event) {
        boolean flag=true;
        calculator.checkPriceBuffet(flag);
    }
    @FXML
    void getRezylt(ActionEvent event) {
        try {
            Float result = calculator.checkWedding();
            getRezylt.setText(String.valueOf(result));
        }
        catch (NullPointerException exception){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка рассчета");
            alert.setHeaderText("Пожалуйста, введите количество гостей");
            alert.setContentText("Нажмите enter для ввода");
            ButtonType okButton = new ButtonType("OK");
            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait();
        }
    }
    @FXML
    void SelectComboBoxMaterial(ActionEvent event) {
        String material = comboBoxMaterial.getValue();
        float price = calculator.addPriceMaterial(material);
        System.out.println("Цена материала: " + price);
    }
    @FXML
    void chooseDecorator(ActionEvent event) throws SQLException {
        String decoratorName = comboBoxDecorator.getSelectionModel().getSelectedItem();
        float priceDecorator=repository.getDecoratorPrice(decoratorName);
        calculator.setPriceDecorator(priceDecorator);
    }
    @FXML
    void choosePhotographer(ActionEvent event) throws SQLException {
        String photographerName = comboBoxPhotographer.getSelectionModel().getSelectedItem();
        float pricePhotographer=repository.getPhotographerPrice(photographerName);
        calculator.setPricePhotographer(pricePhotographer);
    }
    @FXML
    void chooseEventHost(ActionEvent event) throws SQLException {
        String eventHostName = comboBoxEventHost.getSelectionModel().getSelectedItem();
        float priceEventHost=repository.getEventHostPrice(eventHostName);
        calculator.setPriceEventHost(priceEventHost);
    }
    @FXML
    void enteringOfPeople(ActionEvent event) {
        try {
            int numberOfPeople = Integer.parseInt(enteringOfPeople.getText());
            calculator.setNumberOfPeople(numberOfPeople);
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText("Пожалуйста, введите целое числовое значение");
            alert.setContentText("Вы ввели неверное значение.");
            ButtonType okButton = new ButtonType("OK");
            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait();
        }
    }
    @FXML
    void askaboutEventHost(ActionEvent event) {
        comboBoxEventHost.setVisible(askaboutEvenHost.isSelected());
    }
    @FXML
    void askaboutPhotographer(ActionEvent event) {
        comboBoxPhotographer.setVisible(askaboutPhotographer.isSelected());
    }
    @FXML
    void askaboutDecorator(ActionEvent event) {
        comboBoxDecorator.setVisible(askaboutDecorator.isSelected());
    }
    @FXML
    void initialize(){
        if (addWindow.isShowing()) {
            Platform.runLater(() -> addWindow.stage.requestFocus());
        } else {
            try {
                addWindow.openAddWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void Select(ActionEvent event) {
        String selectedPerson = combox.getValue();
        if (selectedPerson.equals("Все")){
            try {
                data.clear();
                personList = repository.getAllPerson();
                for (Person person : personList) {
                    if (person instanceof Photographer) {
                        data.add((Photographer) person);
                    } else if (person instanceof EventHost) {
                        data.add((EventHost) person);
                    }
                    else if (person instanceof Visagiste) {
                        data.add((Visagiste) person);
                    }
                    else if (person instanceof Decorator) {
                        data.add((Decorator) person);
                    }
                    else{
                        data.add((Restaurant) person);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (selectedPerson.equals("Фотограф")){
            data.clear();
            try {
                personList = repository.getAllPerson();
                for (Person person : personList) {
                    if (person instanceof Photographer) {
                        data.add((Photographer) person);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (selectedPerson.equals("Ведущий")){
            data.clear();
            try {
                personList = repository.getAllPerson();
                for (Person person : personList) {
                    if (person instanceof EventHost) {
                        data.add((EventHost) person);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (selectedPerson.equals("Визажист")){
            data.clear();
            try {
                personList = repository.getAllPerson();
                for (Person person : personList) {
                    if (person instanceof Visagiste) {
                        data.add((Visagiste) person);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (selectedPerson.equals("Декоратор")){
            data.clear();
            try {
                personList = repository.getAllPerson();
                for (Person person : personList) {
                    if (person instanceof Decorator) {
                        data.add((Decorator) person);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (selectedPerson.equals("Ресторан")){
            data.clear();
            try {
                personList = repository.getAllPerson();
                for (Person person : personList) {
                    if (person instanceof Restaurant) {
                        data.add((Restaurant) person);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

     @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            data=FXCollections.observableArrayList(personList);
            addInfAbout();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        contactsColumn.setCellValueFactory(new PropertyValueFactory<>("contacts"));
        TableSpecialists.setItems(data);
        combox.setItems(list);
         calculator.getNameRestaurant();
        comboBoxRestaurant.setItems(restaurantNames);
        calculator.getNamePhotographer();
        comboBoxPhotographer.setItems(photographerNames);
         calculator.getNameDecorator();
         comboBoxDecorator.setItems(decoratorNames);
         calculator.getNameEventHost();
         comboBoxEventHost.setItems(eventHostNames);
         comboBoxMaterial.setItems(materialList);
    }
    public void addInfAbout() throws SQLException {
        personList = repository.getAllPerson();
        for (Person person : personList) {
            if (person instanceof Photographer) {
                data.add((Photographer) person);
            } else if (person instanceof EventHost) {
                data.add((EventHost) person);
            }
            else if (person instanceof Visagiste) {
                data.add((Visagiste) person);
            }
            else if (person instanceof Decorator) {
                data.add((Decorator) person);
            }
            else{
                data.add((Restaurant) person);
            }
        }
    }
    @FXML
    void remonePerson(ActionEvent event) {
        Person person = TableSpecialists.getSelectionModel().getSelectedItem();
        if (person == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a person to remove.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        try {
            repository.removePerson(person);
            data.remove(person);
            TableSpecialists.getSelectionModel().clearSelection();
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "An error occurred while removing the person: " + e.getMessage(), ButtonType.OK);
            errorAlert.showAndWait();
        }
    }
    @FXML
    void editPerson(ActionEvent event) {
        if (editWindow.isShowing()) {
            Platform.runLater(() -> editWindow.stage.requestFocus());
        } else {
            try {
                editWindow.openEditWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void updateAll(ActionEvent event) {
        combox.setItems(list);
        calculator.getNameRestaurant();
        comboBoxRestaurant.setItems(restaurantNames);
        calculator.getNamePhotographer();
        comboBoxPhotographer.setItems(photographerNames);
        calculator.getNameDecorator();
        comboBoxDecorator.setItems(decoratorNames);
        calculator.getNameEventHost();
        comboBoxEventHost.setItems(eventHostNames);
        comboBoxMaterial.setItems(materialList);
    }

    public static interface Repository {
        ArrayList<Person> getAllPerson() throws SQLException;
        void removePerson(Person person);

        void addPerson(Person person);
        void setPerson(Person person) throws SQLException;

        ArrayList<Restaurant> getAllRestaurant() throws SQLException;

        ArrayList<Photographer> getAllPhotographer() throws SQLException;

        ArrayList<EventHost> getAllEventHost() throws SQLException;

        ArrayList<Decorator> getAllDecorator() throws SQLException;

        float getRestaurantPrice(String restaurantName) throws SQLException;

        float getPhotographerPrice(String photographerName) throws SQLException;

        float getEventHostPrice(String eventHostName) throws SQLException;

        float getDecoratorPrice(String decoratorName) throws SQLException;
    }
}