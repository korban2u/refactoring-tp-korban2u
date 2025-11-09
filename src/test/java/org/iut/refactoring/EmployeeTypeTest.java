package org.iut.refactoring;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EmployeeTypeTest {

    @Test
    void shouldConvertDeveloperFromString() {
        EmployeeType type = EmployeeType.fromString("DEVELOPPEUR");

        assertThat(type).isEqualTo(EmployeeType.DEVELOPPEUR);
        assertThat(type.getLabel()).isEqualTo("DEVELOPPEUR");
    }

    @Test
    void shouldConvertProjectManagerFromString() {
        EmployeeType type = EmployeeType.fromString("CHEF DE PROJET");

        assertThat(type).isEqualTo(EmployeeType.CHEF_DE_PROJET);
        assertThat(type.getLabel()).isEqualTo("CHEF DE PROJET");
    }

    @Test
    void shouldConvertInternFromString() {
        EmployeeType type = EmployeeType.fromString("STAGIAIRE");

        assertThat(type).isEqualTo(EmployeeType.STAGIAIRE);
        assertThat(type.getLabel()).isEqualTo("STAGIAIRE");
    }

    @Test
    void shouldReturnAutreForUnknownType() {
        EmployeeType type = EmployeeType.fromString("SUPER_HERO");

        assertThat(type).isEqualTo(EmployeeType.AUTRE);
    }

    @Test
    void shouldReturnAutreForNullString() {
        EmployeeType type = EmployeeType.fromString(null);

        assertThat(type).isEqualTo(EmployeeType.AUTRE);
    }

    @Test
    void shouldReturnCorrectToString() {
        assertThat(EmployeeType.DEVELOPPEUR.toString()).isEqualTo("DEVELOPPEUR");
        assertThat(EmployeeType.CHEF_DE_PROJET.toString()).isEqualTo("CHEF DE PROJET");
        assertThat(EmployeeType.STAGIAIRE.toString()).isEqualTo("STAGIAIRE");
    }

    @Test
    void shouldHaveAllExpectedValues() {
        EmployeeType[] types = EmployeeType.values();

        assertThat(types).hasSize(4);
        assertThat(types).contains(
                EmployeeType.DEVELOPPEUR,
                EmployeeType.CHEF_DE_PROJET,
                EmployeeType.STAGIAIRE,
                EmployeeType.AUTRE
        );
    }
}
