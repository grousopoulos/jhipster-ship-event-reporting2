package com.falaisia.ship.eventreporting2.service.mapper;

import static com.falaisia.ship.eventreporting2.domain.FlagAsserts.*;
import static com.falaisia.ship.eventreporting2.domain.FlagTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FlagMapperTest {

    private FlagMapper flagMapper;

    @BeforeEach
    void setUp() {
        flagMapper = new FlagMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFlagSample1();
        var actual = flagMapper.toEntity(flagMapper.toDto(expected));
        assertFlagAllPropertiesEquals(expected, actual);
    }
}
