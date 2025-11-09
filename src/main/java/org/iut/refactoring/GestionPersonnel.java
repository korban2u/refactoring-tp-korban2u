package org.iut.refactoring;

import org.iut.refactoring.reports.ReportService;
import java.util.*;
import java.time.*;

public class GestionPersonnel {

    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final ReportService reportService = new ReportService();
    public HashMap<String, Double> salairesEmployes = new HashMap<>();
    public ArrayList<String> logs = new ArrayList<>();

    public void ajouteSalarie(String type, String nom, double salaireDeBase, int experience, String equipe) {
        Employee employee = EmployeeFactory.createEmployee(type, nom, salaireDeBase, experience, equipe);
        employeeRepository.add(employee);

        double salaireFinal = employee.calculateInitialSalary();

        salairesEmployes.put(employee.getId(), salaireFinal);

        logs.add(LocalDateTime.now() + " - Ajout de l'employé: " + nom);
    }

    public double calculSalaire(String employeId) {
        Optional<Employee> empOpt = employeeRepository.findById(employeId);

        if (!empOpt.isPresent()) {
            System.out.println("ERREUR: impossible de trouver l'employé");
            return 0;
        }

        return empOpt.get().calculateSalary();
    }

    public void generationRapport(String typeRapport, String filtre) {
        List<Employee> employees = employeeRepository.findAll();
        reportService.generateReport(typeRapport, employees, filtre);
        logs.add(LocalDateTime.now() + " - Rapport généré: " + typeRapport);
    }

    public void avancementEmploye(String employeId, String newType) {
        Optional<Employee> oldEmployeeOpt = employeeRepository.findById(employeId);

        if (!oldEmployeeOpt.isPresent()) {
            System.out.println("ERREUR: impossible de trouver l'employé");
            return;
        }

        Employee oldEmployee = oldEmployeeOpt.get();

        Employee newEmployee = EmployeeFactory.createEmployeeWithId(
                employeId,
                newType,
                oldEmployee.getName(),
                oldEmployee.getBaseSalary(),
                oldEmployee.getYearsOfExperience(),
                oldEmployee.getDivision()
        );

        employeeRepository.replace(oldEmployee, newEmployee);

        double nouveauSalaire = newEmployee.calculateSalary();
        salairesEmployes.put(employeId, nouveauSalaire);

        logs.add(LocalDateTime.now() + " - Employé promu: " + newEmployee.getName());
        System.out.println("Employé promu avec succès!");
    }

    public List<Employee> getEmployeesByDivision(String division) {
        return employeeRepository.findByDivision(division);
    }

    public void printLogs() {
        System.out.println("=== LOGS ===");
        for (String log : logs) {
            System.out.println(log);
        }
    }

    public double calculBonusAnnuel(String employeId) {
        Optional<Employee> empOpt = employeeRepository.findById(employeId);

        if (!empOpt.isPresent()) {
            return 0;
        }

        return empOpt.get().calculateAnnualBonus();
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public int getEmployeeCount() {
        return employeeRepository.count();
    }
}