package com.falaisia.ship.eventreporting2.service.mapper;

import static com.falaisia.ship.eventreporting2.domain.VoyageAsserts.*;
import static com.falaisia.ship.eventreporting2.domain.VoyageTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoyageMapperTest {

    private VoyageMapper voyageMapper;

    @BeforeEach
    void setUp() {
        voyageMapper = new VoyageMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getVoyageSample1();
        var actual = voyageMapper.toEntity(voyageMapper.toDto(expected));
        assertVoyageAllPropertiesEquals(expected, actual);
    }
}
