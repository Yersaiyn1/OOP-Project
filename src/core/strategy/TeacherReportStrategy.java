package core.strategy;

import models.academic.Report;
import models.users.Teacher;

import java.util.List;

public interface TeacherReportStrategy {
    void processReports(Teacher teacher, List<Report> reports);
}