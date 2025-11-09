package org.iut.refactoring.reports;

import org.iut.refactoring.Employee;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DivisionReportGenerator implements ReportGenerator {

    @Override
    public void generate(List<Employee> employees, String filter) {
        System.out.println("=== RAPPORT: DIVISION ===");

        HashMap<String, Integer> compteurDivisions = new HashMap<>();

        for (Employee emp : employees) {
            String div = emp.getDivision();
            compteurDivisions.put(div, compteurDivisions.getOrDefault(div, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : compteurDivisions.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " employés");
        }
    }

    @Override
    public String getReportType() {
        return "DIVISION";
    }
}