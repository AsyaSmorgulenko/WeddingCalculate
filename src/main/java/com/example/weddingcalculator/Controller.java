package com.example.weddingcalculator;

import com.example.weddingcalculator.dataBase.DBWorker;
import com.example.weddingcalculator.dataBase.Repository;
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
    public Button editButton;
    @FXML
    private Button addButton;
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
    AddWindow addWindow = new AddWindow();
    EditWindow editWindow=new EditWindow();
    public static ObservableList<Person> data;
    ArrayList<Person> personList= new ArrayList<>();
    ObservableList<String> list = FXCollections.observableArrayList("Фотограф", "Ведущий","Визажист","Декоратор","Ресторан","Все");
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
            //data=FXCollections.observableArrayList(list);
            //TableSpecialists.setItems(data);
            //weddingAgency.getAllPerson1();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        contactsColumn.setCellValueFactory(new PropertyValueFactory<>("contacts"));
        TableSpecialists.setItems(data);
         combox.setItems(list);

    }
    private void addInfAbout() throws SQLException {
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
            //data= FXCollections.observableArrayList(person);
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
            //personList.remove(person);
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
}