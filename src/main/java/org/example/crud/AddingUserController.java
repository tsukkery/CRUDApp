package org.example.crud;

import MVC.Controller.UserController;
import MVC.Model.UserModel;
import MVC.View.UserView;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddingUserController {

    UserModel model = new UserModel();
    UserView view = new UserView();
    UserController controller = new UserController(model, view);

    @FXML
    private TextField inputField1;

    @FXML
    private void handleConfirm() {
        String newName = inputField1.getText();
        controller.addUser(newName);
        Stage stage = (Stage) inputField1.getScene().getWindow();
        stage.close();
    }
}
