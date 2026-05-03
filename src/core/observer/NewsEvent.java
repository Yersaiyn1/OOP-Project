package core.observer;

import models.academic.News;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * NewsEvent — the payload pushed from NewsService to all Observers.
 */
public class NewsEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private final News news;
    private final LocalDateTime timestamp;

    public NewsEvent(News news) {
        this.news = news;
        this.timestamp = LocalDateTime.now();
    }

    public News getNews()                { return news; }
    public LocalDateTime getTimestamp()  { return timestamp; }

    @Override
    public String toString() {
        return "NewsEvent{" + news + ", at " + timestamp + "}";
    }
}