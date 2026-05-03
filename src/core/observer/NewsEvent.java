package core.observer;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NewsEvent implements Serializable {
    private final String news;
    private final LocalDateTime timestamp;

    public NewsEvent(String news) {
        this.news = news;
        this.timestamp = LocalDateTime.now();
    }

    public String getNews() {
        return news;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}