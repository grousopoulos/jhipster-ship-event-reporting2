package com.falaisia.ship.eventreporting2.domain;

import static com.falaisia.ship.eventreporting2.domain.MachineryTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.ShipTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MachineryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Machinery.class);
        Machinery machinery1 = getMachinerySample1();
        Machinery machinery2 = new Machinery();
        assertThat(machinery1).isNotEqualTo(machinery2);

        machinery2.setId(machinery1.getId());
        assertThat(machinery1).isEqualTo(machinery2);

        machinery2 = getMachinerySample2();
        assertThat(machinery1).isNotEqualTo(machinery2);
    }

    @Test
    void shipTest() {
        Machinery machinery = getMachineryRandomSampleGenerator();
        Ship shipBack = getShipRandomSampleGenerator();

        machinery.setShip(shipBack);
        assertThat(machinery.getShip()).isEqualTo(shipBack);

        machinery.ship(null);
        assertThat(machinery.getShip()).isNull();
    }
}
