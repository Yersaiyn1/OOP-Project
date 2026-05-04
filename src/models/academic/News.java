package models.academic;

import models.users.Employee;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * News item published by an Employee (typically a Manager).
 * Distributed via NewsService (Observer pattern).
 */
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String newsId;
    private String title;
    private String content;
    private final LocalDateTime publishedAt;
    private final Employee author;

    public News(String newsId, String title, String content, Employee author) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.publishedAt = LocalDateTime.now();
    }

    public String getNewsId() {
        return newsId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public Employee getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        String authorName = (author == null) ? "system" : author.getFullName();
        return String.format("News{%s, '%s', by %s, at %s}",
                newsId, title, authorName, publishedAt);
    }
}