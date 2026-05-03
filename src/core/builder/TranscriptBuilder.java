package core.builder;

import models.academic.Course;
import models.academic.Mark;
import models.academic.Transcript;
import models.users.Student;

import java.util.HashMap;
import java.util.Map;

public class TranscriptBuilder {
    private Student student;
    private Map<Course, Mark> marks;

    public TranscriptBuilder() {
        this.marks = new HashMap<>();
    }

    public TranscriptBuilder withStudent(Student student) {
        this.student = student;
        return this;
    }

    public TranscriptBuilder withMark(Course course, Mark mark) {
        if (course != null && mark != null) {
            this.marks.put(course, mark);
        }
        return this;
    }

    public Transcript build() {
        if (student == null) {
            throw new IllegalStateException("Student must be set for the transcript.");
        }
        Transcript transcript = new Transcript(student);
        for (Map.Entry<Course, Mark> entry : marks.entrySet()) {
            transcript.putMark(entry.getKey(), entry.getValue());
        }
        return transcript;
    }
}
