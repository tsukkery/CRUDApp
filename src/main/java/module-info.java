module org.example.crud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires junit;


    opens org.example.crud to javafx.fxml;
    exports org.example.crud;
}