package views;

import java.io.IOException;

public class ManagerView extends BaseView {

    private ManagerView() {
    }

    public static void display() throws IOException {
        println("\n--- Manager View ---");
        int choice;
        do {
            println("1. View Academic Reports");
            println("2. View Teacher Reports");
            println("3. View Research Reports");
            println("4. Manage Users");
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
                    println("Displaying Academic Reports...");
                    break;
                case 2:
                    println("Displaying Teacher Reports...");
                    break;
                case 3:
                    println("Displaying Research Reports...");
                    break;
                case 4:
                    println("Managing Users...");
                    break;
                case 0:
                    println("Logging out from Manager View.");
                    break;
                default:
                    if (choice != -1) {
                        println("Invalid choice. Please try again.");
                    }
            }
        } while (choice != 0);
    }
}
