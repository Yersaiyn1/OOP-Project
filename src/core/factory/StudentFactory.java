package core.factory;

import models.enums.Major;
import models.enums.StudyYear;
import models.users.Student;
import models.users.User;

import java.util.Map;

/**
 * StudentFactory — builds Student instances.
 *
 * Expected keys in the data map:
 *   "id", "firstName", "lastName", "email", "password", "phone",
 *   "studentId", "major" (Major), "year" (StudyYear)
 */
public class StudentFactory extends UserFactory {

    private static final long serialVersionUID = 1L;

    @Override
    public User createUser(Map<String, Object> data) {
        Student s = new Student(
                str(data, "id"),
                str(data, "firstName"),
                str(data, "lastName"),
                str(data, "email"),
                str(data, "password"),
                str(data, "phone"),
                str(data, "studentId"),
                (Major) data.get("major"),
                (StudyYear) data.get("year")
        );
        return s;
    }
}