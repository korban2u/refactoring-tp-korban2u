package org.iut.refactoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SalaryCalculatorTest {

    private SalaryCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new SalaryCalculator();
    }

    @Test
    void shouldCalculateDeveloperSalaryWithLowExperience() {
        Employee dev = new Employee("DEVELOPPEUR", "Alice", 50000, 3, "IT");

        double salary = calculator.calculateSalary(dev);

        assertThat(salary).isEqualTo(50000 * 1.2);
    }

    @Test
    void shouldCalculateDeveloperSalaryWithMediumExperience() {
        Employee dev = new Employee("DEVELOPPEUR", "Bob", 50000, 6, "IT");

        double salary = calculator.calculateSalary(dev);

        assertThat(salary).isEqualTo(50000 * 1.2 * 1.15);
    }

    @Test
    void shouldCalculateDeveloperSalaryWithHighExperience() {
        Employee dev = new Employee("DEVELOPPEUR", "Charlie", 50000, 12, "IT");

        double salary = calculator.calculateSalary(dev);

        assertThat(salary).isEqualTo(50000 * 1.2 * 1.15 * 1.05);
    }

    @Test
    void shouldCalculateProjectManagerSalaryWithLowExperience() {
        Employee pm = new Employee("CHEF DE PROJET", "Diana", 60000, 2, "RH");

        double salary = calculator.calculateSalary(pm);

        assertThat(salary).isEqualTo(60000 * 1.5 + 5000);
    }

    @Test
    void shouldCalculateProjectManagerSalaryWithHighExperience() {
        Employee pm = new Employee("CHEF DE PROJET", "Eve", 60000, 5, "RH");

        double salary = calculator.calculateSalary(pm);

        assertThat(salary).isEqualTo(60000 * 1.5 * 1.1 + 5000);
    }

    @Test
    void shouldCalculateInternSalary() {
        Employee intern = new Employee("STAGIAIRE", "Frank", 20000, 0, "IT");

        double salary = calculator.calculateSalary(intern);

        assertThat(salary).isEqualTo(20000 * 0.6);
    }

    @Test
    void shouldCalculateUnknownTypeSalary() {
        Employee other = new Employee("CONSULTANT", "George", 40000, 5, "IT");

        double salary = calculator.calculateSalary(other);

        assertThat(salary).isEqualTo(40000);
    }

    @Test
    void shouldReturn0ForNullEmployee() {
        double salary = calculator.calculateSalary(null);

        assertThat(salary).isEqualTo(0);
    }

    @Test
    void shouldCalculateInitialSalaryForDeveloper() {
        double salary = calculator.calculateInitialSalary(EmployeeType.DEVELOPPEUR, 50000, 6);

        assertThat(salary).isEqualTo(50000 * 1.2 * 1.15);
    }

    @Test
    void shouldCalculateInitialSalaryForProjectManager() {
        double salary = calculator.calculateInitialSalary(EmployeeType.CHEF_DE_PROJET, 60000, 4);

        assertThat(salary).isEqualTo(60000 * 1.5 * 1.1);
    }

    @Test
    void shouldCalculateInitialSalaryForIntern() {
        double salary = calculator.calculateInitialSalary(EmployeeType.STAGIAIRE, 20000, 0);

        assertThat(salary).isEqualTo(20000 * 0.6);
    }

    @Test
    void shouldCalculateDeveloperAnnualBonusWithLowExperience() {
        Employee dev = new Employee("DEVELOPPEUR", "Alice", 50000, 3, "IT");

        double bonus = calculator.calculateAnnualBonus(dev);

        assertThat(bonus).isEqualTo(50000 * 0.1);
    }

    @Test
    void shouldCalculateDeveloperAnnualBonusWithHighExperience() {
        Employee dev = new Employee("DEVELOPPEUR", "Bob", 50000, 6, "IT");

        double bonus = calculator.calculateAnnualBonus(dev);

        assertThat(bonus).isEqualTo(50000 * 0.1 * 1.5);
    }

    @Test
    void shouldCalculateProjectManagerAnnualBonusWithLowExperience() {
        Employee pm = new Employee("CHEF DE PROJET", "Charlie", 60000, 2, "RH");

        double bonus = calculator.calculateAnnualBonus(pm);

        assertThat(bonus).isEqualTo(60000 * 0.2);
    }

    @Test
    void shouldCalculateProjectManagerAnnualBonusWithHighExperience() {
        Employee pm = new Employee("CHEF DE PROJET", "Diana", 60000, 5, "RH");

        double bonus = calculator.calculateAnnualBonus(pm);

        assertThat(bonus).isEqualTo(60000 * 0.2 * 1.3);
    }

    @Test
    void shouldCalculateZeroBonusForIntern() {
        Employee intern = new Employee("STAGIAIRE", "Eve", 20000, 0, "IT");

        double bonus = calculator.calculateAnnualBonus(intern);

        assertThat(bonus).isEqualTo(0);
    }

    @Test
    void shouldCalculateZeroBonusForUnknownType() {
        Employee other = new Employee("CONSULTANT", "Frank", 40000, 5, "IT");

        double bonus = calculator.calculateAnnualBonus(other);

        assertThat(bonus).isEqualTo(0);
    }

    @Test
    void shouldReturn0BonusForNullEmployee() {
        double bonus = calculator.calculateAnnualBonus(null);

        assertThat(bonus).isEqualTo(0);
    }

    @Test
    void shouldCalculateSalaryWithZeroBaseSalary() {
        Employee dev = new Employee("DEVELOPPEUR", "Alice", 0, 5, "IT");

        double salary = calculator.calculateSalary(dev);

        assertThat(salary).isEqualTo(0);
    }

    @Test
    void shouldCalculateSalaryWithNegativeExperience() {
        Employee dev = new Employee("DEVELOPPEUR", "Bob", 50000, -1, "IT");

        double salary = calculator.calculateSalary(dev);

        assertThat(salary).isEqualTo(50000 * 1.2);
    }
}
