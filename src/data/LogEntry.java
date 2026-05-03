package data;

import models.users.User;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * One row in the audit log.
 *
 * Held by Logger and persisted by Database.
 */
public class LogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    private final LocalDateTime timestamp;
    private final User user;
    private final String action;

    public LogEntry(User user, String action) {
        this.timestamp = LocalDateTime.now();
        this.user = user;
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public User getUser() {
        return user;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        String who = (user == null) ? "system" : user.getFullName();
        return String.format("[%s] %s: %s", timestamp, who, action);
    }
}