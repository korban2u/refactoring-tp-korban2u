package org.iut.refactoring.factory;

import org.iut.refactoring.model.Developer;
import org.iut.refactoring.model.Employee;
import org.iut.refactoring.model.Intern;
import org.iut.refactoring.model.ProjectManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EmployeeFactoryTest {

    @Test
    void shouldCreateDeveloper() {
        Employee emp = EmployeeFactory.createEmployee("DEVELOPPEUR", "Alice", 50000, 5, "IT");

        assertThat(emp).isInstanceOf(Developer.class);
        assertThat(emp.getName()).isEqualTo("Alice");
        assertThat(emp.getType()).isEqualTo("DEVELOPPEUR");
    }

    @Test
    void shouldCreateProjectManager() {
        Employee emp = EmployeeFactory.createEmployee("CHEF DE PROJET", "Bob", 60000, 4, "RH");

        assertThat(emp).isInstanceOf(ProjectManager.class);
        assertThat(emp.getName()).isEqualTo("Bob");
        assertThat(emp.getType()).isEqualTo("CHEF DE PROJET");
    }

    @Test
    void shouldCreateIntern() {
        Employee emp = EmployeeFactory.createEmployee("STAGIAIRE", "Charlie", 20000, 0, "IT");

        assertThat(emp).isInstanceOf(Intern.class);
        assertThat(emp.getName()).isEqualTo("Charlie");
        assertThat(emp.getType()).isEqualTo("STAGIAIRE");
    }

    @Test
    void shouldCreateGenericEmployeeForUnknownType() {
        Employee emp = EmployeeFactory.createEmployee("CONSULTANT", "Diana", 40000, 3, "IT");

        assertThat(emp).isNotNull();
        assertThat(emp.getName()).isEqualTo("Diana");
        assertThat(emp.getType()).isEqualTo("CONSULTANT");
        assertThat(emp.calculateSalary()).isEqualTo(40000);
        assertThat(emp.calculateAnnualBonus()).isEqualTo(0);
    }
}