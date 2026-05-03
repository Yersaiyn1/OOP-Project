package models.users;

import core.observer.Observer;
import core.observer.NewsEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Abstract base class for every user in the system.
 *
 * Implements Observer so any user can subscribe to NewsService and
 * receive {@link NewsEvent} updates.
 * Implements Serializable so users can be persisted by Database.
 */
public abstract class User implements Observer, Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private LocalDateTime createdAt;

    protected User(String id, String firstName, String lastName,
                   String email, String password, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.createdAt = LocalDateTime.now();
    }

    // ----- core behaviour -----

    /**
     * Verify a password attempt against the stored password.
     * Plain string compare — fine for an academic project; in real life
     * you'd use a salted hash.
     */
    public boolean authenticate(String pwd) {
        return this.password != null && this.password.equals(pwd);
    }

    /**
     * Send a message to another user. In this offline implementation
     * we simply print to the console and let Logger record the action.
     */
    public void sendMessage(User to, String msg) {
        if (to == null || msg == null) return;
        System.out.printf("[message] %s -> %s: %s%n",
                this.getFullName(), to.getFullName(), msg);
    }

    /**
     * Each concrete subclass returns its role name
     * ("Student", "Teacher", "Manager", "Admin").
     */
    public abstract String getRole();

    // ----- Observer -----

    /**
     * Default reaction to a news event: print it. Subclasses may override
     * (e.g. Student could log it differently).
     */
    @Override
    public void update(NewsEvent event) {
        if (event == null || event.getNews() == null) return;
        System.out.printf("[news -> %s] %s%n",
                this.getFullName(), event.getNews().getTitle());
    }

    // ----- getters / setters -----

    public String getId()        { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getEmail()     { return email; }
    public String getPhone()     { return phone; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName)   { this.lastName = lastName; }
    public void setEmail(String email)         { this.email = email; }
    public void setPassword(String password)   { this.password = password; }
    public void setPhone(String phone)         { this.phone = phone; }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    // ----- equals / hashCode / toString -----
    // Identity by id (unique) — required by the diagram and used by Database maps.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s{id='%s', name='%s', email='%s'}",
                getRole(), id, getFullName(), email);
    }
}