package com.example.weddingcalculator;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 590);
        stage.getIcons().add(new Image("file:C:\\Users\\ADMIN\\IdeaProjects\\WeddingCalculator\\src\\main\\resources\\com\\example\\weddingcalculator\\images\\heart.png"));
        stage.setTitle("Свадебный калькулятор");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}