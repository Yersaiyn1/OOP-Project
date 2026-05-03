package models.users;

import models.enums.ManagerType;
import models.academic.Request;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Manager extends Employee implements Serializable {
    private ManagerType type;
    private List<Request> managedRequests;

    public Manager(String firstName, String lastName, String email, String phone, String password, double salary, LocalDate hireDate, String department, ManagerType type) {
        super(UUID.randomUUID().toString(), firstName, lastName, email, phone, password, LocalDateTime.now(), salary, hireDate, department);
        this.type = type;
        this.managedRequests = new ArrayList<>();
    }

    public ManagerType getType() {
        return type;
    }

    public List<Request> getManagedRequests() {
        return managedRequests;
    }

    public void setType(ManagerType type) {
        this.type = type;
    }

    public void addManagedRequest(Request request) {
        if (request != null && !managedRequests.contains(request)) {
            managedRequests.add(request);
        }
    }
}
