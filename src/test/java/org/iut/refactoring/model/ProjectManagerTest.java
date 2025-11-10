package org.iut.refactoring.model;

import org.iut.refactoring.EmployeeType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProjectManagerTest {

    @Test
    void shouldCalculateSalaryForJuniorProjectManager() {
        ProjectManager pm = new ProjectManager("Alice", 60000, 2, "RH");

        double salary = pm.calculateSalary();

        assertThat(salary).isEqualTo(60000 * 1.5 + 5000);
    }

    @Test
    void shouldCalculateSalaryForSeniorProjectManager() {
        ProjectManager pm = new ProjectManager("Bob", 60000, 5, "RH");

        double salary = pm.calculateSalary();

        assertThat(salary).isEqualTo(60000 * 1.5 * 1.1 + 5000);
    }

    @Test
    void shouldCalculateInitialSalaryWithoutFixedBonus() {
        ProjectManager pm = new ProjectManager("Charlie", 60000, 5, "RH");

        double initialSalary = pm.calculateInitialSalary();

        assertThat(initialSalary).isEqualTo(60000 * 1.5 * 1.1);
    }

    @Test
    void shouldCalculateBonusForJuniorProjectManager() {
        ProjectManager pm = new ProjectManager("Diana", 60000, 2, "RH");

        double bonus = pm.calculateAnnualBonus();

        assertThat(bonus).isEqualTo(60000 * 0.2);
    }

    @Test
    void shouldCalculateBonusForSeniorProjectManager() {
        ProjectManager pm = new ProjectManager("Eve", 60000, 5, "RH");

        double bonus = pm.calculateAnnualBonus();

        assertThat(bonus).isEqualTo(60000 * 0.2 * 1.3);
    }

    @Test
    void shouldHaveCorrectType() {
        ProjectManager pm = new ProjectManager("Alice", 60000, 5, "RH");

        assertThat(pm.getType()).isEqualTo("CHEF DE PROJET");
        assertThat(pm.getEmployeeType()).isEqualTo(EmployeeType.CHEF_DE_PROJET);
    }
}