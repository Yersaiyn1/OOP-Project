package views;

import controllers.UserController;
import data.LogEntry;
import models.users.Admin;
import models.users.User;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * AdminView — menu for administrators.
 *
 * Use cases (per the diagram):
 *   - Add user
 *   - Remove user
 *   - Update user
 *   - View logs
 *   - List all users
 *
 * "Add user" requires a concrete subclass (Admin/Student/Teacher/Manager).
 * To avoid hardcoding concrete constructors here (Y and D own those
 * classes), this view focuses on listing / removal / log viewing —
 * actually creating a user is delegated to factories driven by Main.java.
 */
public class AdminView extends BaseView {

    private AdminView() {}

    public static void show(Admin admin) throws IOException {
        while (true) {
            heading("ADMIN — " + admin.getFullName());
            System.out.println("1) List all users");
            System.out.println("2) Find user by id");
            System.out.println("3) Remove user by id");
            System.out.println("4) View audit logs");
            System.out.println("0) Logout");
            String choice = prompt("> ");

            switch (choice) {
                case "1": listAll(); break;
                case "2": find();    break;
                case "3": remove();  break;
                case "4": logs();    break;
                case "0": return;
                default:  System.out.println("Unknown option.");
            }
        }
    }

    private static void listAll() {
        Collection<User> users = UserController.listAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users in the system.");
            return;
        }
        System.out.println("Users (" + users.size() + "):");
        for (User u : users) {
            System.out.println("  " + u);
        }
    }

    private static void find() throws IOException {
        String id = prompt("User id: ");
        User u = UserController.findById(id);
        System.out.println(u == null ? "Not found." : u.toString());
    }

    private static void remove() throws IOException {
        String id = prompt("User id to remove: ");
        boolean ok = UserController.removeUser(id);
        System.out.println(ok ? "Removed." : "Failed.");
    }

    private static void logs() {
        List<LogEntry> entries = UserController.viewLogs();
        if (entries.isEmpty()) {
            System.out.println("No log entries.");
            return;
        }
        for (LogEntry e : entries) {
            System.out.println(e);
        }
    }
}