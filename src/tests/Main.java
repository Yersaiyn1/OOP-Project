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

        // Save state on any shutdown — normal exit, Ctrl+C (SIGINT), or SIGTERM.
        // Without this, abrupt termination loses all in-memory changes since
        // the last save. JVM guarantees the hook runs unless killed by SIGKILL.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                UniversitySystem.getInstance().shutdown();
            } catch (Exception e) {
                System.err.println("[shutdown-hook] error: " + e.getMessage());
            }
        }, "uni-shutdown-hook"));

        UniversitySystem.getInstance().start();
        seedIfEmpty();
        MainView.run();
    }

    private static void seedIfEmpty() {
        if (!Database.getInstance().getUsers().isEmpty()) return;
        Admin root = new Admin(
                "A001",
                "Root", "Admin",
                "admin@kbtu.kz",
                "admin",
                "+7 700 000 0000",
                500_000.0,
                LocalDate.now(),
                "IT",
                10
        );
        Database.getInstance().getUsers().put(root.getId(), root);
        Logger.getInstance().log("seeded default admin (email=admin@kbtu.kz, password=admin)");
        System.out.println();
        System.out.println("=== Default admin seeded ===");
        System.out.println("email: admin@kbtu.kz");
        System.out.println("password: admin");
        System.out.println("============================");
        System.out.println();
    }
}