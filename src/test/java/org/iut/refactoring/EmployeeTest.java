package org.iut.refactoring;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EmployeeTest {

    @Test
    void shouldCreateEmployeeWithAllFields() {
        Employee employee = new Employee("DEVELOPPEUR", "Alice", 50000, 6, "IT");

        assertThat(employee.getId()).isNotNull();
        assertThat(employee.getType()).isEqualTo("DEVELOPPEUR");
        assertThat(employee.getName()).isEqualTo("Alice");
        assertThat(employee.getBaseSalary()).isEqualTo(50000);
        assertThat(employee.getYearsOfExperience()).isEqualTo(6);
        assertThat(employee.getDivision()).isEqualTo("IT");
    }

    @Test
    void shouldGenerateUniqueIds() {
        Employee emp1 = new Employee("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        Employee emp2 = new Employee("DEVELOPPEUR", "Bob", 50000, 6, "IT");

        assertThat(emp1.getId()).isNotEqualTo(emp2.getId());
    }

    @Test
    void shouldAllowTypeModification() {
        Employee employee = new Employee("DEVELOPPEUR", "Alice", 50000, 6, "IT");

        employee.setType("CHEF DE PROJET");

        assertThat(employee.getType()).isEqualTo("CHEF DE PROJET");
    }

    @Test
    void shouldReturnCorrectEmployeeType() {
        Employee developer = new Employee("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        Employee manager = new Employee("CHEF DE PROJET", "Bob", 60000, 4, "RH");
        Employee intern = new Employee("STAGIAIRE", "Charlie", 20000, 0, "IT");

        assertThat(developer.getEmployeeType()).isEqualTo(EmployeeType.DEVELOPPEUR);
        assertThat(manager.getEmployeeType()).isEqualTo(EmployeeType.CHEF_DE_PROJET);
        assertThat(intern.getEmployeeType()).isEqualTo(EmployeeType.STAGIAIRE);
    }

    @Test
    void shouldConvertToArray() {
        Employee employee = new Employee("id-123", "DEVELOPPEUR", "Alice", 50000, 6, "IT");

        Object[] array = employee.toArray();

        assertThat(array).hasSize(6);
        assertThat(array[0]).isEqualTo("id-123");
        assertThat(array[1]).isEqualTo("DEVELOPPEUR");
        assertThat(array[2]).isEqualTo("Alice");
        assertThat(array[3]).isEqualTo(50000.0);
        assertThat(array[4]).isEqualTo(6);
        assertThat(array[5]).isEqualTo("IT");
    }

    @Test
    void shouldCreateFromArray() {
        Object[] array = new Object[]{"id-123", "DEVELOPPEUR", "Alice", 50000.0, 6, "IT"};

        Employee employee = Employee.fromArray(array);

        assertThat(employee.getId()).isEqualTo("id-123");
        assertThat(employee.getType()).isEqualTo("DEVELOPPEUR");
        assertThat(employee.getName()).isEqualTo("Alice");
        assertThat(employee.getBaseSalary()).isEqualTo(50000.0);
        assertThat(employee.getYearsOfExperience()).isEqualTo(6);
        assertThat(employee.getDivision()).isEqualTo("IT");
    }

    @Test
    void shouldHaveCorrectToStringRepresentation() {
        Employee employee = new Employee("id-123", "DEVELOPPEUR", "Alice", 50000, 6, "IT");

        String result = employee.toString();

        assertThat(result).contains("id-123");
        assertThat(result).contains("DEVELOPPEUR");
        assertThat(result).contains("Alice");
        assertThat(result).contains("50000");
        assertThat(result).contains("6");
        assertThat(result).contains("IT");
    }

    @Test
    void shouldCreateEmployeeWithZeroExperience() {
        Employee employee = new Employee("STAGIAIRE", "Junior", 20000, 0, "IT");

        assertThat(employee.getYearsOfExperience()).isEqualTo(0);
    }

    @Test
    void shouldCreateEmployeeWithHighExperience() {
        Employee employee = new Employee("DEVELOPPEUR", "Senior", 80000, 25, "IT");

        assertThat(employee.getYearsOfExperience()).isEqualTo(25);
    }

    @Test
    void shouldHandleUnknownEmployeeType() {
        Employee employee = new Employee("CONSULTANT", "John", 50000, 5, "IT");

        assertThat(employee.getEmployeeType()).isEqualTo(EmployeeType.AUTRE);
    }

    @Test
    void shouldMaintainImmutabilityOfCoreFields() {
        Employee employee = new Employee("DEVELOPPEUR", "Alice", 50000, 6, "IT");

        String originalId = employee.getId();
        String originalName = employee.getName();
        double originalSalary = employee.getBaseSalary();
        int originalExperience = employee.getYearsOfExperience();
        String originalDivision = employee.getDivision();

        // Seul le type peut changer
        employee.setType("CHEF DE PROJET");

        assertThat(employee.getId()).isEqualTo(originalId);
        assertThat(employee.getName()).isEqualTo(originalName);
        assertThat(employee.getBaseSalary()).isEqualTo(originalSalary);
        assertThat(employee.getYearsOfExperience()).isEqualTo(originalExperience);
        assertThat(employee.getDivision()).isEqualTo(originalDivision);
        assertThat(employee.getType()).isEqualTo("CHEF DE PROJET");
    }
}
