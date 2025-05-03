package com.falaisia.ship.eventreporting2.domain;

import static com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteLineTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.FuelTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BunkerReceivedNoteLineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BunkerReceivedNoteLine.class);
        BunkerReceivedNoteLine bunkerReceivedNoteLine1 = getBunkerReceivedNoteLineSample1();
        BunkerReceivedNoteLine bunkerReceivedNoteLine2 = new BunkerReceivedNoteLine();
        assertThat(bunkerReceivedNoteLine1).isNotEqualTo(bunkerReceivedNoteLine2);

        bunkerReceivedNoteLine2.setId(bunkerReceivedNoteLine1.getId());
        assertThat(bunkerReceivedNoteLine1).isEqualTo(bunkerReceivedNoteLine2);

        bunkerReceivedNoteLine2 = getBunkerReceivedNoteLineSample2();
        assertThat(bunkerReceivedNoteLine1).isNotEqualTo(bunkerReceivedNoteLine2);
    }

    @Test
    void bunkerReceivedNoteTest() {
        BunkerReceivedNoteLine bunkerReceivedNoteLine = getBunkerReceivedNoteLineRandomSampleGenerator();
        BunkerReceivedNote bunkerReceivedNoteBack = getBunkerReceivedNoteRandomSampleGenerator();

        bunkerReceivedNoteLine.setBunkerReceivedNote(bunkerReceivedNoteBack);
        assertThat(bunkerReceivedNoteLine.getBunkerReceivedNote()).isEqualTo(bunkerReceivedNoteBack);

        bunkerReceivedNoteLine.bunkerReceivedNote(null);
        assertThat(bunkerReceivedNoteLine.getBunkerReceivedNote()).isNull();
    }

    @Test
    void fuelTypeTest() {
        BunkerReceivedNoteLine bunkerReceivedNoteLine = getBunkerReceivedNoteLineRandomSampleGenerator();
        FuelType fuelTypeBack = getFuelTypeRandomSampleGenerator();

        bunkerReceivedNoteLine.setFuelType(fuelTypeBack);
        assertThat(bunkerReceivedNoteLine.getFuelType()).isEqualTo(fuelTypeBack);

        bunkerReceivedNoteLine.fuelType(null);
        assertThat(bunkerReceivedNoteLine.getFuelType()).isNull();
    }
}
