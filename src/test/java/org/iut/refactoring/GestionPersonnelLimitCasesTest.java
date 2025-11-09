package org.iut.refactoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

class GestionPersonnelLimitCasesTest {

    private GestionPersonnel gestion;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        gestion = new GestionPersonnel();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void shouldHandleZeroBaseSalary() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 0, 5, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        double salaire = gestion.calculSalaire(employeId);

        assertThat(salaire).isEqualTo(0);
    }

    @Test
    void shouldHandleNegativeExperience() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Bob", 50000, -1, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        double salaire = gestion.calculSalaire(employeId);

        assertThat(salaire).isEqualTo(50000 * 1.2);
    }

    @Test
    void shouldHandleVeryHighExperience() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Senior", 50000, 30, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        double salaire = gestion.calculSalaire(employeId);

        assertThat(salaire).isEqualTo(50000 * 1.2 * 1.15 * 1.05);
    }

    @Test
    void shouldHandleDeveloperWithExactly5YearsExperience() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Developer", 50000, 5, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        double salaire = gestion.calculSalaire(employeId);

        assertThat(salaire).isEqualTo(50000 * 1.2);
    }

    @Test
    void shouldHandleDeveloperWithExactly10YearsExperience() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Developer", 50000, 10, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        double salaire = gestion.calculSalaire(employeId);

        assertThat(salaire).isEqualTo(50000 * 1.2 * 1.15);
    }

    @Test
    void shouldHandleProjectManagerWithExactly3YearsExperience() {
        gestion.ajouteSalarie("CHEF DE PROJET", "Manager", 60000, 3, "RH");
        String employeId = gestion.getEmployees().get(0).getId();

        double salaire = gestion.calculSalaire(employeId);

        assertThat(salaire).isEqualTo(60000 * 1.5 + 5000);
    }

    @Test
    void shouldHandleEmptyStringFilter() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        gestion.ajouteSalarie("CHEF DE PROJET", "Bob", 60000, 4, "RH");

        gestion.generationRapport("SALAIRE", "");

        String output = outContent.toString();
        assertThat(output).contains("Alice:");
        assertThat(output).contains("Bob:");
    }

    @Test
    void shouldHandleNonExistentDivisionFilter() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");

        gestion.generationRapport("SALAIRE", "MARKETING");

        String output = outContent.toString();
        assertThat(output).contains("=== RAPPORT: SALAIRE ===");
        assertThat(output).doesNotContain("Alice:");
    }

    @Test
    void shouldHandleUnknownReportType() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");

        gestion.generationRapport("INCONNU", null);

        String output = outContent.toString();
        assertThat(output).contains("=== RAPPORT: INCONNU ===");
        assertThat(gestion.logs).anyMatch(log -> log.contains("Rapport généré: INCONNU"));
    }

    @Test
    void shouldHandlePromotionToSameType() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        String employeId = gestion.getEmployees().get(0).getId();
        double ancienSalaire = gestion.calculSalaire(employeId);

        gestion.avancementEmploye(employeId, "DEVELOPPEUR");

        double nouveauSalaire = gestion.calculSalaire(employeId);
        assertThat(nouveauSalaire).isEqualTo(ancienSalaire);
    }

    @Test
    void shouldHandlePromotionToUnknownType() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        gestion.avancementEmploye(employeId, "SUPER_HERO");

        Employee emp = gestion.getEmployees().get(0);
        assertThat(emp.getType()).isEqualTo("SUPER_HERO");
        double salaire = gestion.calculSalaire(employeId);
        assertThat(salaire).isEqualTo(50000);
    }

    @Test
    void shouldHandlePromotionOfNonExistentEmployee() {
        gestion.avancementEmploye("fake-id", "CHEF DE PROJET");

        String output = outContent.toString();
        assertThat(output).contains("ERREUR: impossible de trouver l'employé");
    }

    @Test
    void shouldGetEmployeesFromEmptyDivision() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");

        var result = gestion.getEmployeesByDivision("MARKETING");

        assertThat(result).isEmpty();
    }

    @Test
    void shouldHandleMultiplePromotions() {
        gestion.ajouteSalarie("STAGIAIRE", "Alice", 20000, 0, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        gestion.avancementEmploye(employeId, "DEVELOPPEUR");
        gestion.avancementEmploye(employeId, "CHEF DE PROJET");

        Employee emp = gestion.getEmployees().get(0);
        assertThat(emp).isInstanceOf(ProjectManager.class);
        assertThat(emp.getType()).isEqualTo("CHEF DE PROJET");
        double salaire = gestion.calculSalaire(employeId);
        assertThat(salaire).isEqualTo(20000 * 1.5 + 5000);
    }

    @Test
    void shouldPrintLogsWithoutErrors() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");

        gestion.printLogs();

        String output = outContent.toString();
        assertThat(output).contains("=== LOGS ===");
        assertThat(output).contains("Ajout de l'employé: Alice");
    }

    @Test
    void shouldPrintEmptyLogsWithoutErrors() {
        gestion.printLogs();

        String output = outContent.toString();
        assertThat(output).contains("=== LOGS ===");
    }

    @Test
    void shouldHandleDivisionReportWithSingleEmployee() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");

        gestion.generationRapport("DIVISION", null);

        String output = outContent.toString();
        assertThat(output).contains("IT: 1 employés");
    }

    @Test
    void shouldCalculateBonusForProjectManagerWithExactly3Years() {
        gestion.ajouteSalarie("CHEF DE PROJET", "Manager", 60000, 3, "RH");
        String employeId = gestion.getEmployees().get(0).getId();

        double bonus = gestion.calculBonusAnnuel(employeId);

        assertThat(bonus).isEqualTo(60000 * 0.2);
    }

    @Test
    void shouldCalculateBonusForDeveloperWithExactly5Years() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Dev", 50000, 5, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        double bonus = gestion.calculBonusAnnuel(employeId);

        assertThat(bonus).isEqualTo(50000 * 0.1);
    }

    @Test
    void shouldCalculateBonusForUnknownEmployeeType() {
        gestion.ajouteSalarie("CONSULTANT", "John", 50000, 5, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        double bonus = gestion.calculBonusAnnuel(employeId);

        assertThat(bonus).isEqualTo(0);
    }

    @Test
    void shouldHandleSalaryCalculationWithZeroExperience() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Junior", 50000, 0, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        double salaire = gestion.calculSalaire(employeId);

        assertThat(salaire).isEqualTo(50000 * 1.2);
    }

    @Test
    void shouldHandleExperienceReportWithFilter() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        gestion.ajouteSalarie("CHEF DE PROJET", "Bob", 60000, 4, "RH");

        gestion.generationRapport("EXPERIENCE", "IT");

        String output = outContent.toString();
        assertThat(output).contains("Alice: 6 années");
        assertThat(output).doesNotContain("Bob:");
    }
}
