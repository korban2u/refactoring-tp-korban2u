package org.iut.refactoring;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DeveloperTest {

    @Test
    void shouldCalculateSalaryForJuniorDeveloper() {
        Developer dev = new Developer("Alice", 50000, 3, "IT");

        double salary = dev.calculateSalary();

        assertThat(salary).isEqualTo(50000 * 1.2);
    }

    @Test
    void shouldCalculateSalaryForMidLevelDeveloper() {
        Developer dev = new Developer("Bob", 50000, 6, "IT");

        double salary = dev.calculateSalary();

        assertThat(salary).isEqualTo(50000 * 1.2 * 1.15);
    }

    @Test
    void shouldCalculateSalaryForSeniorDeveloper() {
        Developer dev = new Developer("Charlie", 50000, 12, "IT");

        double salary = dev.calculateSalary();

        assertThat(salary).isEqualTo(50000 * 1.2 * 1.15 * 1.05);
    }

    @Test
    void shouldCalculateInitialSalaryWithoutLevel2Bonus() {
        Developer dev = new Developer("Dan", 50000, 12, "IT");

        double initialSalary = dev.calculateInitialSalary();

        assertThat(initialSalary).isEqualTo(50000 * 1.2 * 1.15);
    }

    @Test
    void shouldCalculateBonusForJuniorDeveloper() {
        Developer dev = new Developer("Eve", 50000, 3, "IT");

        double bonus = dev.calculateAnnualBonus();

        assertThat(bonus).isEqualTo(50000 * 0.1);
    }

    @Test
    void shouldCalculateBonusForSeniorDeveloper() {
        Developer dev = new Developer("Frank", 50000, 6, "IT");

        double bonus = dev.calculateAnnualBonus();

        assertThat(bonus).isEqualTo(50000 * 0.1 * 1.5);
    }

    @Test
    void shouldHaveCorrectType() {
        Developer dev = new Developer("Alice", 50000, 5, "IT");

        assertThat(dev.getType()).isEqualTo("DEVELOPPEUR");
        assertThat(dev.getEmployeeType()).isEqualTo(EmployeeType.DEVELOPPEUR);
    }
}