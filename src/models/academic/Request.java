package models.academic;

import models.enums.RequestStatus;
import models.users.User;
import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private String requestId;
    private User sender;
    private String message;
    private RequestStatus status;
    private String response;

    public Request(String requestId, User sender, String message) {
        this.requestId = requestId;
        this.sender = sender;
        this.message = message;
        this.status = RequestStatus.PENDING;
        this.response = "";
    }

    public String getRequestId() {
        return requestId;
    }

    public User getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}