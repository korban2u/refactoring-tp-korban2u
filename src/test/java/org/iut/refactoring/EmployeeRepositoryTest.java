package org.iut.refactoring;

import org.iut.refactoring.model.Developer;
import org.iut.refactoring.model.Employee;
import org.iut.refactoring.model.ProjectManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class EmployeeRepositoryTest {

    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new EmployeeRepository();
    }

    @Test
    void shouldAddEmployee() {
        Developer dev = new Developer("Alice", 50000, 5, "IT");

        repository.add(dev);

        assertThat(repository.count()).isEqualTo(1);
        assertThat(repository.findAll()).containsExactly(dev);
    }

    @Test
    void shouldThrowExceptionWhenAddingNullEmployee() {
        assertThatThrownBy(() -> repository.add(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Un employé ne peux pas être null");
    }

    @Test
    void shouldFindEmployeeById() {
        Developer dev = new Developer("Alice", 50000, 5, "IT");
        repository.add(dev);

        Optional<Employee> found = repository.findById(dev.getId());

        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(dev);
    }

    @Test
    void shouldReturnEmptyWhenEmployeeNotFound() {
        Optional<Employee> found = repository.findById("non-existent-id");

        assertThat(found).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenFindByIdWithNull() {
        Optional<Employee> found = repository.findById(null);

        assertThat(found).isEmpty();
    }

    @Test
    void shouldFindEmployeesByDivision() {
        Developer dev1 = new Developer("Alice", 50000, 5, "IT");
        Developer dev2 = new Developer("Bob", 55000, 7, "IT");
        ProjectManager pm = new ProjectManager("Charlie", 60000, 4, "RH");

        repository.add(dev1);
        repository.add(dev2);
        repository.add(pm);

        List<Employee> itEmployees = repository.findByDivision("IT");

        assertThat(itEmployees).hasSize(2);
        assertThat(itEmployees).containsExactly(dev1, dev2);
    }

    @Test
    void shouldReturnEmptyListWhenNoDivisionMatch() {
        Developer dev = new Developer("Alice", 50000, 5, "IT");
        repository.add(dev);

        List<Employee> result = repository.findByDivision("RH");

        assertThat(result).isEmpty();
    }

    @Test
    void shouldFindAllEmployees() {
        Developer dev = new Developer("Alice", 50000, 5, "IT");
        ProjectManager pm = new ProjectManager("Bob", 60000, 4, "RH");

        repository.add(dev);
        repository.add(pm);

        List<Employee> all = repository.findAll();

        assertThat(all).hasSize(2);
        assertThat(all).containsExactly(dev, pm);
    }

    @Test
    void shouldReturnEmptyListWhenNoEmployees() {
        List<Employee> all = repository.findAll();

        assertThat(all).isEmpty();
    }

    @Test
    void shouldReplaceEmployee() {
        Developer dev = new Developer("Alice", 50000, 5, "IT");
        repository.add(dev);

        ProjectManager pm = new ProjectManager(dev.getId(), "Alice", 50000, 5, "IT");

        repository.replace(dev, pm);

        List<Employee> all = repository.findAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0)).isInstanceOf(ProjectManager.class);
        assertThat(all.get(0).getId()).isEqualTo(dev.getId());
    }

    @Test
    void shouldNotReplaceWhenEmployeeNotFound() {
        Developer dev1 = new Developer("Alice", 50000, 5, "IT");
        Developer dev2 = new Developer("Bob", 55000, 7, "IT");
        ProjectManager pm = new ProjectManager("Charlie", 60000, 4, "RH");

        repository.add(dev1);

        repository.replace(dev2, pm);

        assertThat(repository.count()).isEqualTo(1);
        assertThat(repository.findAll()).containsExactly(dev1);
    }

    @Test
    void shouldCountEmployees() {
        assertThat(repository.count()).isEqualTo(0);

        repository.add(new Developer("Alice", 50000, 5, "IT"));
        assertThat(repository.count()).isEqualTo(1);

        repository.add(new ProjectManager("Bob", 60000, 4, "RH"));
        assertThat(repository.count()).isEqualTo(2);
    }

    @Test
    void shouldCheckIfEmployeeExists() {
        Developer dev = new Developer("Alice", 50000, 5, "IT");
        repository.add(dev);

        assertThat(repository.exists(dev.getId())).isTrue();
        assertThat(repository.exists("non-existent-id")).isFalse();
    }

    @Test
    void shouldClearAllEmployees() {
        repository.add(new Developer("Alice", 50000, 5, "IT"));
        repository.add(new ProjectManager("Bob", 60000, 4, "RH"));

        assertThat(repository.count()).isEqualTo(2);

        repository.clear();

        assertThat(repository.count()).isEqualTo(0);
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    void shouldReturnIndependentListFromFindAll() {
        Developer dev = new Developer("Alice", 50000, 5, "IT");
        repository.add(dev);

        List<Employee> list1 = repository.findAll();
        List<Employee> list2 = repository.findAll();

        assertThat(list1).isNotSameAs(list2);
    }

    @Test
    void shouldHandleMultipleEmployeesWithSameName() {
        Developer dev1 = new Developer("Alice", 50000, 5, "IT");
        Developer dev2 = new Developer("Alice", 55000, 7, "RH");

        repository.add(dev1);
        repository.add(dev2);

        assertThat(repository.count()).isEqualTo(2);
        assertThat(repository.findById(dev1.getId())).isPresent();
        assertThat(repository.findById(dev2.getId())).isPresent();
    }
}
