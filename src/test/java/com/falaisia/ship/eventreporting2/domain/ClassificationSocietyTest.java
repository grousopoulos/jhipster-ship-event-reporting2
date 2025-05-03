package com.falaisia.ship.eventreporting2.domain;

import static com.falaisia.ship.eventreporting2.domain.ClassificationSocietyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassificationSocietyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassificationSociety.class);
        ClassificationSociety classificationSociety1 = getClassificationSocietySample1();
        ClassificationSociety classificationSociety2 = new ClassificationSociety();
        assertThat(classificationSociety1).isNotEqualTo(classificationSociety2);

        classificationSociety2.setId(classificationSociety1.getId());
        assertThat(classificationSociety1).isEqualTo(classificationSociety2);

        classificationSociety2 = getClassificationSocietySample2();
        assertThat(classificationSociety1).isNotEqualTo(classificationSociety2);
    }
}
