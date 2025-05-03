package com.falaisia.ship.eventreporting2.service.mapper;

import static com.falaisia.ship.eventreporting2.domain.PortAsserts.*;
import static com.falaisia.ship.eventreporting2.domain.PortTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PortMapperTest {

    private PortMapper portMapper;

    @BeforeEach
    void setUp() {
        portMapper = new PortMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPortSample1();
        var actual = portMapper.toEntity(portMapper.toDto(expected));
        assertPortAllPropertiesEquals(expected, actual);
    }
}
