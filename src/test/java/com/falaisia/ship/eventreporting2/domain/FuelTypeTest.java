package com.falaisia.ship.eventreporting2.domain;

import static com.falaisia.ship.eventreporting2.domain.FuelTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuelTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelType.class);
        FuelType fuelType1 = getFuelTypeSample1();
        FuelType fuelType2 = new FuelType();
        assertThat(fuelType1).isNotEqualTo(fuelType2);

        fuelType2.setId(fuelType1.getId());
        assertThat(fuelType1).isEqualTo(fuelType2);

        fuelType2 = getFuelTypeSample2();
        assertThat(fuelType1).isNotEqualTo(fuelType2);
    }
}
