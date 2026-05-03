package views;

import controllers.MarkController;
import data.Database;
import models.academic.Course;
import models.users.Student;
import models.users.Teacher;
import models.users.User;

import java.io.IOException;
import java.util.List;

/**
 * TeacherView — menu for teachers.
 */
public class TeacherView extends BaseView {

    private TeacherView() {}

    public static void show(Teacher teacher) throws IOException {
        while (true) {
            heading("TEACHER — " + teacher.getFullName() + " (" + teacher.getTitle() + ")");
            System.out.println("1) View my courses");
            System.out.println("2) View students of a course");
            System.out.println("3) Put mark");
            System.out.println("4) Take attendance for a course");
            System.out.println("5) Generate mark report");
            System.out.println("6) Write recommendation");
            System.out.println("0) Logout");
            String choice = prompt("> ");

            switch (choice) {
                case "1": listCourses(teacher);            break;
                case "2": viewStudents(teacher);           break;
                case "3": putMark(teacher);                break;
                case "4": takeAttendance(teacher);         break;
                case "5": markReport(teacher);             break;
                case "6": writeRecommendation(teacher);    break;
                case "0": return;
                default:  System.out.println("Unknown option.");
            }
        }
    }

    private static void listCourses(Teacher t) {
        if (t.getCoursesTaught().isEmpty()) {
            System.out.println("You don't teach any courses yet.");
            return;
        }
        for (Course c : t.getCoursesTaught()) {
            System.out.println("  " + c);
        }
    }

    private static void viewStudents(Teacher t) throws IOException {
        Course c = pickCourse(t);
        if (c == null) return;
        List<Student> students = t.viewStudents(c);
        if (students.isEmpty()) {
            System.out.println("No students enrolled.");
            return;
        }
        for (Student s : students) {
            System.out.println("  " + s);
        }
    }

    private static void putMark(Teacher t) throws IOException {
        Course c = pickCourse(t);
        if (c == null) return;
        String studentId = prompt("Student id (in DB): ");
        User u = Database.getInstance().getUsers().get(studentId);
        if (!(u instanceof Student)) {
            System.out.println("Not a student.");
            return;
        }
        double f = parseDouble(prompt("First attestation: "));
        double s = parseDouble(prompt("Second attestation: "));
        double e = parseDouble(prompt("Final exam: "));
        boolean ok = MarkController.putMark((Student) u, c, f, s, e);
        System.out.println(ok ? "Mark recorded." : "Failed.");
    }

    private static void takeAttendance(Teacher t) throws IOException {
        Course c = pickCourse(t);
        if (c == null) return;
        if (c.getLessons().isEmpty()) {
            System.out.println("No lessons yet for this course.");
            return;
        }
        t.takeAttendance(c);
        System.out.println("Attendance marked for the latest lesson.");
    }

    private static void markReport(Teacher t) throws IOException {
        Course c = pickCourse(t);
        if (c == null) return;
        System.out.println(t.generateMarkReport(c).export());
    }

    private static void writeRecommendation(Teacher t) throws IOException {
        String studentId = prompt("Student id: ");
        User u = Database.getInstance().getUsers().get(studentId);
        if (!(u instanceof Student)) {
            System.out.println("Not a student.");
            return;
        }
        String content = prompt("Letter content: ");
        String purpose = prompt("Purpose (e.g. job, exchange): ");
        var letter = t.writeRecommendation((Student) u, content, purpose);
        System.out.println("Letter created:");
        System.out.println(letter.export());
    }

    private static Course pickCourse(Teacher t) throws IOException {
        if (t.getCoursesTaught().isEmpty()) {
            System.out.println("You don't teach any courses yet.");
            return null;
        }
        listCourses(t);
        String id = prompt("Course id: ");
        for (Course c : t.getCoursesTaught()) {
            if (c.getCourseId().equals(id)) return c;
        }
        System.out.println("No such course in your list.");
        return null;
    }

    private static double parseDouble(String s) {
        try { return Double.parseDouble(s); }
        catch (NumberFormatException e) { return 0; }
    }
}