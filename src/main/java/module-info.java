module com.example.weddingcalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.weddingcalculator to javafx.fxml;
    exports com.example.weddingcalculator;
    exports com.example.weddingcalculator.specialists;
    opens com.example.weddingcalculator.specialists to javafx.fxml;
    exports com.example.weddingcalculator.dataBase;
    opens com.example.weddingcalculator.dataBase to javafx.fxml;
    exports com.example.weddingcalculator.view;
    opens com.example.weddingcalculator.view to javafx.fxml;
}