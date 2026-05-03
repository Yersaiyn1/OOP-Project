package controllers;

import core.AuthService;
import core.Logger;
import models.exceptions.AuthenticationException;
import models.users.User;

/**
 - Static controller that bridges the Login/Main views with AuthService.

 - Pattern: views call only controllers, controllers call core/data layer.
 */
public final class AuthController {

    private AuthController() {}

    /**
     - Try to log a user in. Returns the User on success, null on failure
     - (the views display the friendly message; we just print + log here).
     */
    public static User login(String email, String password) {
        try {
            return AuthService.getInstance().login(email, password);
        } catch (AuthenticationException e) {
            System.out.println("[auth] " + e.getMessage());
            Logger.getInstance().log("failed login attempt for " + email);
            return null;
        }
    }

    public static void logout() {
        AuthService.getInstance().logout();
    }

    public static User getCurrentUser() {
        return AuthService.getInstance().getCurrentUser();
    }

    public static boolean isLoggedIn() {
        return AuthService.getInstance().isLoggedIn();
    }

    /**
     - Change the current user's password (used by the "Change Password"
     - use case in the use-case diagram).

     - @return true if the old password matched and the new one was applied.
     */
    public static boolean changePassword(String oldPwd, String newPwd) {
        User current = getCurrentUser();
        if (current == null) {
            System.out.println("[auth] no user is logged in");
            return false;
        }
        if (!current.authenticate(oldPwd)) {
            System.out.println("[auth] old password is wrong");
            return false;
        }
        current.setPassword(newPwd);
        Logger.getInstance().log(current, "changed password");
        return true;
    }
}