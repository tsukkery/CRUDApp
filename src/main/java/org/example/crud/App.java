package org.example.crud;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class App extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("CRUDApp");
        stage.setWidth(620);
        stage.setHeight(400);

        stage.show();
    }
}
