package models.academic;

import models.users.Student;
import models.users.Teacher;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * RecommendationLetter written by a Teacher about a Student for a specific purpose
 * (job application, scholarship, exchange program, ...).
 */
public class RecommendationLetter implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String letterId;
    private final Teacher author;
    private final Student recipient;
    private String content;
    private final LocalDateTime issuedAt;
    private String purpose;

    public RecommendationLetter(Teacher author, Student recipient,
                                String content, String purpose) {
        this.letterId = "REC-" + UUID.randomUUID().toString().substring(0, 8);
        this.author = author;
        this.recipient = recipient;
        this.content = content;
        this.purpose = purpose;
        this.issuedAt = LocalDateTime.now();
    }

    public String export() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Recommendation Letter ===").append(System.lineSeparator());
        sb.append("Letter ID: ").append(letterId).append(System.lineSeparator());
        sb.append("Date:      ").append(issuedAt).append(System.lineSeparator());
        sb.append("Purpose:   ").append(purpose).append(System.lineSeparator());
        sb.append("Author:    ").append(author == null ? "?" : author.getFullName())
                .append(System.lineSeparator());
        sb.append("Recipient: ").append(recipient == null ? "?" : recipient.getFullName())
                .append(System.lineSeparator());
        sb.append("--------------------------------------------------").append(System.lineSeparator());
        sb.append(content).append(System.lineSeparator());
        return sb.toString();
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

    public String getContent() {
        return content;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
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

    @Override
    public String toString() {
        return String.format("RecommendationLetter{%s, by=%s, for=%s, purpose='%s'}",
                letterId,
                author == null ? "?" : author.getFullName(),
                recipient == null ? "?" : recipient.getFullName(),
                purpose);
    }
}