package com.example.weddingcalculator;

import com.example.weddingcalculator.specialists.Photographer;
import com.example.weddingcalculator.specialists.WeddingAgency;
import com.example.weddingcalculator.specialists.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddWindow implements Initializable {
    static Stage stage;
    @FXML
    private Button addButton;

    @FXML
    private ComboBox<String> comboBox;

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
    WeddingAgency weddingAgency;
    DBWorker dbWorker=new DBWorker();
    ObservableList<String> list = FXCollections.observableArrayList("Фотограф", "Ведущий");


    public static void openAddWindow() throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AddWindow.class.getResource("add-view.fxml"));
        fxmlLoader.setControllerFactory(clazz -> new AddWindow()); // Устанавливаем контроллер
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
    public boolean isShowing() {
        return stage != null && stage.isShowing();
    }

    @FXML
    void Select(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //comboBox.setItems(list);
    }
    @FXML
    void addPerson(ActionEvent event) {
        String rezyltTextName = nameText.getText();
        String rezyltTextInformation = informationText.getText();
        String rezyltTextContacts = contactsText.getText();
        String rezyltTextPrice = priceText.getText();
        String rezyltTextId = idText.getText();
        try {
            Photographer photographer = new Photographer(Integer.parseInt(rezyltTextId),rezyltTextName,rezyltTextInformation,Integer.parseInt(rezyltTextPrice),rezyltTextContacts);
            //weddingAgency.add(photographer);
            dbWorker.addPerson(photographer);

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        nameText.setText("");
        informationText.setText("");
        contactsText.setText("");
        priceText.setText("");
        idText.setText("");
    }

    public void requestFocus() {
        System.out.println("Окно открыто");
    }
}
