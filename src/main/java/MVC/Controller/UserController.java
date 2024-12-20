package MVC.Controller;

import MVC.Model.User;
import MVC.Model.UserModel;
import MVC.View.UserView;

public class UserController {

    private UserModel model;
    private UserView view;

    public UserController(UserModel model, UserView view) {
        this.model = model;
        this.view = view;
    }

    public void displayUsers(){
        view.displayUsers(model.getUsers());
    }

    public void updateUser(String id, String name) {
        User user = new User(name);
        model.updateUser(id, user);
        view.showMessage(name + " user is updated");
    }

    public void addUser(String name) {
        User user = new User(name);
        model.addUser(user);
        view.showMessage("Added user " + name);
    }

    public void deleteUser(String id) {
        String name = model.getName(id);
        model.deleteUser(id);
        view.showMessage("Deleted user " + name);
    }



}
