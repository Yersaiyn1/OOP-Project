package views;

import controllers.AuthController;
import models.users.User;

import java.io.IOException;

/**
 * LoginView — collects email + password and asks AuthController to log in.
 *
 * Returns the authenticated User or null on failure (caller decides
 * whether to retry or quit).
 */
public class LoginView extends BaseView {

    private LoginView() {}

    public static User show() throws IOException {
        heading("LOGIN");
        String email    = prompt("Email: ");
        String password = prompt("Password: ");

        User user = AuthController.login(email, password);
        if (user == null) {
            System.out.println("Login failed.");
            return null;
        }
        System.out.println("Welcome, " + user.getFullName() + " (" + user.getRole() + ")");
        return user;
    }
}