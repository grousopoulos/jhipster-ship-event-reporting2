package com.falaisia.ship.eventreporting2.domain;

import static com.falaisia.ship.eventreporting2.domain.ClassificationSocietyTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.CountryTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.FlagTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.ShipTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ship.class);
        Ship ship1 = getShipSample1();
        Ship ship2 = new Ship();
        assertThat(ship1).isNotEqualTo(ship2);

        ship2.setId(ship1.getId());
        assertThat(ship1).isEqualTo(ship2);

        ship2 = getShipSample2();
        assertThat(ship1).isNotEqualTo(ship2);
    }

    @Test
    void ownerCountryTest() {
        Ship ship = getShipRandomSampleGenerator();
        Country countryBack = getCountryRandomSampleGenerator();

        ship.setOwnerCountry(countryBack);
        assertThat(ship.getOwnerCountry()).isEqualTo(countryBack);

        ship.ownerCountry(null);
        assertThat(ship.getOwnerCountry()).isNull();
    }

    @Test
    void flagTest() {
        Ship ship = getShipRandomSampleGenerator();
        Flag flagBack = getFlagRandomSampleGenerator();

        ship.setFlag(flagBack);
        assertThat(ship.getFlag()).isEqualTo(flagBack);

        ship.flag(null);
        assertThat(ship.getFlag()).isNull();
    }

    @Test
    void classificationSocietyTest() {
        Ship ship = getShipRandomSampleGenerator();
        ClassificationSociety classificationSocietyBack = getClassificationSocietyRandomSampleGenerator();

        ship.setClassificationSociety(classificationSocietyBack);
        assertThat(ship.getClassificationSociety()).isEqualTo(classificationSocietyBack);

        ship.classificationSociety(null);
        assertThat(ship.getClassificationSociety()).isNull();
    }
}
