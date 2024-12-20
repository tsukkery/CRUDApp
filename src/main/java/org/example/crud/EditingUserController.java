package org.example.crud;

import MVC.Model.DataHolder;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditingUserController {

    @FXML
    private TextField inputField1;

    @FXML
    private Button updateButton;

    private AppController mainController;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void confirmUpdate() {

        System.out.println(inputField1.getText());
        if (inputField1.getText() != null)
            DataHolder.getInstance().setTextData(inputField1.getText());
        else
            DataHolder.getInstance().setTextData("no name was here");
        Stage stage = (Stage) inputField1.getScene().getWindow();
        stage.close();
        if (mainController != null) {
            mainController.performAction();
        }
    }


}
