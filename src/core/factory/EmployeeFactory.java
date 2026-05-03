package core.factory;

import models.enums.ManagerType;
import models.users.Manager;
import models.users.User;

import java.time.LocalDate;
import java.util.Map;

/**
 * EmployeeFactory — builds Manager instances.
 *
 * In this project, "Employee" creation through this factory means a
 * Manager. Admins and Teachers have their own paths (Admin is created
 * directly in Main during seeding; Teachers go through TeacherFactory).
 *
 * Expected keys:
 *   "id", "firstName", "lastName", "email", "password", "phone",
 *   "salary", "hireDate" (LocalDate), "department",
 *   "managerType" (ManagerType)
 */
public class EmployeeFactory extends UserFactory {

    private static final long serialVersionUID = 1L;

    @Override
    public User createUser(Map<String, Object> data) {
        Object hireDateObj = data.get("hireDate");
        LocalDate hireDate = (hireDateObj instanceof LocalDate)
                ? (LocalDate) hireDateObj : LocalDate.now();

        ManagerType type = (ManagerType) data.get("managerType");
        if (type == null) type = ManagerType.DEPARTMENT;

        return new Manager(
                str(data, "id"),
                str(data, "firstName"),
                str(data, "lastName"),
                str(data, "email"),
                str(data, "password"),
                str(data, "phone"),
                dbl(data, "salary", 0),
                hireDate,
                str(data, "department"),
                type
        );
    }
}