package org.iut.refactoring;

public final class SalaryConstants {

    // Multiplicateurs de salaire de base par type
    public static final double DEVELOPER_BASE_MULTIPLIER = 1.2;
    public static final double PROJECT_MANAGER_BASE_MULTIPLIER = 1.5;
    public static final double INTERN_BASE_MULTIPLIER = 0.6;

    // Bonus d'expérience pour développeurs
    public static final int DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_1 = 5;
    public static final double DEVELOPER_EXPERIENCE_MULTIPLIER_LEVEL_1 = 1.15;

    public static final int DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_2 = 10;
    public static final double DEVELOPER_EXPERIENCE_MULTIPLIER_LEVEL_2 = 1.05;

    // Bonus d'expérience pour chefs de projet
    public static final int PROJECT_MANAGER_EXPERIENCE_THRESHOLD = 3;
    public static final double PROJECT_MANAGER_EXPERIENCE_MULTIPLIER = 1.1;
    public static final double PROJECT_MANAGER_FIXED_BONUS = 5000.0;

    // Bonus annuel : pourcentages de base
    public static final double DEVELOPER_ANNUAL_BONUS_RATE = 0.1;
    public static final double PROJECT_MANAGER_ANNUAL_BONUS_RATE = 0.2;

    // Bonus annuel : multiplicateurs d'expérience
    public static final double DEVELOPER_ANNUAL_BONUS_EXPERIENCE_MULTIPLIER = 1.5;
    public static final double PROJECT_MANAGER_ANNUAL_BONUS_EXPERIENCE_MULTIPLIER = 1.3;


    private SalaryConstants() {

    }
}