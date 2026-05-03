package views;

import core.Logger;
import models.users.Teacher;
import strategies.DefaultReportGenerationStrategy;
import strategies.TeacherReportStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

public class TeacherView extends BaseView implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void showMenu(Teacher teacher) throws IOException {
        if (teacher == null) {
            System.out.println("Ошибка: Преподаватель не найден.");
            return;
        }

        TeacherReportStrategy reportStrategy = new DefaultReportGenerationStrategy();
        boolean exit = false;

        System.out.println("\n====================================");
        System.out.println("  Кабинет преподавателя: " + teacher.getFullName());
        System.out.println("====================================");

        while (!exit) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Посмотреть мои курсы");
            System.out.println("2. Просмотреть и обработать отчеты");
            System.out.println("3. Посмотреть мои научные статьи");
            System.out.println("4. Просмотреть мои проекты");
            System.out.println("5. Поставить оценку студенту");
            System.out.println("0. Выход");
            System.out.print("\nВведите номер действия: ");

            String choice = reader.readLine();

            switch (choice) {
                case "1":
                    viewCourses(teacher);
                    break;
                case "2":
                    processReports(teacher, reportStrategy);
                    break;
                case "3":
                    viewPapers(teacher);
                    break;
                case "4":
                    viewProjects(teacher);
                    break;
                case "5":
                    submitMarkMenu(teacher);
                    break;
                case "0":
                    System.out.println("Выход из системы...");
                    Logger.getInstance().log(teacher, "logged out");
                    exit = true;
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }

    private static void viewCourses(Teacher teacher) {
        System.out.println("\n--- Ваши курсы ---");
        if (teacher.getCourses().isEmpty()) {
            System.out.println("У вас пока нет добавленных курсов.");
        } else {
            teacher.getCourses().forEach(c -> System.out.println("- " + c.toString()));
        }
    }

    private static void processReports(Teacher teacher, TeacherReportStrategy strategy) {
        System.out.println("\n--- Обработка отчетов ---");
        strategy.processReports(teacher, teacher.getReports());
    }

    private static void viewPapers(Teacher teacher) {
        System.out.println("\n--- Ваши научные статьи ---");
        if (teacher.getPapers().isEmpty()) {
            System.out.println("У вас пока нет добавленных статей.");
        } else {
            teacher.printPapers(null);
        }
    }

    private static void viewProjects(Teacher teacher) {
        System.out.println("\n--- Ваши проекты ---");
        if (teacher.getProjects().isEmpty()) {
            System.out.println("У вас пока нет активных проектов.");
        } else {
            teacher.getProjects().forEach(p -> System.out.println("- " + p.getProjectName()));
        }
    }

    private static void submitMarkMenu(Teacher teacher) throws IOException {
        System.out.println("\n--- Выставление оценки ---");
        System.out.print("Введите ID студента: ");
        String studentId = reader.readLine();
        System.out.print("Введите ID курса: ");
        String courseId = reader.readLine();
        System.out.print("Введите оценку (например, A, B, C): ");
        String score = reader.readLine();

        Logger.getInstance().log(teacher, "Tried to submit mark " + score + " for student " + studentId + " in course " + courseId);
        System.out.println("Оценка успешно добавлена в очередь на запись!");
    }
}