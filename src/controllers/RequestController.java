package controllers;

import core.Logger;
import data.Database;
import models.academic.Request;
import models.enums.RequestStatus;
import models.users.Student;
import models.users.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RequestController implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final List<Request> requests = new ArrayList<>();

    public static void createRequest(Student student, String content) {
        if (student != null && content != null && !content.isEmpty()) {
            Request request = new Request(
                    "REQ-" + System.currentTimeMillis(),
                    student,
                    content
            );
            requests.add(request);
            Logger.getInstance().log(student, "created request: " + content);
        }
    }

    public static List<Request> getAllRequests() {
        return new ArrayList<>(requests);
    }

    public static List<Request> getRequestsByStudent(Student student) {
        return requests.stream()
                .filter(r -> r.getStudent().equals(student))
                .collect(Collectors.toList());
    }

    public static List<Request> getPendingRequests() {
        return requests.stream()
                .filter(r -> r.getStatus() == RequestStatus.PENDING)
                .collect(Collectors.toList());
    }

    public static void approveRequest(String requestId, Teacher approver) {
        Request request = requests.stream()
                .filter(r -> r.getRequestId().equals(requestId))
                .findFirst()
                .orElse(null);

        if (request != null) {
            request.setStatus(RequestStatus.APPROVED);
            Logger.getInstance().log(approver, "approved request: " + requestId);
        }
    }

    public static void rejectRequest(String requestId, Teacher rejector) {
        Request request = requests.stream()
                .filter(r -> r.getRequestId().equals(requestId))
                .findFirst()
                .orElse(null);

        if (request != null) {
            request.setStatus(RequestStatus.REJECTED);
            Logger.getInstance().log(rejector, "rejected request: " + requestId);
        }
    }
}