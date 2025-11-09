package org.iut.refactoring;

public class ProjectManager extends Employee {

    public ProjectManager(String name, double baseSalary, int yearsOfExperience, String division) {
        super("CHEF DE PROJET", name, baseSalary, yearsOfExperience, division);
    }

    protected ProjectManager(String id, String name, double baseSalary, int yearsOfExperience, String division) {
        super(id, "CHEF DE PROJET", name, baseSalary, yearsOfExperience, division);
    }

    @Override
    public double calculateSalary() {
        double salary = getBaseSalary() * SalaryConstants.PROJECT_MANAGER_BASE_MULTIPLIER;

        if (getYearsOfExperience() > SalaryConstants.PROJECT_MANAGER_EXPERIENCE_THRESHOLD) {
            salary = salary * SalaryConstants.PROJECT_MANAGER_EXPERIENCE_MULTIPLIER;
        }

        salary = salary + SalaryConstants.PROJECT_MANAGER_FIXED_BONUS;

        return salary;
    }

    @Override
    public double calculateInitialSalary() {
        double salary = getBaseSalary() * SalaryConstants.PROJECT_MANAGER_BASE_MULTIPLIER;

        if (getYearsOfExperience() > SalaryConstants.PROJECT_MANAGER_EXPERIENCE_THRESHOLD) {
            salary = salary * SalaryConstants.PROJECT_MANAGER_EXPERIENCE_MULTIPLIER;
        }

        return salary;
    }

    @Override
    public double calculateAnnualBonus() {
        double bonus = getBaseSalary() * SalaryConstants.PROJECT_MANAGER_ANNUAL_BONUS_RATE;

        if (getYearsOfExperience() > SalaryConstants.PROJECT_MANAGER_EXPERIENCE_THRESHOLD) {
            bonus = bonus * SalaryConstants.PROJECT_MANAGER_ANNUAL_BONUS_EXPERIENCE_MULTIPLIER;
        }

        return bonus;
    }
}