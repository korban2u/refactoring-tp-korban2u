package org.iut.refactoring;

public class Intern extends Employee {

    public Intern(String name, double baseSalary, int yearsOfExperience, String division) {
        super("STAGIAIRE", name, baseSalary, yearsOfExperience, division);
    }

    protected Intern(String id, String name, double baseSalary, int yearsOfExperience, String division) {
        super(id, "STAGIAIRE", name, baseSalary, yearsOfExperience, division);
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() * SalaryConstants.INTERN_BASE_MULTIPLIER;
    }

    @Override
    public double calculateInitialSalary() {
        return getBaseSalary() * SalaryConstants.INTERN_BASE_MULTIPLIER;
    }

    @Override
    public double calculateAnnualBonus() {
        return 0; // Les stagiaires sont des exclaves du coup ils ont pas de bonus
    }
}