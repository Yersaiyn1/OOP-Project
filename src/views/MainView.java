package views;

import controllers.AuthController;
import controllers.UniversitySystem;
import models.users.Admin;
import models.users.User;

import java.io.IOException;

/**
 * MainView — top-level menu loop.
 *
 * Loops: login -> dispatch by role -> logout -> repeat.
 * Quit option saves the database and exits.
 *
 * Note: StudentView / TeacherView / ManagerView are written by Y and D.
 * MainView only references them by class name; if a teammate hasn't
 * landed their view yet, the corresponding branch will fall through to
 * a placeholder.
 */
public class MainView extends BaseView {

    private MainView() {}

    public static void run() throws IOException {
        UniversitySystem.getInstance().start();

        while (true) {
            heading("UNIVERSITY SYSTEM");
            System.out.println("1) Login");
            System.out.println("0) Quit");
            String choice = prompt("> ");

            switch (choice) {
                case "1":
                    User user = LoginView.show();
                    if (user != null) {
                        dispatch(user);
                    }
                    break;
                case "0":
                    UniversitySystem.getInstance().shutdown();
                    System.out.println("Goodbye.");
                    return;
                default:
                    System.out.println("Unknown option.");
            }
        }
    }

    /**
     * Hand control to the appropriate view for the user's role.
     * After the role-specific view returns, the user is logged out
     * and we go back to the login screen.
     */
    private static void dispatch(User user) throws IOException {
        try {
            switch (user.getRole()) {
                case "Admin":
                    AdminView.show((Admin) user);
                    break;
                case "Student":
                    // StudentView.show((Student) user);   // implemented by Y
                    System.out.println("[StudentView not yet implemented in this build]");
                    break;
                case "Teacher":
                    // TeacherView.show((Teacher) user);   // implemented by D
                    System.out.println("[TeacherView not yet implemented in this build]");
                    break;
                case "Manager":
                    // ManagerView.show((Manager) user);   // implemented by Y
                    System.out.println("[ManagerView not yet implemented in this build]");
                    break;
                default:
                    System.out.println("No view registered for role: " + user.getRole());
            }
        } finally {
            AuthController.logout();
        }
    }
}