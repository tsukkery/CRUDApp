package MVC;

import MVC.Controller.UserController;
import MVC.Model.UserModel;
import MVC.View.UserView;

import java.sql.*;

public class Main {


    public static void main(String[] args) throws SQLException {
        UserModel model = new UserModel();
        UserView view = new UserView();
        UserController controller = new UserController(model, view);
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/crudapp",
                "root", "12345");
//        System.out.println(model.getUsers().size() + 1);
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM users");
        while (results.next()) {
            String id = results.getString(1);
            String name = results.getString(2);
            String createdAt = results.getString(3);
            String updatedAt = results.getString(4);
            System.out.println(results.getRow() + ". " + id + "\t" + name + "\t" + createdAt + "\t" + updatedAt);
        }
    }
}

