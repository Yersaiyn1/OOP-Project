package views;

import java.io.IOException;

public class StudentView extends BaseView {

    private StudentView() {
    }

    public static void display() throws IOException {
        println("\n--- Student View ---");
        int choice;
        do {
            println("1. View Grades");
            println("2. View Schedule");
            println("3. Register for Courses");
            println("0. Logout");
            print("Enter your choice: ");
            try {
                choice = readInt();
            } catch (NumberFormatException e) {
                println("Invalid input. Please enter a number.");
                choice = -1;
            }

            switch (choice) {
                case 1:
                    println("Displaying Grades...");
                    break;
                case 2:
                    println("Displaying Schedule...");
                    break;
                case 3:
                    println("Registering for Courses...");
                    break;
                case 0:
                    println("Logging out from Student View.");
                    break;
                default:
                    if (choice != -1) {
                        println("Invalid choice. Please try again.");
                    }
            }
        } while (choice != 0);
    }
}
