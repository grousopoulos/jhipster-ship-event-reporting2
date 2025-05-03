package com.falaisia.ship.eventreporting2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassificationSocietyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassificationSocietyDTO.class);
        ClassificationSocietyDTO classificationSocietyDTO1 = new ClassificationSocietyDTO();
        classificationSocietyDTO1.setId(1L);
        ClassificationSocietyDTO classificationSocietyDTO2 = new ClassificationSocietyDTO();
        assertThat(classificationSocietyDTO1).isNotEqualTo(classificationSocietyDTO2);
        classificationSocietyDTO2.setId(classificationSocietyDTO1.getId());
        assertThat(classificationSocietyDTO1).isEqualTo(classificationSocietyDTO2);
        classificationSocietyDTO2.setId(2L);
        assertThat(classificationSocietyDTO1).isNotEqualTo(classificationSocietyDTO2);
        classificationSocietyDTO1.setId(null);
        assertThat(classificationSocietyDTO1).isNotEqualTo(classificationSocietyDTO2);
    }
}
