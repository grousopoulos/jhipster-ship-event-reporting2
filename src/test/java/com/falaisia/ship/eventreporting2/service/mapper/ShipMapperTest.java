package com.falaisia.ship.eventreporting2.service.mapper;

import static com.falaisia.ship.eventreporting2.domain.ShipAsserts.*;
import static com.falaisia.ship.eventreporting2.domain.ShipTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipMapperTest {

    private ShipMapper shipMapper;

    @BeforeEach
    void setUp() {
        shipMapper = new ShipMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getShipSample1();
        var actual = shipMapper.toEntity(shipMapper.toDto(expected));
        assertShipAllPropertiesEquals(expected, actual);
    }
}
