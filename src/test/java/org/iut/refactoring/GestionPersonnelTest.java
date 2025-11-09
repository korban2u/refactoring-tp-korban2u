package org.iut.refactoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class GestionPersonnelTest {

    private GestionPersonnel gestion;

    @BeforeEach
    void setUp() {
        gestion = new GestionPersonnel();
    }

    @Test
    void shouldAddDeveloperAndCalculateSalary() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        double salaire = gestion.calculSalaire(employeId);
        assertThat(gestion.getEmployees()).hasSize(1);
        assertThat(salaire).isEqualTo(50000 * 1.2 * 1.15);
        assertThat(gestion.salairesEmployes.get(employeId)).isEqualTo(50000 * 1.2 * 1.15);
    }

    @Test
    void shouldAddProjectManagerWithBonus() {
        gestion.ajouteSalarie("CHEF DE PROJET", "Bob", 60000, 4, "RH");
        String employeId = gestion.getEmployees().get(0).getId();

        double salaire = gestion.calculSalaire(employeId);

        assertThat(salaire).isEqualTo(60000 * 1.5 * 1.1 + 5000);
    }

    @Test
    void shouldAddInternWithReducedSalary() {
        gestion.ajouteSalarie("STAGIAIRE", "Charlie", 20000, 0, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        double salaire = gestion.calculSalaire(employeId);

        assertThat(salaire).isEqualTo(20000 * 0.6);
    }

    @Test
    void shouldCalculateSalaryForSeniorDeveloper() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Dan", 55000, 12, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        double salaire = gestion.calculSalaire(employeId);

        assertThat(salaire).isEqualTo(55000 * 1.2 * 1.15 * 1.05);
    }

    @Test
    void shouldCalculateBonusForJuniorDeveloper() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Eve", 50000, 3, "IT");
        String employeId = gestion.getEmployees().get(0).getId();
        double bonus = gestion.calculBonusAnnuel(employeId);

        assertThat(bonus).isEqualTo(50000 * 0.1);
    }

    @Test
    void shouldCalculateBonusForSeniorDeveloper() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Frank", 50000, 6, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        double bonus = gestion.calculBonusAnnuel(employeId);

        assertThat(bonus).isEqualTo(50000 * 0.1 * 1.5);
    }

    @Test
    void shouldCalculateBonusForProjectManager() {
        gestion.ajouteSalarie("CHEF DE PROJET", "Grace", 60000, 5, "RH");
        String employeId = gestion.getEmployees().get(0).getId();

        double bonus = gestion.calculBonusAnnuel(employeId);

        assertThat(bonus).isEqualTo(60000 * 0.2 * 1.3);
    }

    @Test
    void shouldNotCalculateBonusForIntern() {
        gestion.ajouteSalarie("STAGIAIRE", "Hugo", 20000, 0, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        double bonus = gestion.calculBonusAnnuel(employeId);

        assertThat(bonus).isEqualTo(0);
    }

    @Test
    void shouldReturn0ForNonExistentEmployeeSalary() {
        double salaire = gestion.calculSalaire("fake-id");

        assertThat(salaire).isEqualTo(0);
    }

    @Test
    void shouldReturn0ForNonExistentEmployeeBonus() {
        double bonus = gestion.calculBonusAnnuel("fake-id");
        assertThat(bonus).isEqualTo(0);
    }

    @Test
    void shouldPromoteEmployee() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        String employeId = gestion.getEmployees().get(0).getId();
        double ancienSalaire = gestion.calculSalaire(employeId);

        gestion.avancementEmploye(employeId, "CHEF DE PROJET");
        double nouveauSalaire = gestion.calculSalaire(employeId);

        Employee emp = gestion.getEmployees().get(0);
        assertThat(emp.getType()).isEqualTo("CHEF DE PROJET");
        assertThat(nouveauSalaire).isNotEqualTo(ancienSalaire);
        assertThat(nouveauSalaire).isEqualTo(50000 * 1.5 * 1.1 + 5000);
    }

    @Test
    void shouldGetEmployeesByDivision() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        gestion.ajouteSalarie("CHEF DE PROJET", "Bob", 60000, 4, "RH");
        gestion.ajouteSalarie("STAGIAIRE", "Charlie", 20000, 0, "IT");

        List<Employee> employesIT = gestion.getEmployeesByDivision("IT");
        List<Employee> employesRH = gestion.getEmployeesByDivision("RH");

        assertThat(employesIT).hasSize(2);
        assertThat(employesRH).hasSize(1);
        assertThat(employesIT.get(0).getName()).isEqualTo("Alice");
        assertThat(employesIT.get(1).getName()).isEqualTo("Charlie");
        assertThat(employesRH.get(0).getName()).isEqualTo("Bob");
    }

    @Test
    void shouldLogActions() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        String employeId = gestion.getEmployees().get(0).getId();
        gestion.avancementEmploye(employeId, "CHEF DE PROJET");

        assertThat(gestion.logs).hasSize(2);
        assertThat(gestion.logs.get(0)).contains("Ajout de l'employé: Alice");
        assertThat(gestion.logs.get(1)).contains("Employé promu: Alice");
    }

    @Test
    void shouldHandleMultipleEmployeesWithDifferentTypes() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        gestion.ajouteSalarie("CHEF DE PROJET", "Bob", 60000, 4, "RH");
        gestion.ajouteSalarie("STAGIAIRE", "Charlie", 20000, 0, "IT");
        gestion.ajouteSalarie("DEVELOPPEUR", "Dan", 55000, 12, "IT");

        assertThat(gestion.getEmployees()).hasSize(4);
        assertThat(gestion.salairesEmployes).hasSize(4);
        assertThat(gestion.logs).hasSize(4);
    }

    @Test
    void shouldHandleUnknownEmployeeType() {
        gestion.ajouteSalarie("AUTRE", "Xavier", 40000, 2, "IT");
        String employeId = gestion.getEmployees().get(0).getId();

        double salaire = gestion.calculSalaire(employeId);

        assertThat(salaire).isEqualTo(40000);
    }
}