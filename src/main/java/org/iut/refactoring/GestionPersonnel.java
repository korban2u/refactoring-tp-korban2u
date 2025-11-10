package org.iut.refactoring;

import org.iut.refactoring.factory.EmployeeFactory;
import org.iut.refactoring.model.Employee;
import org.iut.refactoring.reports.ReportService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service principal de gestion du personnel.
 * Orchestre les opérations entre le repository, les employés et les rapports.
 */
public class GestionPersonnel {

    private final EmployeeRepository employeeRepository;
    private final ReportService reportService;
    private final Map<String, Double> salairesEmployes;
    private final List<String> logs;

    public GestionPersonnel() {
        this.employeeRepository = new EmployeeRepository();
        this.reportService = new ReportService();
        this.salairesEmployes = new HashMap<>();
        this.logs = new ArrayList<>();
    }

    /**
     * Ajoute un nouvel employé au système.
     */
    public void ajouteSalarie(String type, String nom, double salaireDeBase, int experience, String equipe) {
        Employee employee = EmployeeFactory.createEmployee(type, nom, salaireDeBase, experience, equipe);
        employeeRepository.add(employee);

        double salaireFinal = employee.calculateInitialSalary();
        salairesEmployes.put(employee.getId(), salaireFinal);

        logs.add(LocalDateTime.now() + " - Ajout de l'employé: " + nom);
    }

    /**
     * Calcule le salaire d'un employé.
     * @return le salaire calculé, ou 0 si l'employé n'existe pas
     */
    public double calculSalaire(String employeId) {
        Optional<Employee> empOpt = employeeRepository.findById(employeId);

        if (empOpt.isEmpty()) {
            System.out.println("ERREUR: impossible de trouver l'employé");
            return 0;
        }

        return empOpt.get().calculateSalary();
    }

    /**
     * Génère un rapport selon le type spécifié.
     */
    public void generationRapport(String typeRapport, String filtre) {
        List<Employee> employees = employeeRepository.findAll();
        reportService.generateReport(typeRapport, employees, filtre);
        logs.add(LocalDateTime.now() + " - Rapport généré: " + typeRapport);
    }

    /**
     * Promeut un employé vers un nouveau type de poste.
     */
    public void avancementEmploye(String employeId, String newType) {
        Optional<Employee> oldEmployeeOpt = employeeRepository.findById(employeId);

        if (oldEmployeeOpt.isEmpty()) {
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

    /**
     * Récupère tous les employés d'une division.
     */
    public List<Employee> getEmployeesByDivision(String division) {
        return employeeRepository.findByDivision(division);
    }

    /**
     * Affiche tous les logs d'activité.
     */
    public void printLogs() {
        System.out.println("=== LOGS ===");
        for (String log : logs) {
            System.out.println(log);
        }
    }

    /**
     * Calcule le bonus annuel d'un employé.
     * @return le bonus calculé, ou 0 si l'employé n'existe pas
     */
    public double calculBonusAnnuel(String employeId) {
        Optional<Employee> empOpt = employeeRepository.findById(employeId);

        return empOpt.map(Employee::calculateAnnualBonus).orElse(0.0);

    }

    /**
     * Récupère tous les employés.
     */
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Retourne le nombre d'employés.
     */
    public int getEmployeeCount() {
        return employeeRepository.count();
    }

    /**
     * Récupère les salaires enregistrés (pour compatibilité avec les tests).
     */
    public Map<String, Double> getSalairesEmployes() {
        return new HashMap<>(salairesEmployes);
    }

    /**
     * Récupère les logs (pour compatibilité avec les tests).
     */
    public List<String> getLogs() {
        return new ArrayList<>(logs);
    }
}