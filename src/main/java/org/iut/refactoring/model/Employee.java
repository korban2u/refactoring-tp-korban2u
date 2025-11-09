package org.iut.refactoring.model;

import org.iut.refactoring.EmployeeType;

import java.util.UUID;

public abstract class Employee {
    private final String id;
    private String type;
    private final String name;
    private final double baseSalary;
    private final int yearsOfExperience;
    private final String division;

    protected Employee(String type, String name, double baseSalary, int yearsOfExperience, String division) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.name = name;
        this.baseSalary = baseSalary;
        this.yearsOfExperience = yearsOfExperience;
        this.division = division;
    }

    public Employee(String id, String type, String name, double baseSalary, int yearsOfExperience, String division) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.baseSalary = baseSalary;
        this.yearsOfExperience = yearsOfExperience;
        this.division = division;
    }

    // Méthodes abstraites à implémenter par les sous-classes
    public abstract double calculateSalary();
    public abstract double calculateInitialSalary();
    public abstract double calculateAnnualBonus();

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getDivision() {
        return division;
    }

    public EmployeeType getEmployeeType() {
        return EmployeeType.fromString(type);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", baseSalary=" + baseSalary +
                ", yearsOfExperience=" + yearsOfExperience +
                ", division='" + division + '\'' +
                '}';
    }
}