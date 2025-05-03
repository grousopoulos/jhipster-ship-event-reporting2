package com.falaisia.ship.eventreporting2.domain;

import static com.falaisia.ship.eventreporting2.domain.ShipTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.VoyageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoyageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voyage.class);
        Voyage voyage1 = getVoyageSample1();
        Voyage voyage2 = new Voyage();
        assertThat(voyage1).isNotEqualTo(voyage2);

        voyage2.setId(voyage1.getId());
        assertThat(voyage1).isEqualTo(voyage2);

        voyage2 = getVoyageSample2();
        assertThat(voyage1).isNotEqualTo(voyage2);
    }

    @Test
    void shipTest() {
        Voyage voyage = getVoyageRandomSampleGenerator();
        Ship shipBack = getShipRandomSampleGenerator();

        voyage.setShip(shipBack);
        assertThat(voyage.getShip()).isEqualTo(shipBack);

        voyage.ship(null);
        assertThat(voyage.getShip()).isNull();
    }
}
