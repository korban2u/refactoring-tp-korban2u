package org.iut.refactoring.reports;

import org.iut.refactoring.Developer;
import org.iut.refactoring.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ReportServiceTest {

    private ReportService service;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        service = new ReportService();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void shouldGenerateSalaryReport() {
        List<Employee> employees = Arrays.asList(
                new Developer("Alice", 50000, 6, "IT")
        );

        service.generateReport("SALAIRE", employees, null);

        String output = outContent.toString();
        assertThat(output).contains("=== RAPPORT: SALAIRE ===");
    }

    @Test
    void shouldGenerateExperienceReport() {
        List<Employee> employees = Arrays.asList(
                new Developer("Alice", 50000, 6, "IT")
        );

        service.generateReport("EXPERIENCE", employees, null);

        String output = outContent.toString();
        assertThat(output).contains("=== RAPPORT: EXPERIENCE ===");
    }

    @Test
    void shouldGenerateDivisionReport() {
        List<Employee> employees = Arrays.asList(
                new Developer("Alice", 50000, 6, "IT")
        );

        service.generateReport("DIVISION", employees, null);

        String output = outContent.toString();
        assertThat(output).contains("=== RAPPORT: DIVISION ===");
    }

    @Test
    void shouldHandleUnknownReportType() {
        List<Employee> employees = Arrays.asList(
                new Developer("Alice", 50000, 6, "IT")
        );

        service.generateReport("INCONNU", employees, null);

        String output = outContent.toString();
        assertThat(output).contains("=== RAPPORT: INCONNU ===");
    }

    @Test
    void shouldRegisterCustomGenerator() {
        ReportGenerator customGenerator = new ReportGenerator() {
            @Override
            public void generate(List<Employee> employees, String filter) {
                System.out.println("=== CUSTOM REPORT ===");
            }

            @Override
            public String getReportType() {
                return "CUSTOM";
            }
        };

        service.registerGenerator(customGenerator);

        assertThat(service.isReportTypeSupported("CUSTOM")).isTrue();
    }

    @Test
    void shouldCheckIfReportTypeIsSupported() {
        assertThat(service.isReportTypeSupported("SALAIRE")).isTrue();
        assertThat(service.isReportTypeSupported("EXPERIENCE")).isTrue();
        assertThat(service.isReportTypeSupported("DIVISION")).isTrue();
        assertThat(service.isReportTypeSupported("UNKNOWN")).isFalse();
    }
}