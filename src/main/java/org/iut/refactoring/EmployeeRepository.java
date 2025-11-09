package org.iut.refactoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    public void add(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        employees.add(employee);
    }

    public Optional<Employee> findById(String employeeId) {
        if (employeeId == null) {
            return Optional.empty();
        }

        for (Employee emp : employees) {
            if (emp.getId().equals(employeeId)) {
                return Optional.of(emp);
            }
        }
        return Optional.empty();
    }

    public List<Employee> findByDivision(String division) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getDivision().equals(division)) {
                result.add(emp);
            }
        }
        return result;
    }

    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    public void replace(Employee oldEmployee, Employee newEmployee) {
        int index = employees.indexOf(oldEmployee);
        if (index != -1) {
            employees.set(index, newEmployee);
        }
    }

    public int count() {
        return employees.size();
    }

    public boolean exists(String employeeId) {
        return findById(employeeId).isPresent();
    }

    public void clear() {
        employees.clear();
    }
}