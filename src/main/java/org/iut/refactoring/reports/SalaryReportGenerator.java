package org.iut.refactoring.reports;

import org.iut.refactoring.Employee;
import java.util.List;

public class SalaryReportGenerator implements ReportGenerator {

    @Override
    public void generate(List<Employee> employees, String filter) {
        System.out.println("=== RAPPORT: SALAIRE ===");

        for (Employee emp : employees) {
            if (matchesFilter(emp, filter)) {
                String nom = emp.getName();
                double salaire = emp.calculateSalary();
                System.out.println(nom + ": " + salaire + " €");
            }
        }
    }

    @Override
    public String getReportType() {
        return "SALAIRE";
    }

    private boolean matchesFilter(Employee emp, String filter) {
        return filter == null || filter.isEmpty() || emp.getDivision().equals(filter);
    }
}