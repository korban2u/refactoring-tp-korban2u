package org.iut.refactoring;

public class Developer extends Employee {

    public Developer(String name, double baseSalary, int yearsOfExperience, String division) {
        super("DEVELOPPEUR", name, baseSalary, yearsOfExperience, division);
    }

    protected Developer(String id, String name, double baseSalary, int yearsOfExperience, String division) {
        super(id, "DEVELOPPEUR", name, baseSalary, yearsOfExperience, division);
    }

    @Override
    public double calculateSalary() {
        double salary = getBaseSalary() * SalaryConstants.DEVELOPER_BASE_MULTIPLIER;

        if (getYearsOfExperience() > SalaryConstants.DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_1) {
            salary = salary * SalaryConstants.DEVELOPER_EXPERIENCE_MULTIPLIER_LEVEL_1;
        }

        if (getYearsOfExperience() > SalaryConstants.DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_2) {
            salary = salary * SalaryConstants.DEVELOPER_EXPERIENCE_MULTIPLIER_LEVEL_2;
        }

        return salary;
    }

    @Override
    public double calculateInitialSalary() {
        double salary = getBaseSalary() * SalaryConstants.DEVELOPER_BASE_MULTIPLIER;

        if (getYearsOfExperience() > SalaryConstants.DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_1) {
            salary = salary * SalaryConstants.DEVELOPER_EXPERIENCE_MULTIPLIER_LEVEL_1;
        }

        return salary;
    }

    @Override
    public double calculateAnnualBonus() {
        double bonus = getBaseSalary() * SalaryConstants.DEVELOPER_ANNUAL_BONUS_RATE;

        if (getYearsOfExperience() > SalaryConstants.DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_1) {
            bonus = bonus * SalaryConstants.DEVELOPER_ANNUAL_BONUS_EXPERIENCE_MULTIPLIER;
        }

        return bonus;
    }
}