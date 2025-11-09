package org.iut.refactoring;

import java.util.*;
import java.time.*;

public class GestionPersonnel {

    private ArrayList<Employee> employees = new ArrayList<>();
    public HashMap<String, Double> salairesEmployes = new HashMap<>();
    public ArrayList<String> logs = new ArrayList<>();

    public void ajouteSalarie(String type, String nom, double salaireDeBase, int experience, String equipe) {
        Employee employee = EmployeeFactory.createEmployee(type, nom, salaireDeBase, experience, equipe);
        employees.add(employee);

        double salaireFinal = employee.calculateInitialSalary();

        salairesEmployes.put(employee.getId(), salaireFinal);

        logs.add(LocalDateTime.now() + " - Ajout de l'employé: " + nom);
    }

    private Employee findEmployeeById(String employeId) {
        for (Employee emp : employees) {
            if (emp.getId().equals(employeId)) {
                return emp;
            }
        }
        return null;
    }

    public double calculSalaire(String employeId) {
        Employee emp = findEmployeeById(employeId);

        if (emp == null) {
            System.out.println("ERREUR: impossible de trouver l'employé");
            return 0;
        }

        return emp.calculateSalary();
    }

    public void generationRapport(String typeRapport, String filtre) {
        System.out.println("=== RAPPORT: " + typeRapport + " ===");

        if (typeRapport.equals("SALAIRE")) {
            for (Employee emp : employees) {
                if (filtre == null || filtre.isEmpty() ||
                        emp.getDivision().equals(filtre)) {
                    String id = emp.getId();
                    String nom = emp.getName();
                    double salaire = calculSalaire(id);
                    System.out.println(nom + ": " + salaire + " €");
                }
            }
        } else if (typeRapport.equals("EXPERIENCE")) {
            for (Employee emp : employees) {
                if (filtre == null || filtre.isEmpty() ||
                        emp.getDivision().equals(filtre)) {
                    String nom = emp.getName();
                    int exp = emp.getYearsOfExperience();
                    System.out.println(nom + ": " + exp + " années");
                }
            }
        } else if (typeRapport.equals("DIVISION")) {
            HashMap<String, Integer> compteurDivisions = new HashMap<>();
            for (Employee emp : employees) {
                String div = emp.getDivision();
                compteurDivisions.put(div, compteurDivisions.getOrDefault(div, 0) + 1);
            }
            for (Map.Entry<String, Integer> entry : compteurDivisions.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " employés");
            }
        }
        logs.add(LocalDateTime.now() + " - Rapport généré: " + typeRapport);
    }

    public void avancementEmploye(String employeId, String newType) {
        Employee oldEmployee = findEmployeeById(employeId);

        if (oldEmployee == null) {
            System.out.println("ERREUR: impossible de trouver l'employé");
            return;
        }

        Employee newEmployee = EmployeeFactory.createEmployeeWithId(
                employeId,
                newType,
                oldEmployee.getName(),
                oldEmployee.getBaseSalary(),
                oldEmployee.getYearsOfExperience(),
                oldEmployee.getDivision()
        );

        // Remplacer l'ancien employé par le nouveau dans la liste
        int index = employees.indexOf(oldEmployee);
        if (index != -1) {
            employees.set(index, newEmployee);
        }

        double nouveauSalaire = newEmployee.calculateSalary();
        salairesEmployes.put(employeId, nouveauSalaire);

        logs.add(LocalDateTime.now() + " - Employé promu: " + newEmployee.getName());
        System.out.println("Employé promu avec succès!");
    }

    public List<Employee> getEmployeesByDivision(String division) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getDivision().equals(division)) {
                result.add(emp);
            }
        }
        return result;
    }

    public void printLogs() {
        System.out.println("=== LOGS ===");
        for (String log : logs) {
            System.out.println(log);
        }
    }

    public double calculBonusAnnuel(String employeId) {
        Employee emp = findEmployeeById(employeId);

        if (emp == null) return 0;

        return emp.calculateAnnualBonus();
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public int getEmployeeCount() {
        return employees.size();
    }
}