package org.iut.refactoring.reports;

import org.iut.refactoring.model.Employee;
import java.util.List;

public interface ReportGenerator {
    void generate(List<Employee> employees, String filter);
    String getReportType();
}