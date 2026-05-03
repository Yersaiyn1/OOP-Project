package views;

import controllers.CourseController;
import controllers.EnrollmentController;
import controllers.MarkController;
import controllers.NewsController;
import models.academic.Course;
import models.academic.Mark;
import models.academic.News;
import models.academic.RecommendationLetter;
import models.users.Student;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * StudentView — menu for students.
 */
public class StudentView extends BaseView {

    private StudentView() {}

    public static void show(Student student) throws IOException {
        while (true) {
            heading("STUDENT — " + student.getFullName());
            System.out.println("1) View available courses");
            System.out.println("2) Register for a course");
            System.out.println("3) View my marks");
            System.out.println("4) View transcript");
            System.out.println("5) View news feed");
            System.out.println("6) Subscribe to news");
            System.out.println("7) View my recommendation letters");
            System.out.println("0) Logout");
            String choice = prompt("> ");

            switch (choice) {
                case "1": listCourses();  break;
                case "2": register(); break;
                case "3": viewMarks();   break;
                case "4": viewTranscript(student); break;
                case "5": viewNews(); break;
                case "6": NewsController.subscribe(); break;
                case "7": viewRecommendations(student); break;
                case "0": return;
                default:  System.out.println("Unknown option.");
            }
        }
    }

    private static void listCourses() {
        Collection<Course> courses = CourseController.listAll();
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        for (Course c : courses) {
            System.out.println("  " + c);
        }
    }

    private static void register() throws IOException {
        String id = prompt("Course id: ");
        Course c = CourseController.findById(id);
        if (c == null) {
            System.out.println("No such course.");
            return;
        }
        boolean ok = EnrollmentController.registerForCourse(c);
        System.out.println(ok ? "Registered." : "Registration failed.");
    }

    private static void viewMarks() {
        List<Mark> marks = MarkController.viewMyMarks();
        if (marks.isEmpty()) {
            System.out.println("No marks yet.");
            return;
        }
        for (Mark m : marks) {
            System.out.println("  " + m);
        }
    }

    private static void viewTranscript(Student s) {
        System.out.println(s.getTranscript());
        System.out.println(s.getTranscript().generateReport().export());
    }

    private static void viewNews() {
        List<News> feed = NewsController.getFeed();
        if (feed.isEmpty()) {
            System.out.println("No news yet.");
            return;
        }
        for (News n : feed) {
            System.out.println("  " + n);
        }
    }

    private static void viewRecommendations(Student student) {
        List<RecommendationLetter> letters = student.getRecommendations();
        if (letters.isEmpty()) {
            System.out.println("You have no recommendation letters.");
            return;
        }
        System.out.println("You have " + letters.size() + " letter(s):");
        for (RecommendationLetter letter : letters) {
            System.out.println();
            System.out.println(letter.export());
        }
    }

}