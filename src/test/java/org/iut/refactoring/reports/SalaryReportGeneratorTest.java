package org.iut.refactoring.reports;

import org.iut.refactoring.model.Developer;
import org.iut.refactoring.model.Employee;
import org.iut.refactoring.model.ProjectManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SalaryReportGeneratorTest {

    private SalaryReportGenerator generator;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        generator = new SalaryReportGenerator();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void shouldGenerateSalaryReport() {
        List<Employee> employees = Arrays.asList(
                new Developer("Alice", 50000, 6, "IT"),
                new ProjectManager("Bob", 60000, 4, "RH")
        );

        generator.generate(employees, null);

        String output = outContent.toString();
        assertThat(output).contains("=== RAPPORT: SALAIRE ===");
        assertThat(output).contains("Alice:");
        assertThat(output).contains("Bob:");
    }

    @Test
    void shouldFilterByDivision() {
        List<Employee> employees = Arrays.asList(
                new Developer("Alice", 50000, 6, "IT"),
                new ProjectManager("Bob", 60000, 4, "RH")
        );

        generator.generate(employees, "IT");

        String output = outContent.toString();
        assertThat(output).contains("Alice:");
        assertThat(output).doesNotContain("Bob:");
    }

    @Test
    void shouldReturnCorrectReportType() {
        assertThat(generator.getReportType()).isEqualTo("SALAIRE");
    }
}