package org.iut.refactoring;

import java.util.*;
import java.time.*;

public class GestionPersonnel {

    // TODO Ancien systeme de merde  (à tej plus apres)
    public ArrayList<Object[]> employes = new ArrayList<>();

    // Nouveau système
    private ArrayList<Employee> employees = new ArrayList<>();

    public HashMap<String, Double> salairesEmployes = new HashMap<>();
    public ArrayList<String> logs = new ArrayList<>();

    public void ajouteSalarie(String type, String nom, double salaireDeBase, int experience, String equipe) {
        EmployeeType employeeType = EmployeeType.fromString(type);

        // Créer l'objet Employee (nouveau système)
        Employee employee = new Employee(employeeType.getLabel(), nom, salaireDeBase, experience, equipe);
        employees.add(employee);

        // TODO SUPRIMER ancien système
        employes.add(employee.toArray());

        double salaireFinal = calculerSalaireInitial(employeeType, salaireDeBase, experience);

        salairesEmployes.put(employee.getId(), salaireFinal);

        logs.add(LocalDateTime.now() + " - Ajout de l'employé: " + nom);
    }

    private double calculerSalaireInitial(EmployeeType type, double salaireDeBase, int experience) {
        double salaireFinal = salaireDeBase;

        switch (type) {
            case DEVELOPPEUR:
                salaireFinal = salaireDeBase * 1.2;
                if (experience > 5) {
                    salaireFinal = salaireFinal * 1.15;
                }
                break;
            case CHEF_DE_PROJET:
                salaireFinal = salaireDeBase * 1.5;
                if (experience > 3) {
                    salaireFinal = salaireFinal * 1.1;
                }
                break;
            case STAGIAIRE:
                salaireFinal = salaireDeBase * 0.6;
                break;
            default:
                salaireFinal = salaireDeBase;
                break;
        }

        return salaireFinal;
    }

    // Nouvelle méthode utilisant Employee
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
                salaireFinal = salaireDeBase * 1.2;
                if (experience > 5) {
                    salaireFinal = salaireFinal * 1.15;
                }
                if (experience > 10) {
                    salaireFinal = salaireFinal * 1.05;
                }
                break;
            case CHEF_DE_PROJET:
                salaireFinal = salaireDeBase * 1.5;
                if (experience > 3) {
                    salaireFinal = salaireFinal * 1.1;
                }
                salaireFinal = salaireFinal + 5000;
                break;
            case STAGIAIRE:
                salaireFinal = salaireDeBase * 0.6;
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

        // Synchroniser avec l'ancien système
        for (Object[] oldEmp : employes) {
            if (oldEmp[0].equals(employeId)) {
                oldEmp[1] = newType;
                break;
            }
        }

        double nouveauSalaire = calculSalaire(employeId);
        salairesEmployes.put(employeId, nouveauSalaire);

        logs.add(LocalDateTime.now() + " - Employé promu: " + emp.getName());
        System.out.println("Employé promu avec succès!");
    }

    public ArrayList<Object[]> getEmployesParDivision(String division) {
        ArrayList<Object[]> resultat = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getDivision().equals(division)) {
                resultat.add(emp.toArray());
            }
        }
        return resultat;
    }

    // Nouvelle méthode qui utilsie Employee
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
                bonus = salaireDeBase * 0.1;
                if (experience > 5) {
                    bonus = bonus * 1.5;
                }
                break;
            case CHEF_DE_PROJET:
                bonus = salaireDeBase * 0.2;
                if (experience > 3) {
                    bonus = bonus * 1.3;
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

    // Méthode pour recup les employé
    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }
}