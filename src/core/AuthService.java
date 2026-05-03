package core;

import data.Database;
import models.exceptions.AuthenticationException;
import models.users.User;

import java.io.Serializable;

/**
 - Singleton AuthService.

 - Holds the currently logged-in User. Every controller that mutates
 - data must check {@link #getCurrentUser()} != null first; if it is
 - null, the call should be rejected.

 - Throws {@link AuthenticationException} on bad credentials.
 */
public class AuthService implements Serializable {

    private static final long serialVersionUID = 1L;

    private User currentUser;

    private AuthService() {}

    private static class Holder {
        private static final AuthService INSTANCE = new AuthService();
    }

    public static AuthService getInstance() {
        return Holder.INSTANCE;
    }

    /**
     - Authenticate a user by email + password.

     - Searches Database.users for a matching email, verifies password.

     - @return the logged-in User on success
     - @throws AuthenticationException if no user matches or password is wrong
     */
    public User login(String email, String pwd) {
        if (email == null || pwd == null) {
            throw new AuthenticationException("Email and password are required.");
        }

        User found = null;
        for (User u : Database.getInstance().getUsers().values()) {
            if (email.equalsIgnoreCase(u.getEmail())) {
                found = u;
                break;
            }
        }

        if (found == null) {
            throw new AuthenticationException("No user with email: " + email);
        }
        if (!found.authenticate(pwd)) {
            throw new AuthenticationException("Wrong password for: " + email);
        }

        this.currentUser = found;
        Logger.getInstance().log(found, "logged in");
        return found;
    }

    public void logout() {
        if (currentUser != null) {
            Logger.getInstance().log(currentUser, "logged out");
        }
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}