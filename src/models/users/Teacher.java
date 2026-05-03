package models.users;

import core.Logger;
import core.observer.NewsEvent;
import models.academic.Course;
import models.academic.Mark;
import models.academic.Report;
import models.enums.TeacherTitle;
import models.research.Researcher;
import models.research.ResearchPaper;
import models.research.ResearchProject;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Класс Преподавателя (Teacher), наследующийся от Employee.
 * Реализует интерфейсы Researcher (для исследовательской деятельности) и Observer (для подписки на новости).
 */
public class Teacher extends Employee implements Researcher, Serializable {

    private static final long serialVersionUID = 1L;

    private TeacherTitle title;
    private final List<Course> courses;
    private final List<Report> reports;
    private final List<ResearchPaper> papers;
    private final List<ResearchProject> projects;
    private int hIndex;

    public Teacher(String id, String firstName, String lastName,
                   String email, String password, String phone,
                   double salary, LocalDate hireDate, String department) {
        super(id, firstName, lastName, email, password, phone, salary, hireDate, department);
        this.title = TeacherTitle.TUTOR;
        this.courses = new ArrayList<>();
        this.reports = new ArrayList<>();
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.hIndex = 0;
    }

    // Реализация абстрактного метода getRole из User
    @Override
    public String getRole() {
        return "TEACHER";
    }

    public TeacherTitle getTitle() {
        return title;
    }

    public void setTitle(TeacherTitle title) {
        this.title = title;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
            Logger.getInstance().log(this, "added course: " + course.toString());
        }
    }

    @Override
    public int getHIndex() {
        return hIndex;
    }

    public void setHIndex(int hIndex) {
        this.hIndex = hIndex;
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return papers;
    }

    @Override
    public List<ResearchProject> getProjects() {
        return projects;
    }

    @Override
    public void addPaper(ResearchPaper paper) {
        if (paper != null && !papers.contains(paper)) {
            papers.add(paper);
            Logger.getInstance().log(this, "added research paper: " + paper.toString());
        }
    }

    @Override
    public void joinProject(ResearchProject project) {
        if (project != null && !projects.contains(project)) {
            projects.add(project);
            Logger.getInstance().log(this, "joined research project: " + project.toString());
        }
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        if (comparator != null) {
            papers.sort(comparator);
        }
        for (ResearchPaper p : papers) {
            System.out.println(p.toString());
        }
    }

    public List<Report> getReports() {
        return reports;
    }

    public void addReport(Report report) {
        if (report != null) {
            reports.add(report);
            Logger.getInstance().log(this, "submitted report");
        }
    }

    @Override
    public void update(NewsEvent event) {
        System.out.println("[" + getFullName() + "] Получил новость: " + event.getNews());
    }

    public void submitMark(Student student, Course course, Mark mark) {
        if (student != null && course != null && mark != null) {
            Logger.getInstance().log(this, "submitted mark to " + student.getFullName());
        }
    }
}