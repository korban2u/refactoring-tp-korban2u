package org.iut.refactoring.model;

import org.iut.refactoring.EmployeeType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class InternTest {

    @Test
    void shouldCalculateSalaryForIntern() {
        Intern intern = new Intern("Alice", 20000, 0, "IT");

        double salary = intern.calculateSalary();

        assertThat(salary).isEqualTo(20000 * 0.6);
    }

    @Test
    void shouldCalculateInitialSalaryForIntern() {
        Intern intern = new Intern("Bob", 20000, 0, "IT");

        double initialSalary = intern.calculateInitialSalary();

        assertThat(initialSalary).isEqualTo(20000 * 0.6);
    }

    @Test
    void shouldNotReceiveBonus() {
        Intern intern = new Intern("Charlie", 20000, 0, "IT");

        double bonus = intern.calculateAnnualBonus();

        assertThat(bonus).isEqualTo(0);
    }

    @Test
    void shouldHaveCorrectType() {
        Intern intern = new Intern("Alice", 20000, 0, "IT");

        assertThat(intern.getType()).isEqualTo("STAGIAIRE");
        assertThat(intern.getEmployeeType()).isEqualTo(EmployeeType.STAGIAIRE);
    }
}