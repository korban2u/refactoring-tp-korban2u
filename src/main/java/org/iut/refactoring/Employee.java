package org.iut.refactoring;

import java.util.UUID;

public class Employee {
    private final String id;
    private String type;
    private final String name;
    private final double baseSalary;
    private final int yearsOfExperience;
    private final String division;

    public Employee(String type, String name, double baseSalary, int yearsOfExperience, String division) {
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

    // TODO méthode pour convertir vers Object[] (c'est pour que ça marche je dois le tej apres )
    public Object[] toArray() {
        return new Object[]{id, type, name, baseSalary, yearsOfExperience, division};
    }

    // Méthode statique pour créer depuis Object[]
    public static Employee fromArray(Object[] array) {
        return new Employee(
                (String) array[0],
                (String) array[1],
                (String) array[2],
                (double) array[3],
                (int) array[4],
                (String) array[5]
        );
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