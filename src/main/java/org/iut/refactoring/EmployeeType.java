package org.iut.refactoring;

public enum EmployeeType {
    DEVELOPPEUR("DEVELOPPEUR"),
    CHEF_DE_PROJET("CHEF DE PROJET"),
    STAGIAIRE("STAGIAIRE"),
    AUTRE("AUTRE");

    private final String label;

    EmployeeType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static EmployeeType fromString(String typeString) {
        for (EmployeeType type : EmployeeType.values()) {
            if (type.label.equals(typeString)) {
                return type;
            }
        }
        return AUTRE;
    }

    @Override
    public String toString() {
        return label;
    }
}