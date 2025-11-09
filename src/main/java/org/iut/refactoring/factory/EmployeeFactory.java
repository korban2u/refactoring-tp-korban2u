package org.iut.refactoring.factory;

import org.iut.refactoring.EmployeeType;
import org.iut.refactoring.model.Developer;
import org.iut.refactoring.model.Employee;
import org.iut.refactoring.model.Intern;
import org.iut.refactoring.model.ProjectManager;

public class EmployeeFactory {

    public static Employee createEmployee(String type, String name, double baseSalary, int yearsOfExperience, String division) {
        EmployeeType employeeType = EmployeeType.fromString(type);

        switch (employeeType) {
            case DEVELOPPEUR:
                return new Developer(name, baseSalary, yearsOfExperience, division);
            case CHEF_DE_PROJET:
                return new ProjectManager(name, baseSalary, yearsOfExperience, division);
            case STAGIAIRE:
                return new Intern(name, baseSalary, yearsOfExperience, division);
            default:
                return new GenericEmployee(type, name, baseSalary, yearsOfExperience, division);
        }
    }

    public static Employee createEmployeeWithId(String id, String type, String name, double baseSalary, int yearsOfExperience, String division) {
        EmployeeType employeeType = EmployeeType.fromString(type);

        switch (employeeType) {
            case DEVELOPPEUR:
                return new Developer(id, name, baseSalary, yearsOfExperience, division);
            case CHEF_DE_PROJET:
                return new ProjectManager(id, name, baseSalary, yearsOfExperience, division);
            case STAGIAIRE:
                return new Intern(id, name, baseSalary, yearsOfExperience, division);
            default:
                return new GenericEmployee(id, type, name, baseSalary, yearsOfExperience, division);
        }
    }

    // Classe interne pour les golmons qui vont s'amuser à creer un employé qui existe pas
    // il a des stats de base
    private static class GenericEmployee extends Employee {

        public GenericEmployee(String type, String name, double baseSalary, int yearsOfExperience, String division) {
            super(type, name, baseSalary, yearsOfExperience, division);
        }

        public GenericEmployee(String id, String type, String name, double baseSalary, int yearsOfExperience, String division) {
            super(id, type, name, baseSalary, yearsOfExperience, division);
        }

        @Override
        public double calculateSalary() {
            return getBaseSalary();
        }

        @Override
        public double calculateInitialSalary() {
            return getBaseSalary();
        }

        @Override
        public double calculateAnnualBonus() {
            return 0;
        }
    }
}