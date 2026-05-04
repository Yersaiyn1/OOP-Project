package models.academic;

import models.enums.RequestStatus;
import models.users.Employee;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Request — a generic request handled by a Manager.
 * Examples: course registration approval, leave request, complaint.
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String requestId;
    private final Employee sender;
    private final String type;
    private RequestStatus status;
    private final LocalDateTime createdAt;
    private String description;

    public Request(String requestId, Employee sender, String type, String description) {
        this.requestId = requestId;
        this.sender = sender;
        this.type = type;
        this.description = description;
        this.status = RequestStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public String getRequestId() {
        return requestId;
    }

    public Employee getSender() {
        return sender;
    }

    public String getType() {
        return type;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        String senderName = (sender == null) ? "anonymous" : sender.getFullName();
        return String.format("Request{%s, type='%s', from=%s, status=%s}",
                requestId, type, senderName, status);
    }
}