package org.iut.refactoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class GestionPersonnelMigrationTest {

    private GestionPersonnel gestion;

    @BeforeEach
    void setUp() {
        gestion = new GestionPersonnel();
    }

    @Test
    void shouldMaintainBothOldAndNewSystemsInSync() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");

        assertThat(gestion.employes).hasSize(1);
        assertThat(gestion.getEmployees()).hasSize(1);

        Object[] oldSystem = gestion.employes.get(0);
        Employee newSystem = gestion.getEmployees().get(0);

        assertThat(oldSystem[0]).isEqualTo(newSystem.getId());
        assertThat(oldSystem[1]).isEqualTo(newSystem.getType());
        assertThat(oldSystem[2]).isEqualTo(newSystem.getName());
        assertThat(oldSystem[3]).isEqualTo(newSystem.getBaseSalary());
        assertThat(oldSystem[4]).isEqualTo(newSystem.getYearsOfExperience());
        assertThat(oldSystem[5]).isEqualTo(newSystem.getDivision());
    }

    @Test
    void shouldUseEmployeeObjectsInCalculSalaire() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        Employee employee = gestion.getEmployees().get(0);

        double salaire = gestion.calculSalaire(employee.getId());

        assertThat(salaire).isEqualTo(50000 * 1.2 * 1.15);
    }

    @Test
    void shouldUseEmployeeObjectsInCalculBonus() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        Employee employee = gestion.getEmployees().get(0);

        double bonus = gestion.calculBonusAnnuel(employee.getId());

        assertThat(bonus).isEqualTo(50000 * 0.1 * 1.5);
    }

    @Test
    void shouldUseEmployeeObjectsInGenerationRapport() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        gestion.ajouteSalarie("CHEF DE PROJET", "Bob", 60000, 4, "RH");

        List<Employee> employees = gestion.getEmployees();

        assertThat(employees).hasSize(2);
        assertThat(employees.get(0).getName()).isEqualTo("Alice");
        assertThat(employees.get(1).getName()).isEqualTo("Bob");
    }

    @Test
    void shouldSynchronizeTypeChangesBetweenSystems() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        Employee employee = gestion.getEmployees().get(0);
        String employeeId = employee.getId();

        gestion.avancementEmploye(employeeId, "CHEF DE PROJET");

        Employee updatedEmployee = gestion.getEmployees().get(0);
        Object[] oldSystemArray = gestion.employes.get(0);

        assertThat(updatedEmployee.getType()).isEqualTo("CHEF DE PROJET");
        assertThat(oldSystemArray[1]).isEqualTo("CHEF DE PROJET");
    }

    @Test
    void shouldUseNewGetEmployeesByDivisionMethod() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        gestion.ajouteSalarie("CHEF DE PROJET", "Bob", 60000, 4, "RH");
        gestion.ajouteSalarie("STAGIAIRE", "Charlie", 20000, 0, "IT");

        List<Employee> itEmployees = gestion.getEmployeesByDivision("IT");
        List<Employee> rhEmployees = gestion.getEmployeesByDivision("RH");

        assertThat(itEmployees).hasSize(2);
        assertThat(rhEmployees).hasSize(1);
        assertThat(itEmployees.get(0).getName()).isEqualTo("Alice");
        assertThat(itEmployees.get(1).getName()).isEqualTo("Charlie");
        assertThat(rhEmployees.get(0).getName()).isEqualTo("Bob");
    }

    @Test
    void shouldReturnEmployeeObjectsWithCorrectData() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");

        Employee employee = gestion.getEmployees().get(0);

        assertThat(employee).isNotNull();
        assertThat(employee.getName()).isEqualTo("Alice");
        assertThat(employee.getType()).isEqualTo("DEVELOPPEUR");
        assertThat(employee.getBaseSalary()).isEqualTo(50000);
        assertThat(employee.getYearsOfExperience()).isEqualTo(6);
        assertThat(employee.getDivision()).isEqualTo("IT");
    }

    @Test
    void shouldMaintainOldGetEmployesParDivisionCompatibility() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        gestion.ajouteSalarie("STAGIAIRE", "Charlie", 20000, 0, "IT");

        var oldResult = gestion.getEmployesParDivision("IT");

        assertThat(oldResult).hasSize(2);
        assertThat(oldResult.get(0)[2]).isEqualTo("Alice");
        assertThat(oldResult.get(1)[2]).isEqualTo("Charlie");
    }

    @Test
    void shouldHandleMultipleEmployeesInBothSystems() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        gestion.ajouteSalarie("CHEF DE PROJET", "Bob", 60000, 4, "RH");
        gestion.ajouteSalarie("STAGIAIRE", "Charlie", 20000, 0, "IT");
        gestion.ajouteSalarie("DEVELOPPEUR", "Dan", 55000, 12, "IT");

        assertThat(gestion.getEmployees()).hasSize(4);
        assertThat(gestion.employes).hasSize(4);

        for (int i = 0; i < 4; i++) {
            Employee emp = gestion.getEmployees().get(i);
            Object[] oldEmp = gestion.employes.get(i);

            assertThat(emp.getId()).isEqualTo(oldEmp[0]);
            assertThat(emp.getType()).isEqualTo(oldEmp[1]);
            assertThat(emp.getName()).isEqualTo(oldEmp[2]);
        }
    }

    @Test
    void shouldReturnImmutableListFromGetEmployees() {
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");

        List<Employee> employees1 = gestion.getEmployees();
        List<Employee> employees2 = gestion.getEmployees();

        assertThat(employees1).isNotSameAs(employees2);
        assertThat(employees1).hasSize(1);
        assertThat(employees2).hasSize(1);
    }
}
