package core.builder;

import models.academic.Course;
import models.academic.Mark;
import models.academic.Transcript;
import models.users.Student;

/**
 * TranscriptBuilder — fluent builder for Transcript.
 *
 * Usage:
 *   Transcript t = new TranscriptBuilder()
 *       .setStudent(alice)
 *       .addMark(course1, mark1)
 *       .addMark(course2, mark2)
 *       .build();
 */
public class TranscriptBuilder {

    private Student student;
    private Transcript transcript;

    public TranscriptBuilder setStudent(Student s) {
        this.student = s;
        if (this.transcript == null) {
            this.transcript = new Transcript(s);
        } else {
            this.transcript.setStudent(s);
        }
        return this;
    }

    public TranscriptBuilder addMark(Course c, Mark m) {
        if (this.transcript == null) {
            this.transcript = new Transcript(student);
        }
        this.transcript.addMark(c, m);
        return this;
    }

    public Transcript build() {
        if (this.transcript == null) {
            this.transcript = new Transcript(student);
        }
        this.transcript.calculateGPA();
        return this.transcript;
    }
}