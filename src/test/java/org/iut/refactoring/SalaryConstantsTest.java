package org.iut.refactoring;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SalaryConstantsTest {

    @Test
    void shouldHaveCorrectDeveloperConstants() {
        assertThat(SalaryConstants.DEVELOPER_BASE_MULTIPLIER).isEqualTo(1.2);
        assertThat(SalaryConstants.DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_1).isEqualTo(5);
        assertThat(SalaryConstants.DEVELOPER_EXPERIENCE_MULTIPLIER_LEVEL_1).isEqualTo(1.15);
        assertThat(SalaryConstants.DEVELOPER_EXPERIENCE_THRESHOLD_LEVEL_2).isEqualTo(10);
        assertThat(SalaryConstants.DEVELOPER_EXPERIENCE_MULTIPLIER_LEVEL_2).isEqualTo(1.05);
    }

    @Test
    void shouldHaveCorrectProjectManagerConstants() {
        assertThat(SalaryConstants.PROJECT_MANAGER_BASE_MULTIPLIER).isEqualTo(1.5);
        assertThat(SalaryConstants.PROJECT_MANAGER_EXPERIENCE_THRESHOLD).isEqualTo(3);
        assertThat(SalaryConstants.PROJECT_MANAGER_EXPERIENCE_MULTIPLIER).isEqualTo(1.1);
        assertThat(SalaryConstants.PROJECT_MANAGER_FIXED_BONUS).isEqualTo(5000.0);
    }

    @Test
    void shouldHaveCorrectInternConstants() {
        assertThat(SalaryConstants.INTERN_BASE_MULTIPLIER).isEqualTo(0.6);
    }

    @Test
    void shouldHaveCorrectAnnualBonusConstants() {
        assertThat(SalaryConstants.DEVELOPER_ANNUAL_BONUS_RATE).isEqualTo(0.1);
        assertThat(SalaryConstants.PROJECT_MANAGER_ANNUAL_BONUS_RATE).isEqualTo(0.2);
        assertThat(SalaryConstants.DEVELOPER_ANNUAL_BONUS_EXPERIENCE_MULTIPLIER).isEqualTo(1.5);
        assertThat(SalaryConstants.PROJECT_MANAGER_ANNUAL_BONUS_EXPERIENCE_MULTIPLIER).isEqualTo(1.3);
    }

    @Test
    void shouldBeAFinalClass() {
        assertThat(java.lang.reflect.Modifier.isFinal(SalaryConstants.class.getModifiers())).isTrue();
    }

    @Test
    void shouldHaveOnlyPublicStaticFinalFields() {
        java.lang.reflect.Field[] fields = SalaryConstants.class.getDeclaredFields();

        for (java.lang.reflect.Field field : fields) {
            int modifiers = field.getModifiers();
            assertThat(java.lang.reflect.Modifier.isPublic(modifiers)).isTrue();
            assertThat(java.lang.reflect.Modifier.isStatic(modifiers)).isTrue();
            assertThat(java.lang.reflect.Modifier.isFinal(modifiers)).isTrue();
        }
    }
}
