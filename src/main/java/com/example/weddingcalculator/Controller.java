package com.example.weddingcalculator;

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
    DBWorker dbWorker=new DBWorker();
    //WeddingAgency weddingAgency;
    AddWindow addWindow = new AddWindow();
    EditWindow editWindow=new EditWindow();
    ObservableList<Person> data= FXCollections.observableArrayList();
    ArrayList<Person> list;
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

     @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addInfAbout();
            //data=FXCollections.observableArrayList(list);
            //TableSpecialists.setItems(data);
            //weddingAgency.getAllPerson1();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        contactsColumn.setCellValueFactory(new PropertyValueFactory<>("contacts"));
        //data=FXCollections.observableArrayList(list);
        TableSpecialists.setItems(data);

    }
    private void addInfAbout() throws SQLException {
        ArrayList<Person> personList = dbWorker.getAllPerson();
        for (Person person : personList) {
            if (person instanceof Photographer) {
                data.add((Photographer) person);
               // data= FXCollections.observableArrayList(person);
            } else if (person instanceof EventHost) {
                data.add((EventHost) person);
                //data= FXCollections.observableArrayList(person);
            }
            else if (person instanceof Visagiste) {
                data.add((Visagiste) person);
                //data= FXCollections.observableArrayList(person);
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
            dbWorker.removePerson(person);
            data.remove(person);
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