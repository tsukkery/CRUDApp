package MVC.View;

import MVC.Model.User;

import java.util.List;

public class UserView {

    public void displayUsers(List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println((i+1) + ". " + user.getName());
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

}
