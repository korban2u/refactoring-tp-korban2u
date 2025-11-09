package org.iut.refactoring;

import java.util.*;
import java.time.*;

public class GestionPersonnel {

    private ArrayList<Employee> employees = new ArrayList<>();
    public HashMap<String, Double> salairesEmployes = new HashMap<>();
    public ArrayList<String> logs = new ArrayList<>();

    public void ajouteSalarie(String type, String nom, double salaireDeBase, int experience, String equipe) {
        EmployeeType employeeType = EmployeeType.fromString(type);

        Employee employee = new Employee(employeeType.getLabel(), nom, salaireDeBase, experience, equipe);
        employees.add(employee);

        double salaireFinal = calculerSalaireInitial(employeeType, salaireDeBase, experience);

        salairesEmployes.put(employee.getId(), salaireFinal);

        logs.add(LocalDateTime.now() + " - Ajout de l'employé: " + nom);
    }

    private double calculerSalaireInitial(EmployeeType type, double salaireDeBase, int experience) {
        double salaireFinal = salaireDeBase;

        switch (type) {
            case DEVELOPPEUR:
                salaireFinal = salaireDeBase * SalaryConstants.DEVELOPER_BASE_MULTIPLIER;
                if (experience > SalaryConstants.DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_1) {
                    salaireFinal = salaireFinal * SalaryConstants.DEVELOPER_EXPERIENCE_MULTIPLIER_LEVEL_1;
                }
                break;
            case CHEF_DE_PROJET:
                salaireFinal = salaireDeBase * SalaryConstants.PROJECT_MANAGER_BASE_MULTIPLIER;
                if (experience > SalaryConstants.PROJECT_MANAGER_EXPERIENCE_THRESHOLD) {
                    salaireFinal = salaireFinal * SalaryConstants.PROJECT_MANAGER_EXPERIENCE_MULTIPLIER;
                }
                break;
            case STAGIAIRE:
                salaireFinal = salaireDeBase * SalaryConstants.INTERN_BASE_MULTIPLIER;
                break;
            default:
                salaireFinal = salaireDeBase;
                break;
        }

        return salaireFinal;
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

        EmployeeType type = emp.getEmployeeType();
        double salaireDeBase = emp.getBaseSalary();
        int experience = emp.getYearsOfExperience();

        double salaireFinal = salaireDeBase;

        switch (type) {
            case DEVELOPPEUR:
                salaireFinal = salaireDeBase * SalaryConstants.DEVELOPER_BASE_MULTIPLIER;
                if (experience > SalaryConstants.DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_1) {
                    salaireFinal = salaireFinal * SalaryConstants.DEVELOPER_EXPERIENCE_MULTIPLIER_LEVEL_1;
                }
                if (experience > SalaryConstants.DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_2) {
                    salaireFinal = salaireFinal * SalaryConstants.DEVELOPER_EXPERIENCE_MULTIPLIER_LEVEL_2;
                }
                break;
            case CHEF_DE_PROJET:
                salaireFinal = salaireDeBase * SalaryConstants.PROJECT_MANAGER_BASE_MULTIPLIER;
                if (experience > SalaryConstants.PROJECT_MANAGER_EXPERIENCE_THRESHOLD) {
                    salaireFinal = salaireFinal * SalaryConstants.PROJECT_MANAGER_EXPERIENCE_MULTIPLIER;
                }
                salaireFinal = salaireFinal + SalaryConstants.PROJECT_MANAGER_FIXED_BONUS;
                break;
            case STAGIAIRE:
                salaireFinal = salaireDeBase * SalaryConstants.INTERN_BASE_MULTIPLIER;
                break;
            default:
                salaireFinal = salaireDeBase;
                break;
        }

        return salaireFinal;
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
        Employee emp = findEmployeeById(employeId);

        if (emp == null) {
            System.out.println("ERREUR: impossible de trouver l'employé");
            return;
        }

        emp.setType(newType);

        double nouveauSalaire = calculSalaire(employeId);
        salairesEmployes.put(employeId, nouveauSalaire);

        logs.add(LocalDateTime.now() + " - Employé promu: " + emp.getName());
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

        EmployeeType type = emp.getEmployeeType();
        int experience = emp.getYearsOfExperience();
        double salaireDeBase = emp.getBaseSalary();

        double bonus = 0;

        switch (type) {
            case DEVELOPPEUR:
                bonus = salaireDeBase * SalaryConstants.DEVELOPER_ANNUAL_BONUS_RATE;
                if (experience > SalaryConstants.DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_1) {
                    bonus = bonus * SalaryConstants.DEVELOPER_ANNUAL_BONUS_EXPERIENCE_MULTIPLIER;
                }
                break;
            case CHEF_DE_PROJET:
                bonus = salaireDeBase * SalaryConstants.PROJECT_MANAGER_ANNUAL_BONUS_RATE;
                if (experience > SalaryConstants.PROJECT_MANAGER_EXPERIENCE_THRESHOLD) {
                    bonus = bonus * SalaryConstants.PROJECT_MANAGER_ANNUAL_BONUS_EXPERIENCE_MULTIPLIER;
                }
                break;
            case STAGIAIRE:
                bonus = 0;
                break;
            default:
                bonus = 0;
                break;
        }

        return bonus;
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public int getEmployeeCount() {
        return employees.size();
    }
}