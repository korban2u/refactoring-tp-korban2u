package org.iut.refactoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion de la persistance des employés.
 * Fournit les opérations CRUD de base.
 */
public class EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    /**
     * Ajoute un employé au repository.
     * @param employee l'employé à ajouter
     * @throws IllegalArgumentException si l'employé est null
     */
    public void add(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        employees.add(employee);
    }

    /**
     * Recherche un employé par son ID.
     * @param employeeId l'ID de l'employé
     * @return Optional contenant l'employé si trouvé, vide sinon
     */
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

    /**
     * Recherche tous les employés d'une division.
     * @param division le nom de la division
     * @return liste des employés de cette division
     */
    public List<Employee> findByDivision(String division) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getDivision().equals(division)) {
                result.add(emp);
            }
        }
        return result;
    }

    /**
     * Retourne tous les employés.
     * @return copie de la liste de tous les employés
     */
    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    /**
     * Remplace un employé par un autre (utilisé pour les promotions).
     * @param oldEmployee l'employé à remplacer
     * @param newEmployee le nouvel employé
     */
    public void replace(Employee oldEmployee, Employee newEmployee) {
        int index = employees.indexOf(oldEmployee);
        if (index != -1) {
            employees.set(index, newEmployee);
        }
    }

    /**
     * Retourne le nombre d'employés.
     * @return le nombre total d'employés
     */
    public int count() {
        return employees.size();
    }

    /**
     * Vérifie si un employé existe par son ID.
     * @param employeeId l'ID à vérifier
     * @return true si l'employé existe, false sinon
     */
    public boolean exists(String employeeId) {
        return findById(employeeId).isPresent();
    }

    /**
     * Supprime tous les employés.
     */
    public void clear() {
        employees.clear();
    }
}