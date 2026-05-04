package models.users;

import core.observer.NewsService;
import data.Database;
import models.academic.Course;
import models.academic.News;
import models.academic.Report;
import models.academic.Request;
import models.enums.Major;
import models.enums.ManagerType;
import models.enums.RequestStatus;
import models.enums.StudyYear;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager — handles requests, course catalog, teacher assignments, news.
 */
public class Manager extends Employee {

    private static final long serialVersionUID = 1L;

    private ManagerType type;
    private final List<Request> managedRequests = new ArrayList<>();

    public Manager(String id, String firstName, String lastName,
                   String email, String password, String phone,
                   double salary, LocalDate hireDate, String department,
                   ManagerType type) {
        super(id, firstName, lastName, email, password, phone,
                salary, hireDate, department);
        this.type = type;
    }

    @Override
    public String getRole() {
        return "Manager";
    }

    public void approveRegistration(Request r) {
        if (r == null) return;
        r.setStatus(RequestStatus.APPROVED);
        if (!managedRequests.contains(r)) {
            managedRequests.add(r);
        }
    }

    public void rejectRegistration(Request r) {
        if (r == null) return;
        r.setStatus(RequestStatus.REJECTED);
        if (!managedRequests.contains(r)) {
            managedRequests.add(r);
        }
    }

    /**
     * Open a course up for registration by setting its target major and year
     * and storing it in the central catalog.
     */
    public void addCourseForRegistration(Course c, Major m, StudyYear y) {
        if (c == null) return;
        c.setTargetMajor(m);
        c.setTargetYear(y);
        Database.getInstance().getCourses().put(c.getCourseId(), c);
    }

    public void assignCourse(Teacher t, Course c) {
        if (t == null || c == null) return;
        t.manageCourse(c);
    }

    public Report generateAcademicReport() {
        Report r = new Report("Academic Report — " + getDepartment());
        r.put("manager", getFullName());
        r.put("courses", Database.getInstance().getCourses().size());
        r.put("students", countStudents());
        return r;
    }

    public void manageNews(News n) {
        if (n == null) return;
        Database.getInstance().getNews().put(n.getNewsId(), n);
        NewsService.getInstance().publishNews(n);
    }

    private int countStudents() {
        int n = 0;
        for (User u : Database.getInstance().getUsers().values()) {
            if (u instanceof Student) n++;
        }
        return n;
    }

    public void setType(ManagerType type) {
        this.type = type;
    }

    public ManagerType getType()
    {
        return type;
    }

    public List<Request> getManagedRequests() {
        return managedRequests;
    }


}