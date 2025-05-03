package com.falaisia.ship.eventreporting2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConsumptionLineDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionLineDTO.class);
        ConsumptionLineDTO consumptionLineDTO1 = new ConsumptionLineDTO();
        consumptionLineDTO1.setId(1L);
        ConsumptionLineDTO consumptionLineDTO2 = new ConsumptionLineDTO();
        assertThat(consumptionLineDTO1).isNotEqualTo(consumptionLineDTO2);
        consumptionLineDTO2.setId(consumptionLineDTO1.getId());
        assertThat(consumptionLineDTO1).isEqualTo(consumptionLineDTO2);
        consumptionLineDTO2.setId(2L);
        assertThat(consumptionLineDTO1).isNotEqualTo(consumptionLineDTO2);
        consumptionLineDTO1.setId(null);
        assertThat(consumptionLineDTO1).isNotEqualTo(consumptionLineDTO2);
    }
}
