package MVC.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    List<User> users = new ArrayList<>();

    public Boolean userExist() {
        return getUsers() != null && !getUsers().isEmpty();
    }

    public String getName(String id) {
        for (User user : getUsers()) {
            if (user.getId() == id)
                return user.getName();
        }
        return "";
    }

    public void addUser(User user) {
        int ind = 0;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crudapp", "root", "12345")){
            String sql = "INSERT INTO users (id, indexx, name) VALUES (REPLACE(UUID(), '-', ''), ?, ?);";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            if (userExist())
                ind = getUsers().size() + 1;
            else
                ind = 1;
            preparedStatement.setInt(1, ind);
            preparedStatement.setString(2, user.getName());
            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    public List<User> getUsers() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crudapp", "root", "12345")) {
            String sql = "Select indexx, id, name, createdAt from users;";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                int index = rs.getInt(1);
                String id = rs.getString(2);
                String name = rs.getString(3);
                Date createdAt = rs.getDate(4);

                User user = new User(index, id, name, createdAt);
                users.add(user);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
        return users;
    }


    public void updateUser(String id, User user) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crudapp", "root", "12345")){
                String sql = "UPDATE users set name = ? where id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, id.toLowerCase());

                int rows = preparedStatement.executeUpdate();
                System.out.printf("%d rows added \n", rows);

            } catch (Exception ex) {
                System.out.println("Connection failed...");
                System.out.println(ex);
            }
    }

    public void deleteUser(String id) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crudapp", "root", "12345")) {
            String sql = "DELETE FROM users where id = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id);

            int rows = preparedStatement.executeUpdate();
            System.out.printf("%d rows deleted\n", rows);

        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);

        }

    }
}
