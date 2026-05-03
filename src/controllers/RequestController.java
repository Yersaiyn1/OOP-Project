package controllers;

import core.AuthService;
import core.Logger;
import data.Database;
import models.academic.Request;
import models.users.Employee;
import models.users.Manager;
import models.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Controller for managing Request lifecycle.
 */
public final class RequestController {

    private RequestController() {}

    /**
     * Create a new request from the current user (must be an Employee — this
     * covers Teacher and Manager; Students submit other types of requests
     * which can be added similarly).
     */
    public static Request createRequest(String type, String description) {
        User current = AuthService.getInstance().getCurrentUser();
        if (!(current instanceof Employee)) {
            System.out.println("[request] only employees can submit requests through this flow");
            return null;
        }
        String id = "REQ-" + UUID.randomUUID().toString().substring(0, 8);
        Request r = new Request(id, (Employee) current, type, description);
        Database.getInstance().getRequests().put(id, r);
        Logger.getInstance().log(current, "submitted request " + id + " of type " + type);
        return r;
    }

    /**
     * Approve a request. Managers only.
     */
    public static boolean approve(Request r) {
        User current = AuthService.getInstance().getCurrentUser();
        if (!(current instanceof Manager)) {
            System.out.println("[request] manager privileges required");
            return false;
        }
        if (r == null) return false;
        ((Manager) current).approveRegistration(r);
        Logger.getInstance().log(current, "approved request " + r.getRequestId());
        return true;
    }

    /**
     * Reject a request. Managers only.
     */
    public static boolean reject(Request r) {
        User current = AuthService.getInstance().getCurrentUser();
        if (!(current instanceof Manager)) {
            System.out.println("[request] manager privileges required");
            return false;
        }
        if (r == null) return false;
        ((Manager) current).rejectRegistration(r);
        Logger.getInstance().log(current, "rejected request " + r.getRequestId());
        return true;
    }

    /**
     * List all stored requests.
     */
    public static List<Request> listAll() {
        if (!AuthService.getInstance().isLoggedIn()) return new ArrayList<>();
        List<Request> out = new ArrayList<>();
        for (Object v : Database.getInstance().getRequests().values()) {
            if (v instanceof Request) out.add((Request) v);
        }
        return out;
    }
}