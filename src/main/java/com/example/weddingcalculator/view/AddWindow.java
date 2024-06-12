package com.example.weddingcalculator.view;

import com.example.weddingcalculator.specialists.*;
import com.example.weddingcalculator.dataBase.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static com.example.weddingcalculator.view.Controller.*;

public class AddWindow implements Initializable {
    private Repository repository;
    public AddWindow() {
       this.repository = new DBWorker();
    }
    public static Stage stage;
    @FXML
    private Button addButton;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField contactsText;

    @FXML
    private TextField informationText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField priceText;

    DBWorker dbWorker=new DBWorker();
    String selectedPerson;

    ObservableList<String> list = FXCollections.observableArrayList("Фотограф", "Ведущий","Визажист","Декоратор","Ресторан");
    public static void openAddWindow() throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AddWindow.class.getResource("/com/example/weddingcalculator/add-view.fxml"));
        fxmlLoader.setControllerFactory(clazz -> new AddWindow());
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Добавление");
        stage.getIcons().add(new Image("file:C:\\Users\\ADMIN\\IdeaProjects\\WeddingCalculator\\src\\main\\resources\\com\\example\\weddingcalculator\\images\\heart.png"));
        stage.setMinWidth(541);
        stage.setMinHeight(425);
        stage.setMaxWidth(541);
        stage.setMaxHeight(425);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();
    }
    public boolean isShowing() {
        return stage != null && stage.isShowing();
    }

    @FXML
    void Select(ActionEvent event) {
        selectedPerson = comboBox.getValue();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setItems(list);
    }
    @FXML
    void addPerson(ActionEvent event) throws SQLException {
        String rezyltTextName = nameText.getText();
        String rezyltTextInformation = informationText.getText();
        String rezyltTextContacts = contactsText.getText();
        String rezyltTextPrice = priceText.getText();
        int Id = hashCode();
        if (selectedPerson == null || selectedPerson.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Пожалуйста, выберите тип специалиста.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        else if (selectedPerson.equals("Фотограф")) {
            if (repository.getAllPhotographerName(rezyltTextName).contains(rezyltTextName)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Такой фотограф уже существует", ButtonType.OK);
                alert.showAndWait();
                return;
            } else {
                try {
                    Photographer photographer = new Photographer(Id, rezyltTextName, rezyltTextInformation, Integer.parseInt(rezyltTextPrice), rezyltTextContacts);
                    repository.addPerson(photographer);
                    data.add(photographer);

                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Введите корректную цену", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            }
        }
        else if (selectedPerson.equals("Ведущий")) {
            if (repository.getAllEventHostName(rezyltTextName).contains(rezyltTextName)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Такой ведущий уже существует", ButtonType.OK);
                alert.showAndWait();
                return;
            } else {
                try {
                    EventHost eventHost = new EventHost(Id, rezyltTextName, rezyltTextInformation, Integer.parseInt(rezyltTextPrice), rezyltTextContacts);
                    repository.addPerson(eventHost);
                    data.add(eventHost);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Введите корректную цену", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            }
        }
        else if (selectedPerson.equals("Визажист")) {
            try {
                Visagiste visagiste = new Visagiste(Id, rezyltTextName, rezyltTextInformation, Integer.parseInt(rezyltTextPrice), rezyltTextContacts);
                repository.addPerson(visagiste);
                data.add(visagiste);

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Введите корректную цену", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        }
            else if (selectedPerson.equals("Декоратор")) {
            if (repository.getAllDecoratorName(rezyltTextName).contains(rezyltTextName)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Такой декоратор уже существует", ButtonType.OK);
                alert.showAndWait();
                return;
            } else {
                try {
                    Decorator decorator = new Decorator(Id, rezyltTextName, rezyltTextInformation, Integer.parseInt(rezyltTextPrice), rezyltTextContacts);
                    repository.addPerson(decorator);
                    data.add(decorator);

                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Введите корректную цену", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            }
        }
        else {
            if (repository.getAllRestaurantName(rezyltTextName).contains(rezyltTextName)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Такой ресторан уже существует", ButtonType.OK);
                alert.showAndWait();
                return;
            } else {
                try {
                    Restaurant restaurant = new Restaurant(Id, rezyltTextName, rezyltTextInformation, Integer.parseInt(rezyltTextPrice), rezyltTextContacts);
                    repository.addPerson(restaurant);
                    data.add(restaurant);

                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Введите корректную цену", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            }
        }
        nameText.setText("");
        informationText.setText("");
        contactsText.setText("");
        priceText.setText("");
        stage.close();
    }
}
