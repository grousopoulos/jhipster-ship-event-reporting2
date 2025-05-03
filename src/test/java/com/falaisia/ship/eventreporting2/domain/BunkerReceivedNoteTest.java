package com.falaisia.ship.eventreporting2.domain;

import static com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteLineTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.VoyageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BunkerReceivedNoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BunkerReceivedNote.class);
        BunkerReceivedNote bunkerReceivedNote1 = getBunkerReceivedNoteSample1();
        BunkerReceivedNote bunkerReceivedNote2 = new BunkerReceivedNote();
        assertThat(bunkerReceivedNote1).isNotEqualTo(bunkerReceivedNote2);

        bunkerReceivedNote2.setId(bunkerReceivedNote1.getId());
        assertThat(bunkerReceivedNote1).isEqualTo(bunkerReceivedNote2);

        bunkerReceivedNote2 = getBunkerReceivedNoteSample2();
        assertThat(bunkerReceivedNote1).isNotEqualTo(bunkerReceivedNote2);
    }

    @Test
    void voyageTest() {
        BunkerReceivedNote bunkerReceivedNote = getBunkerReceivedNoteRandomSampleGenerator();
        Voyage voyageBack = getVoyageRandomSampleGenerator();

        bunkerReceivedNote.setVoyage(voyageBack);
        assertThat(bunkerReceivedNote.getVoyage()).isEqualTo(voyageBack);

        bunkerReceivedNote.voyage(null);
        assertThat(bunkerReceivedNote.getVoyage()).isNull();
    }

    @Test
    void linesTest() {
        BunkerReceivedNote bunkerReceivedNote = getBunkerReceivedNoteRandomSampleGenerator();
        BunkerReceivedNoteLine bunkerReceivedNoteLineBack = getBunkerReceivedNoteLineRandomSampleGenerator();

        bunkerReceivedNote.addLines(bunkerReceivedNoteLineBack);
        assertThat(bunkerReceivedNote.getLines()).containsOnly(bunkerReceivedNoteLineBack);
        assertThat(bunkerReceivedNoteLineBack.getBunkerReceivedNote()).isEqualTo(bunkerReceivedNote);

        bunkerReceivedNote.removeLines(bunkerReceivedNoteLineBack);
        assertThat(bunkerReceivedNote.getLines()).doesNotContain(bunkerReceivedNoteLineBack);
        assertThat(bunkerReceivedNoteLineBack.getBunkerReceivedNote()).isNull();

        bunkerReceivedNote.lines(new HashSet<>(Set.of(bunkerReceivedNoteLineBack)));
        assertThat(bunkerReceivedNote.getLines()).containsOnly(bunkerReceivedNoteLineBack);
        assertThat(bunkerReceivedNoteLineBack.getBunkerReceivedNote()).isEqualTo(bunkerReceivedNote);

        bunkerReceivedNote.setLines(new HashSet<>());
        assertThat(bunkerReceivedNote.getLines()).doesNotContain(bunkerReceivedNoteLineBack);
        assertThat(bunkerReceivedNoteLineBack.getBunkerReceivedNote()).isNull();
    }
}
