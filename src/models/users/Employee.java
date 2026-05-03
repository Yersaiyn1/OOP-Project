package models.users;

import java.time.LocalDate;

/**
 - Abstract base class for every employee of the university.
 - Adds salary, hire date and department on top of User.

 - Concrete subclasses: Admin, Teacher, Manager.
 */
public abstract class Employee extends User {

    private static final long serialVersionUID = 1L;

    private double salary;
    private LocalDate hireDate;
    private String department;

    protected Employee(String id, String firstName, String lastName,
                       String email, String password, String phone,
                       double salary, LocalDate hireDate, String department) {
        super(id, firstName, lastName, email, password, phone);
        this.salary = salary;
        this.hireDate = hireDate;
        this.department = department;
    }

    /**
     - send a complaint to an Admin.
     - logger should record the action at the call site.
     */
    public void sendComplaint(Admin to, String text) {
        if (to == null || text == null) return;
        System.out.printf("[complaint] %s -> %s: %s%n",
                getFullName(), to.getFullName(), text);
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    public void setHireDate(LocalDate hireDate)  {
        this.hireDate = hireDate;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }
    public LocalDate getHireDate() {
        return hireDate;
    }
    public String getDepartment() {
        return department;
    }


}