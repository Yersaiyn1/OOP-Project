package core.factory;

import models.enums.TeacherTitle;
import models.research.TeacherResearcher;
import models.users.Teacher;
import models.users.User;

import java.time.LocalDate;
import java.util.Map;

/**
 * TeacherFactory — builds Teacher instances.
 *
 * IMPORTANT: if the title is PROFESSOR, the result is automatically
 * wrapped into a TeacherResearcher (Decorator pattern). The caller still
 * stores the raw Teacher in Database.users — the decorator can be
 * retrieved separately or via the User's instanceof Researcher check.
 *
 * Expected keys:
 *   "id", "firstName", "lastName", "email", "password", "phone",
 *   "salary", "hireDate" (LocalDate), "department", "title" (TeacherTitle)
 */
public class TeacherFactory extends UserFactory {

    private static final long serialVersionUID = 1L;

    @Override
    public User createUser(Map<String, Object> data) {
        Object hireDateObj = data.get("hireDate");
        LocalDate hireDate = (hireDateObj instanceof LocalDate)
                ? (LocalDate) hireDateObj : LocalDate.now();

        TeacherTitle title = (TeacherTitle) data.get("title");
        if (title == null) title = TeacherTitle.LECTURER;

        Teacher t = new Teacher(
                str(data, "id"),
                str(data, "firstName"),
                str(data, "lastName"),
                str(data, "email"),
                str(data, "password"),
                str(data, "phone"),
                dbl(data, "salary", 0),
                hireDate,
                str(data, "department"),
                title
        );

        // Auto-wrap PROFESSOR as a Researcher.
        // The wrapper is created and discarded here; in real flows you
        // would store both the Teacher and the wrapping Researcher.
        if (title == TeacherTitle.PROFESSOR) {
            new TeacherResearcher(t);
        }
        return t;
    }

    /**
     * Convenience: build the Teacher AND its Researcher decorator
     * (for PROFESSOR) so the caller can keep both references.
     * Returns null if the teacher is not a professor.
     */
    public TeacherResearcher createProfessorAsResearcher(Map<String, Object> data) {
        User u = createUser(data);
        if (u instanceof Teacher && ((Teacher) u).isProfessor()) {
            return new TeacherResearcher(u);
        }
        return null;
    }
}