package org.example.crud;

import MVC.Controller.UserController;
import MVC.Model.DataHolder;
import MVC.Model.User;
import MVC.Model.UserModel;
import MVC.View.UserView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Date;

public class AppController{

    UserModel model = new UserModel();
    UserView view = new UserView();
    UserController controller = new UserController(model, view);

    @FXML
    private Button add;

    @FXML
    private Button upd;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, Integer> indexColumn;

    @FXML
    private TableColumn<User, String> idColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, Date> createdColumn;

    @FXML
    private TableColumn<User, Date> updatedColumn;

    private ObservableList<User> users = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        ContextMenu contextMenu = new ContextMenu();

        tableView.setContextMenu(contextMenu);
        MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(t -> {
            User selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    DataHolder.getInstance().setId(selectedItem.getId());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("deletingUser.fxml"));
                    Parent root = loader.load();
                    EditingUserController secondController = loader.getController();
                    secondController.setMainController(this);

                    Stage newWindow = new Stage();
                    newWindow.setScene(new Scene(root, 300, 200));
                    newWindow.setTitle("Updating user");
                    newWindow.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        contextMenu.getItems().add(edit);

        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(t -> {
            User selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                System.out.println(selectedItem.getId());
                controller.deleteUser(selectedItem.getId());
            }
        });
        contextMenu.getItems().add(delete);

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if(t.getButton() == MouseButton.SECONDARY && tableView.getSelectionModel().getSelectedItem() != null) {
                contextMenu.show(tableView, t.getScreenX(), t.getScreenY());
            } else contextMenu.hide();
        });

        tableView.setContextMenu(contextMenu);


        indexColumn.setCellFactory(cellData -> new TableCell<>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index+1));
                }
            }
        });
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        createdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCreatedAt()));
        updatedColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUpdatedAt()));

        loadDataFromDatabase();

        tableView.setItems(users);
        tableView.getSortOrder().add(indexColumn);
        tableView.sort();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });
    }

    public void performAction() {
        controller.updateUser(DataHolder.getInstance().getId(), DataHolder.getInstance().getTextData());
    }

    public void performAction(String userId) {
        String newName = DataHolder.getInstance().getTextData();
        System.out.println(userId);
        System.out.println(newName);
        controller.updateUser(userId, newName);
    }

    @FXML
    private void clickAdd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addingUser.fxml"));
            Parent root = loader.load();

            Stage newWindow = new Stage();
            newWindow.setScene(new Scene(root, 300, 200));
            newWindow.setTitle("Adding user");
            newWindow.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterTable(String searchText) {
        if (searchText.isEmpty()) {
            tableView.setItems(users);
        } else {
            ObservableList<User> filteredList = FXCollections.observableArrayList();
            for (User user : users) {
                if (user.getName().toLowerCase().contains(searchText.toLowerCase()) || user.getId().contains(searchText.toLowerCase())) {
                    filteredList.add(user);
                }
            }
            tableView.setItems(filteredList);
        }
    }

    @FXML
    private void clickUpd(ActionEvent event) {
        users.clear();
        loadDataFromDatabase();
    }

    public void createTable() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/crudapp", "root", "12345");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("DROP TABLE IF EXISTS users;\n" +
                     "CREATE TABLE users\n" +
                     "(\n" +
                     "    id         varchar(32) PRIMARY KEY,\n" +
                     "\tindexx\t\tint,\n" +
                     "    name    \tvarchar(45),\n" +
                     "    createdAt \tTIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                     "    updatedAt   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP\n" +
                     ");")) {

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection failed");
        }
    }

    private void loadDataFromDatabase() {
//        String url = "jdbc:mysql://localhost:3306/crudapp";
//        String user = "root";
//        String password = "12345";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/crudapp", "root", "12345");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users;")) {

            while (resultSet.next()) {
                int index = resultSet.getInt("indexx");
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                java.util.Date createdAt = resultSet.getDate("createdAt");
                java.util.Date updatedAt = resultSet.getDate("updatedAt");
                users.add(new User(index, id, name, createdAt, updatedAt));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection failed");
        }
    }

    public String giveName(String text){
        return text;
    }


}
