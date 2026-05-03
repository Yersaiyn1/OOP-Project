package models.academic;

import models.users.Teacher;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reportId;
    private Teacher teacher;
    private String content;
    private LocalDateTime createdAt;

    public Report(String reportId, Teacher teacher, String content) {
        this.reportId = reportId;
        this.teacher = teacher;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public String getReportId() {
        return reportId;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}