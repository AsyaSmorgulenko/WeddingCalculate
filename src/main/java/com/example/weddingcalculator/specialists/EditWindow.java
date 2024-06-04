package com.example.weddingcalculator.specialists;

import com.example.weddingcalculator.AddWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditWindow implements Initializable {
    private static Stage stage;
    @FXML
    private TextField contactsText;

    @FXML
    private TextField idText;

    @FXML
    private TextField informationText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField priceText;

    @FXML
    private Button saveButton;
    public static void openEditWindow() throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AddWindow.class.getResource("edit-view.fxml"));
        fxmlLoader.setControllerFactory(clazz -> new EditWindow()); // Устанавливаем контроллер
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Добавление");
        stage.getIcons().add(new Image("file:C:\\Users\\ADMIN\\IdeaProjects\\WeddingCalculator\\src\\main\\image\\heart.png"));
        stage.setMinWidth(541);
        stage.setMinHeight(425);
        stage.setMaxWidth(541);
        stage.setMaxHeight(425);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}