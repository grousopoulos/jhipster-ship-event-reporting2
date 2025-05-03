package com.falaisia.ship.eventreporting2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MachineryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MachineryDTO.class);
        MachineryDTO machineryDTO1 = new MachineryDTO();
        machineryDTO1.setId(1L);
        MachineryDTO machineryDTO2 = new MachineryDTO();
        assertThat(machineryDTO1).isNotEqualTo(machineryDTO2);
        machineryDTO2.setId(machineryDTO1.getId());
        assertThat(machineryDTO1).isEqualTo(machineryDTO2);
        machineryDTO2.setId(2L);
        assertThat(machineryDTO1).isNotEqualTo(machineryDTO2);
        machineryDTO1.setId(null);
        assertThat(machineryDTO1).isNotEqualTo(machineryDTO2);
    }
}
