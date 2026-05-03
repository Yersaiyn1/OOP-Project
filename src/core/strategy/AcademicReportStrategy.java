package core.strategy;

public class AcademicReportStrategy implements ReportStrategy {

    @Override
    public String generateReport() {
        return "--- Academic Report ---\n" +
               "Generated a placeholder academic report.\n" +
               "Further implementation needed to fetch and display actual academic data.";
    }
}
