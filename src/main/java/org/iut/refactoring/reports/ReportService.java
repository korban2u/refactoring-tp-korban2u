package org.iut.refactoring.reports;

import org.iut.refactoring.Employee;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

    private final Map<String, ReportGenerator> reportGenerators = new HashMap<>();

    public ReportService() {
        registerDefaultGenerators();
    }

    private void registerDefaultGenerators() {
        registerGenerator(new SalaryReportGenerator());
        registerGenerator(new ExperienceReportGenerator());
        registerGenerator(new DivisionReportGenerator());
    }

    public void registerGenerator(ReportGenerator generator) {
        reportGenerators.put(generator.getReportType(), generator);
    }

    public void generateReport(String reportType, List<Employee> employees, String filter) {
        ReportGenerator generator = reportGenerators.get(reportType);

        if (generator != null) {
            generator.generate(employees, filter);
        } else {
            // Rapport inconnu - afficher un header générique
            System.out.println("=== RAPPORT: " + reportType + " ===");
        }
    }

    public boolean isReportTypeSupported(String reportType) {
        return reportGenerators.containsKey(reportType);
    }
}