package com.falaisia.ship.eventreporting2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MachineryOperationLineDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MachineryOperationLineDTO.class);
        MachineryOperationLineDTO machineryOperationLineDTO1 = new MachineryOperationLineDTO();
        machineryOperationLineDTO1.setId(1L);
        MachineryOperationLineDTO machineryOperationLineDTO2 = new MachineryOperationLineDTO();
        assertThat(machineryOperationLineDTO1).isNotEqualTo(machineryOperationLineDTO2);
        machineryOperationLineDTO2.setId(machineryOperationLineDTO1.getId());
        assertThat(machineryOperationLineDTO1).isEqualTo(machineryOperationLineDTO2);
        machineryOperationLineDTO2.setId(2L);
        assertThat(machineryOperationLineDTO1).isNotEqualTo(machineryOperationLineDTO2);
        machineryOperationLineDTO1.setId(null);
        assertThat(machineryOperationLineDTO1).isNotEqualTo(machineryOperationLineDTO2);
    }
}
