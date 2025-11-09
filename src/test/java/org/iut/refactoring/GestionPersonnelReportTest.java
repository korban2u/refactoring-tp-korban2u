package org.iut.refactoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

class GestionPersonnelReportTest {

    private GestionPersonnel gestion;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        gestion = new GestionPersonnel();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void shouldGenerateSalaryReportForAll() {
        
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        gestion.ajouteSalarie("CHEF DE PROJET", "Bob", 60000, 4, "RH");

        gestion.generationRapport("SALAIRE", null);

        String output = outContent.toString();
        assertThat(output).contains("=== RAPPORT: SALAIRE ===");
        assertThat(output).contains("Alice:");
        assertThat(output).contains("Bob:");
    }

    @Test
    void shouldGenerateSalaryReportFilteredByDivision() {
        
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        gestion.ajouteSalarie("CHEF DE PROJET", "Bob", 60000, 4, "RH");
        gestion.ajouteSalarie("STAGIAIRE", "Charlie", 20000, 0, "IT");

        
        gestion.generationRapport("SALAIRE", "IT");

        
        String output = outContent.toString();
        assertThat(output).contains("Alice:");
        assertThat(output).contains("Charlie:");
        assertThat(output).doesNotContain("Bob:");
    }

    @Test
    void shouldGenerateExperienceReport() {
        
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        gestion.ajouteSalarie("CHEF DE PROJET", "Bob", 60000, 4, "RH");

        
        gestion.generationRapport("EXPERIENCE", null);

        
        String output = outContent.toString();
        assertThat(output).contains("=== RAPPORT: EXPERIENCE ===");
        assertThat(output).contains("Alice: 6 années");
        assertThat(output).contains("Bob: 4 années");
    }

    @Test
    void shouldGenerateDivisionReport() {
        
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        gestion.ajouteSalarie("CHEF DE PROJET", "Bob", 60000, 4, "RH");
        gestion.ajouteSalarie("STAGIAIRE", "Charlie", 20000, 0, "IT");

        
        gestion.generationRapport("DIVISION", null);

        
        String output = outContent.toString();
        assertThat(output).contains("=== RAPPORT: DIVISION ===");
        assertThat(output).contains("IT: 2 employés");
        assertThat(output).contains("RH: 1 employés");
    }

    @Test
    void shouldLogReportGeneration() {
        
        gestion.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");

        
        gestion.generationRapport("SALAIRE", "IT");

        assertThat(gestion.getLogs()).hasSize(2);
        assertThat(gestion.getLogs().get(1)).contains("Rapport généré: SALAIRE");
    }
}