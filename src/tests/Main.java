package tests;

import controllers.UniversitySystem;
import core.Logger;
import data.Database;
import models.enums.Major;
import models.enums.StudyYear;
import models.enums.TeacherTitle;
import models.research.TeacherResearcher;
import models.users.Admin;
import models.users.Student;
import models.users.Teacher;
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

        Admin root = new Admin("A001", "Root", "Admin",
                "admin@kbtu.kz", "admin", "+7 700 000 0000",
                500_000.0, LocalDate.now(), "IT", 10);
        Database.getInstance().getUsers().put(root.getId(), root);

        Student alice = new Student("S001", "Alice", "Student",
                "student@kbtu.kz", "student", "+7 700 000 0001",
                "23B0301", Major.CS, StudyYear.FOURTH);
        Database.getInstance().getUsers().put(alice.getId(), alice);

        Teacher bob = new Teacher("T001", "Bob", "Professor",
                "teacher@kbtu.kz", "teacher", "+7 700 000 0002",
                250_000.0, LocalDate.now(), "CS", TeacherTitle.PROFESSOR);
        Database.getInstance().getUsers().put(bob.getId(), bob);
        Database.getInstance().addResearcher(bob.getId(), new TeacherResearcher(bob));

        Logger.getInstance().log("seeded default users");
        System.out.println();
        System.out.println("=== Default accounts ===");
        System.out.println("Admin — email: admin@kbtu.kz | password: admin");
        System.out.println("Student — email: student@kbtu.kz | password: student");
        System.out.println("Teacher — email: teacher@kbtu.kz | password: teacher");
        System.out.println("========================");
        System.out.println();
    }
}