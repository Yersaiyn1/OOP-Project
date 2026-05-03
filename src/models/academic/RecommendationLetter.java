package models.academic;

import models.users.Student;
import models.users.Teacher;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class RecommendationLetter implements Serializable {
    private String letterId;
    private Teacher author;
    private Student recipient;
    private LocalDateTime issuedAt;
    private String content;
    private String purpose;

    public RecommendationLetter(Teacher author, Student recipient, String content, String purpose) {
        this.letterId = UUID.randomUUID().toString();
        this.author = author;
        this.recipient = recipient;
        this.issuedAt = LocalDateTime.now();
        this.content = content;
        this.purpose = purpose;
    }

    public String getLetterId() {
        return letterId;
    }

    public Teacher getAuthor() {
        return author;
    }

    public Student getRecipient() {
        return recipient;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public String getContent() {
        return content;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
