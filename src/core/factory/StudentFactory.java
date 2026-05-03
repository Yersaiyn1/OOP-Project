package core.factory;

import models.users.Student;
import models.users.User;
import java.util.Map;

public class StudentFactory implements UserFactory {
    private static final long serialVersionUID = 1L;

    @Override
    public User createUser(Map<String, String> data) {
        Student student = new Student(data.get("id"));

        // Дополнительная инициализация, если требуется
        if (data.containsKey("firstName")) {
            student.setFirstName(data.get("firstName"));
        }
        if (data.containsKey("lastName")) {
            student.setLastName(data.get("lastName"));
        }
        if (data.containsKey("email")) {
            student.setEmail(data.get("email"));
        }
        if (data.containsKey("phone")) {
            student.setPhone(data.get("phone"));
        }
        // Пароль обычно устанавливается через метод или в сеттере
        if (data.containsKey("password")) {
            student.setPassword(data.get("password"));
        }

        return student;
    }
}