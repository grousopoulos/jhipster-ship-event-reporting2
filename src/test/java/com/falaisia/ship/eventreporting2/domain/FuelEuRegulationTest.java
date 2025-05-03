package com.falaisia.ship.eventreporting2.domain;

import static com.falaisia.ship.eventreporting2.domain.FuelEuRegulationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuelEuRegulationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelEuRegulation.class);
        FuelEuRegulation fuelEuRegulation1 = getFuelEuRegulationSample1();
        FuelEuRegulation fuelEuRegulation2 = new FuelEuRegulation();
        assertThat(fuelEuRegulation1).isNotEqualTo(fuelEuRegulation2);

        fuelEuRegulation2.setId(fuelEuRegulation1.getId());
        assertThat(fuelEuRegulation1).isEqualTo(fuelEuRegulation2);

        fuelEuRegulation2 = getFuelEuRegulationSample2();
        assertThat(fuelEuRegulation1).isNotEqualTo(fuelEuRegulation2);
    }
}
