package models.academic;

import java.io.Serializable;
import java.time.LocalDateTime;

public class News implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String body;
    private LocalDateTime date;

    public News(String title, String body) {
        this.title = title;
        this.body = body;
        this.date = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getDate() {
        return date;
    }
}