package org.iut.refactoring;

public class SalaryCalculator {

    public double calculateSalary(Employee employee) {
        if (employee == null) {
            return 0;
        }

        EmployeeType type = employee.getEmployeeType();
        double baseSalary = employee.getBaseSalary();
        int experience = employee.getYearsOfExperience();

        return calculateSalaryByType(type, baseSalary, experience);
    }

    public double calculateInitialSalary(EmployeeType type, double baseSalary, int experience) {
        return calculateBaseSalaryByType(type, baseSalary, experience);
    }

    public double calculateAnnualBonus(Employee employee) {
        if (employee == null) {
            return 0;
        }

        EmployeeType type = employee.getEmployeeType();
        double baseSalary = employee.getBaseSalary();
        int experience = employee.getYearsOfExperience();

        return calculateAnnualBonusByType(type, baseSalary, experience);
    }

    private double calculateSalaryByType(EmployeeType type, double baseSalary, int experience) {
        double salary = baseSalary;

        switch (type) {
            case DEVELOPPEUR:
                salary = calculateDeveloperSalary(baseSalary, experience);
                break;
            case CHEF_DE_PROJET:
                salary = calculateProjectManagerSalary(baseSalary, experience);
                break;
            case STAGIAIRE:
                salary = calculateInternSalary(baseSalary);
                break;
            default:
                salary = baseSalary;
                break;
        }

        return salary;
    }

    private double calculateBaseSalaryByType(EmployeeType type, double baseSalary, int experience) {
        double salary = baseSalary;

        switch (type) {
            case DEVELOPPEUR:
                salary = calculateDeveloperBaseSalary(baseSalary, experience);
                break;
            case CHEF_DE_PROJET:
                salary = calculateProjectManagerBaseSalary(baseSalary, experience);
                break;
            case STAGIAIRE:
                salary = calculateInternSalary(baseSalary);
                break;
            default:
                salary = baseSalary;
                break;
        }

        return salary;
    }

    private double calculateDeveloperSalary(double baseSalary, int experience) {
        double salary = baseSalary * SalaryConstants.DEVELOPER_BASE_MULTIPLIER;

        if (experience > SalaryConstants.DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_1) {
            salary = salary * SalaryConstants.DEVELOPER_EXPERIENCE_MULTIPLIER_LEVEL_1;
        }

        if (experience > SalaryConstants.DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_2) {
            salary = salary * SalaryConstants.DEVELOPER_EXPERIENCE_MULTIPLIER_LEVEL_2;
        }

        return salary;
    }

    private double calculateDeveloperBaseSalary(double baseSalary, int experience) {
        double salary = baseSalary * SalaryConstants.DEVELOPER_BASE_MULTIPLIER;

        if (experience > SalaryConstants.DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_1) {
            salary = salary * SalaryConstants.DEVELOPER_EXPERIENCE_MULTIPLIER_LEVEL_1;
        }

        return salary;
    }

    private double calculateProjectManagerSalary(double baseSalary, int experience) {
        double salary = baseSalary * SalaryConstants.PROJECT_MANAGER_BASE_MULTIPLIER;

        if (experience > SalaryConstants.PROJECT_MANAGER_EXPERIENCE_THRESHOLD) {
            salary = salary * SalaryConstants.PROJECT_MANAGER_EXPERIENCE_MULTIPLIER;
        }

        salary = salary + SalaryConstants.PROJECT_MANAGER_FIXED_BONUS;

        return salary;
    }

    private double calculateProjectManagerBaseSalary(double baseSalary, int experience) {
        double salary = baseSalary * SalaryConstants.PROJECT_MANAGER_BASE_MULTIPLIER;

        if (experience > SalaryConstants.PROJECT_MANAGER_EXPERIENCE_THRESHOLD) {
            salary = salary * SalaryConstants.PROJECT_MANAGER_EXPERIENCE_MULTIPLIER;
        }

        return salary;
    }

    private double calculateInternSalary(double baseSalary) {
        return baseSalary * SalaryConstants.INTERN_BASE_MULTIPLIER;
    }

    private double calculateAnnualBonusByType(EmployeeType type, double baseSalary, int experience) {
        double bonus = 0;

        switch (type) {
            case DEVELOPPEUR:
                bonus = calculateDeveloperAnnualBonus(baseSalary, experience);
                break;
            case CHEF_DE_PROJET:
                bonus = calculateProjectManagerAnnualBonus(baseSalary, experience);
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

    private double calculateDeveloperAnnualBonus(double baseSalary, int experience) {
        double bonus = baseSalary * SalaryConstants.DEVELOPER_ANNUAL_BONUS_RATE;

        if (experience > SalaryConstants.DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_1) {
            bonus = bonus * SalaryConstants.DEVELOPER_ANNUAL_BONUS_EXPERIENCE_MULTIPLIER;
        }

        return bonus;
    }

    private double calculateProjectManagerAnnualBonus(double baseSalary, int experience) {
        double bonus = baseSalary * SalaryConstants.PROJECT_MANAGER_ANNUAL_BONUS_RATE;

        if (experience > SalaryConstants.PROJECT_MANAGER_EXPERIENCE_THRESHOLD) {
            bonus = bonus * SalaryConstants.PROJECT_MANAGER_ANNUAL_BONUS_EXPERIENCE_MULTIPLIER;
        }

        return bonus;
    }
}