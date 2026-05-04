package models.academic;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Report — produced by ReportStrategy implementations.
 * Holds free-form key/value data plus a title.
 */
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String reportId;
    private String title;
    private final Map<String, Object> data;
    private final LocalDateTime generatedAt;

    public Report(String title) {
        this.reportId = "RPT-" + UUID.randomUUID().toString().substring(0, 8);
        this.title = title;
        this.data = new HashMap<>();
        this.generatedAt = LocalDateTime.now();
    }

    public String export() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(title).append(" ===").append(System.lineSeparator());
        sb.append("Report ID: ").append(reportId).append(System.lineSeparator());
        sb.append("Generated: ").append(generatedAt).append(System.lineSeparator());
        sb.append("------------------------------------------------------------").append(System.lineSeparator());
        for (Map.Entry<String, Object> e : data.entrySet()) {
            sb.append("  ").append(e.getKey()).append(": ").append(e.getValue())
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }

    public String getReportId() {
        return reportId;
    }

    public String getTitle() {
        return title;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void put(String key, Object val) {
        this.data.put(key, val);
    }

    @Override
    public String toString() {
        return "Report{" + reportId + ", '" + title + "'}";
    }
}