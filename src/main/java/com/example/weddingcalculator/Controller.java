package com.example.weddingcalculator;

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
    private final ObservableList<Person> data= FXCollections.observableArrayList();

    @FXML
    void initialize(){

        /* Platform.runLater(() -> {
                AddWindow addWindow = new AddWindow();
                try {
                    addWindow.openAddWindow();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });*/
        if (addWindow.isShowing()) {
            // Если открыто, просто переведите фокус на него
            //addWindow.requestFocus();
            Platform.runLater(() -> addWindow.stage.requestFocus());
        } else {
            // Если не открыто, откройте его
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
            //weddingAgency.getAllPerson1();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        contactsColumn.setCellValueFactory(new PropertyValueFactory<>("contacts"));
        TableSpecialists.setItems(data);

    }
    private void addInfAbout() throws SQLException {
        ArrayList<Person> personList = dbWorker.getAllPerson();
        for (Person person : personList) {
            if (person instanceof Photographer) {
                data.add((Photographer) person);
            } else if (person instanceof EventHost) {
                data.add((EventHost) person);
            }
        }
    }
    @FXML
    void remonePerson(ActionEvent event) {
        Person selectedPerson = TableSpecialists.getSelectionModel().getSelectedItem();

        if (selectedPerson == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a person to remove.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        try {
            dbWorker.removePerson(selectedPerson);
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "An error occurred while removing the person: " + e.getMessage(), ButtonType.OK);
            errorAlert.showAndWait();
        }
        //refreshTable();
        //dbWorker.removePerson((Person) data);
    }
    @FXML
    void editPerson(ActionEvent event) {
        Platform.runLater(() -> {
            EditWindow editWindow = new EditWindow();
            try {
                editWindow.openEditWindow();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}