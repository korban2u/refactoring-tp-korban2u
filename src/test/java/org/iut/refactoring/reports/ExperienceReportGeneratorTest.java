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

class ExperienceReportGeneratorTest {

    private ExperienceReportGenerator generator;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        generator = new ExperienceReportGenerator();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void shouldGenerateExperienceReport() {
        List<Employee> employees = Arrays.asList(
                new Developer("Alice", 50000, 6, "IT"),
                new ProjectManager("Bob", 60000, 4, "RH")
        );

        generator.generate(employees, null);

        String output = outContent.toString();
        assertThat(output).contains("=== RAPPORT: EXPERIENCE ===");
        assertThat(output).contains("Alice: 6 années");
        assertThat(output).contains("Bob: 4 années");
    }

    @Test
    void shouldReturnCorrectReportType() {
        assertThat(generator.getReportType()).isEqualTo("EXPERIENCE");
    }
}