package org.iut.refactoring.reports;

import org.iut.refactoring.Employee;
import java.util.List;

public class ExperienceReportGenerator implements ReportGenerator {

    @Override
    public void generate(List<Employee> employees, String filter) {
        System.out.println("=== RAPPORT: EXPERIENCE ===");

        for (Employee emp : employees) {
            if (matchesFilter(emp, filter)) {
                String nom = emp.getName();
                int exp = emp.getYearsOfExperience();
                System.out.println(nom + ": " + exp + " années");
            }
        }
    }

    @Override
    public String getReportType() {
        return "EXPERIENCE";
    }

    private boolean matchesFilter(Employee emp, String filter) {
        return filter == null || filter.isEmpty() || emp.getDivision().equals(filter);
    }
}