package com.falaisia.ship.eventreporting2.service.mapper;

import static com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteLineAsserts.*;
import static com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteLineTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BunkerReceivedNoteLineMapperTest {

    private BunkerReceivedNoteLineMapper bunkerReceivedNoteLineMapper;

    @BeforeEach
    void setUp() {
        bunkerReceivedNoteLineMapper = new BunkerReceivedNoteLineMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBunkerReceivedNoteLineSample1();
        var actual = bunkerReceivedNoteLineMapper.toEntity(bunkerReceivedNoteLineMapper.toDto(expected));
        assertBunkerReceivedNoteLineAllPropertiesEquals(expected, actual);
    }
}
