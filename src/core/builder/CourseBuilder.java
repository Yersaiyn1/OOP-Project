package core.builder;

import models.academic.Course;
import models.enums.Major;
import models.enums.StudyYear;
import models.users.Teacher;

/**
 * CourseBuilder — fluent builder for Course.
 *
 * Usage:
 *   Course c = new CourseBuilder()
 *       .setId("CSCI1207")
 *       .setName("Data Science")
 *       .setCredits(6)
 *       .setMajor(Major.IS)
 *       .setYear(StudyYear.SECOND)
 *       .addInstructor(prof)
 *       .build();
 */
public class CourseBuilder {

    private String courseId = "TBD";
    private String name = "Untitled";
    private int credits = 3;
    private Major major = Major.IS;
    private StudyYear year = StudyYear.FIRST;
    private Teacher instructor;

    public CourseBuilder setId(String id) {
        this.courseId = id;
        return this;
    }

    public CourseBuilder setName(String n) {
        this.name = n;
        return this;
    }

    public CourseBuilder setCredits(int c) {
        this.credits = c;
        return this;
    }

    public CourseBuilder setMajor(Major m) {
        this.major = m;
        return this;
    }

    public CourseBuilder setYear(StudyYear y) {
        this.year = y;
        return this;
    }

    public CourseBuilder addInstructor(Teacher t) {
        this.instructor = t;
        return this;
    }

    public Course build() {
        Course c = new Course(courseId, name, credits, major, year);
        if (instructor != null) {
            c.addInstructor(instructor);
        }
        return c;
    }
}