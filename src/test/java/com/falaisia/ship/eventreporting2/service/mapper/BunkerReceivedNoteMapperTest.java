package com.falaisia.ship.eventreporting2.service.mapper;

import static com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteAsserts.*;
import static com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BunkerReceivedNoteMapperTest {

    private BunkerReceivedNoteMapper bunkerReceivedNoteMapper;

    @BeforeEach
    void setUp() {
        bunkerReceivedNoteMapper = new BunkerReceivedNoteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBunkerReceivedNoteSample1();
        var actual = bunkerReceivedNoteMapper.toEntity(bunkerReceivedNoteMapper.toDto(expected));
        assertBunkerReceivedNoteAllPropertiesEquals(expected, actual);
    }
}
