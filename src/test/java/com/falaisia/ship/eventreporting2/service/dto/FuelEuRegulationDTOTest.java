package com.falaisia.ship.eventreporting2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuelEuRegulationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelEuRegulationDTO.class);
        FuelEuRegulationDTO fuelEuRegulationDTO1 = new FuelEuRegulationDTO();
        fuelEuRegulationDTO1.setId(1L);
        FuelEuRegulationDTO fuelEuRegulationDTO2 = new FuelEuRegulationDTO();
        assertThat(fuelEuRegulationDTO1).isNotEqualTo(fuelEuRegulationDTO2);
        fuelEuRegulationDTO2.setId(fuelEuRegulationDTO1.getId());
        assertThat(fuelEuRegulationDTO1).isEqualTo(fuelEuRegulationDTO2);
        fuelEuRegulationDTO2.setId(2L);
        assertThat(fuelEuRegulationDTO1).isNotEqualTo(fuelEuRegulationDTO2);
        fuelEuRegulationDTO1.setId(null);
        assertThat(fuelEuRegulationDTO1).isNotEqualTo(fuelEuRegulationDTO2);
    }
}
