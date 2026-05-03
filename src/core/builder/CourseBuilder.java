package core.builder;

import models.academic.Course;
import models.enums.Major;
import models.enums.StudyYear;

public class CourseBuilder {
    private String name;
    private int credits;
    private Major targetMajor;
    private StudyYear targetYear;

    public CourseBuilder() {
        // Default values or leave null for required fields to be set by user
    }

    public CourseBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CourseBuilder withCredits(int credits) {
        this.credits = credits;
        return this;
    }

    public CourseBuilder withTargetMajor(Major targetMajor) {
        this.targetMajor = targetMajor;
        return this;
    }

    public CourseBuilder withTargetYear(StudyYear targetYear) {
        this.targetYear = targetYear;
        return this;
    }

    /**
     * Builds and returns a new Course object.
     * @return A new Course instance.
     * @throws IllegalStateException if any required fields are not set.
     */
    public Course build() {
        if (name == null || targetMajor == null || targetYear == null) {
            throw new IllegalStateException("Course name, target major, and target year must be set.");
        }
        // Credits can have a default value if not set, or also be required.
        // For now, assuming 0 is an acceptable default if not specified.
        return new Course(name, credits, targetMajor, targetYear);
    }
}
