package controllers;

import core.AuthService;
import core.Logger;
import data.Database;
import data.LogEntry;
import models.users.Admin;
import models.users.User;

import java.util.Collection;
import java.util.List;

/**
 - Controller for user-management operations.

 - Most methods require the current user to be an Admin — checked at the
 - top of every method that mutates state. Non-admins get a console
 - message and the call is rejected.
 */
public final class UserController {

    private UserController() {}

    private static boolean requireAdmin() {
        User current = AuthService.getInstance().getCurrentUser();
        if (!(current instanceof Admin)) {
            System.out.println("[users] admin privileges required");
            return false;
        }
        return true;
    }

    /**
     - Add a new user to the system. Admins only.
     */
    public static boolean addUser(User u) {
        if (!requireAdmin())
            return false;
        if (u == null)
            return false;

        Admin admin = (Admin) AuthService.getInstance().getCurrentUser();
        admin.addUser(u);
        Logger.getInstance().log(admin, "added user " + u.getId() + " (" + u.getRole() + ")");
        return true;
    }

    /**
     - Remove a user by id. Admins only.
     */
    public static boolean removeUser(String userId) {
        if (!requireAdmin())
            return false;
        if (userId == null)
            return false;

        User existing = Database.getInstance().getUsers().get(userId);
        if (existing == null) {
            System.out.println("[users] no user with id " + userId);
            return false;
        }

        Admin admin = (Admin) AuthService.getInstance().getCurrentUser();
        admin.removeUser(existing);
        Logger.getInstance().log(admin, "removed user " + userId);
        return true;
    }

    /**
     - Update an existing user. Admins only.
     */
    public static boolean updateUser(User u) {
        if (!requireAdmin())
            return false;
        if (u == null)
            return false;

        Admin admin = (Admin) AuthService.getInstance().getCurrentUser();
        admin.updateUser(u);
        Logger.getInstance().log(admin, "updated user " + u.getId());
        return true;
    }

    /**
     - View audit logs. Admins only.
     */
    public static List<LogEntry> viewLogs() {
        if (!requireAdmin()) return List.of();
        Admin admin = (Admin) AuthService.getInstance().getCurrentUser();
        return admin.viewLogs();
    }

    /**
     - Read-only listing of all users. Available to any logged-in user.
     */
    public static Collection<User> listAllUsers() {
        if (!AuthService.getInstance().isLoggedIn()) {
            System.out.println("[users] login required");
            return List.of();
        }
        return Database.getInstance().getUsers().values();
    }

    /**
     - Find a user by id (any logged-in user can look up).
     */
    public static User findById(String id) {
        if (!AuthService.getInstance().isLoggedIn()) return null;
        return Database.getInstance().getUsers().get(id);
    }
}