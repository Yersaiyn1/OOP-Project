package core.strategy;

public class ResearchReportStrategy implements ReportStrategy {

    @Override
    public String generateReport() {
        return "--- Research Report ---\n" +
               "Generated a placeholder research report.\n" +
               "Further implementation needed to fetch and display actual research data.";
    }
}
