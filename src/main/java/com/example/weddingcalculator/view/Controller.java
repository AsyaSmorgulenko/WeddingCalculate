package com.example.weddingcalculator.view;

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
import javafx.stage.FileChooser;

import java.io.File;
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
    private Button addButton;
    @FXML
    private Button updateAll;
    @FXML
    private Button dowland;
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
    private ObservableList<String> list = FXCollections.observableArrayList("Фотограф", "Ведущий","Визажист","Декоратор","Ресторан","Все");
    private ObservableList<String> materialList = FXCollections.observableArrayList("Серебро", "Желтое золото","Красное золото","Белое золото");
    public static ObservableList<String> restaurantNames = FXCollections.observableArrayList();
    public static ObservableList<String> photographerNames = FXCollections.observableArrayList();
    public static ObservableList<String> eventHostNames = FXCollections.observableArrayList();
    public static ObservableList<String> decoratorNames = FXCollections.observableArrayList();
    @FXML
    void dowland(ActionEvent event) throws IOException {
        ObservableList<Person> persons =TableSpecialists.getItems();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить таблицу");
        fileChooser.setInitialFileName("wedding.csv");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV-файлы", "*.csv")
        );
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            java.io.FileWriter fileWriter = new java.io.FileWriter(file);
            fileWriter.write(nameColumn.getText() + "," +
                    surnameColumn.getText() + "," +
                    priceColumn.getText() + "," +
                    contactsColumn.getText() + "," +
                    "Тип специалиста" +"\n");
            for (Person person : persons) {
                if (person instanceof Photographer) {
                    fileWriter.write(person.getName() + "," +
                            ((Photographer) person).getSurname() + "," +
                            person.getPrice() + "," +
                            person.getContacts() + "," +
                            "Фотограф"+"\n");
                }
                else if (person instanceof Restaurant) {
                    fileWriter.write(person.getName() + "," +
                            ((Restaurant) person).getSurname() + "," +
                            person.getPrice() + "," +
                            person.getContacts() + "," +
                            "Ресторан"+"\n");
                }
                else if (person instanceof EventHost) {
                    fileWriter.write(person.getName() + "," +
                            ((EventHost) person).getSurname() + "," +
                            person.getPrice() + "," +
                            person.getContacts() + "," +
                            "Ведущий"+"\n");
                }
                else if (person instanceof Decorator) {
                    fileWriter.write(person.getName() + "," +
                            ((Decorator) person).getSurname() + "," +
                            person.getPrice() + "," +
                            person.getContacts() + "," +
                            "Декоратор"+"\n");
                }
                else {
                    fileWriter.write(person.getName() + "," +
                            ((Visagiste) person).getSurname() + "," +
                            person.getPrice() + "," +
                            person.getContacts() + "," +
                            "Визажист"+"\n");
                }
            }

            fileWriter.close();
        }
    }
    @FXML
    void chooseRestaurant(ActionEvent event) throws SQLException {
        String restaurantName = comboBoxRestaurant.getSelectionModel().getSelectedItem();
        float priceRestaurant=repository.getRestaurantPrice(restaurantName);
        calculator.setPriceRestaurant(priceRestaurant);
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
        if (!askaboutEvenHost.isSelected()) {
            comboBoxEventHost.getSelectionModel().clearSelection();
        }
    }
    @FXML
    void askaboutPhotographer(ActionEvent event) {
        comboBoxPhotographer.setVisible(askaboutPhotographer.isSelected());
        if (!askaboutPhotographer.isSelected()) {
            comboBoxPhotographer.getSelectionModel().clearSelection();
        }
    }
    @FXML
    void askaboutDecorator(ActionEvent event) {
        comboBoxDecorator.setVisible(askaboutDecorator.isSelected());
        if (!askaboutDecorator.isSelected()) {
            comboBoxDecorator.getSelectionModel().clearSelection();
        }
    }
    @FXML
    void chooseBuffet(ActionEvent event) {
        boolean flag;
        if (chooseBuffet.isSelected()) {
            flag = true;
        } else {
            flag = false;
        }
        calculator.checkPriceBuffet(flag);
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
            Alert alert = new Alert(Alert.AlertType.WARNING, "Пожалуйста,выделите персона.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        try {
            repository.removePerson(person);
            data.remove(person);
            TableSpecialists.getSelectionModel().clearSelection();
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Ошибка удаления" + e.getMessage(), ButtonType.OK);
            errorAlert.showAndWait();
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

}