package core.factory;

import models.users.Employee;
import models.users.User;
import java.time.LocalDate;
import java.util.Map;

public class EmployeeFactory implements UserFactory {
    private static final long serialVersionUID = 1L;

    @Override
    public User createUser(Map<String, String> data) {
        return new Employee(
                data.get("id"),
                data.get("firstName"),
                data.get("lastName"),
                data.get("email"),
                data.get("password"),
                data.get("phone"),
                0.0,
                LocalDate.now(),
                "Департамент"
        ) {
            private static final long serialVersionUID = 1L;
        };
    }
}