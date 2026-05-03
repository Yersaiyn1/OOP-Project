package tests;

import controllers.UniversitySystem;
import core.Logger;
import data.Database;
import models.users.Admin;
import views.MainView;

import java.io.IOException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws IOException {
        UniversitySystem.getInstance().start();
        seedIfEmpty();
        MainView.run();
    }

    /**
     * If the database has no users yet (first run), create a default Admin
     * so the operator can log in. Credentials are printed to the console.
     */
    private static void seedIfEmpty() {
        if (!Database.getInstance().getUsers().isEmpty()) return;

        Admin root = new Admin(
                "A001",                 // id
                "Root", "Admin",        // first/last
                "admin@kbtu.kz",        // email
                "admin",                // password
                "+7 700 000 0000",      // phone
                500_000.0,              // salary
                LocalDate.now(),        // hire date
                "IT",                   // department
                10                      // access level
        );
        Database.getInstance().getUsers().put(root.getId(), root);
        Logger.getInstance().log("seeded default admin (email=admin@kbtu.kz, password=admin)");
        System.out.println();
        System.out.println("=== Default admin seeded ===");
        System.out.println("  email:    admin@kbtu.kz");
        System.out.println("  password: admin");
        System.out.println("============================");
        System.out.println();
    }
}