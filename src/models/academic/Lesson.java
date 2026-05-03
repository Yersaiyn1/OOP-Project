package models.academic;

import models.enums.LessonType;
import java.io.Serializable;

public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;

    private String lessonId;
    private String name;
    private LessonType type;
    private int credits;

    public Lesson(String lessonId, String name, LessonType type, int credits) {
        this.lessonId = lessonId;
        this.name = name;
        this.type = type;
        this.credits = credits;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}