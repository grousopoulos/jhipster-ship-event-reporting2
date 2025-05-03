package com.falaisia.ship.eventreporting2.service.mapper;

import static com.falaisia.ship.eventreporting2.domain.MachineryAsserts.*;
import static com.falaisia.ship.eventreporting2.domain.MachineryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MachineryMapperTest {

    private MachineryMapper machineryMapper;

    @BeforeEach
    void setUp() {
        machineryMapper = new MachineryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMachinerySample1();
        var actual = machineryMapper.toEntity(machineryMapper.toDto(expected));
        assertMachineryAllPropertiesEquals(expected, actual);
    }
}
