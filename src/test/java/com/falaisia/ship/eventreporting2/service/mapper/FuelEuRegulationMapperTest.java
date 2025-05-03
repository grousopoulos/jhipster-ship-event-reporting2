package com.falaisia.ship.eventreporting2.service.mapper;

import static com.falaisia.ship.eventreporting2.domain.FuelEuRegulationAsserts.*;
import static com.falaisia.ship.eventreporting2.domain.FuelEuRegulationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuelEuRegulationMapperTest {

    private FuelEuRegulationMapper fuelEuRegulationMapper;

    @BeforeEach
    void setUp() {
        fuelEuRegulationMapper = new FuelEuRegulationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFuelEuRegulationSample1();
        var actual = fuelEuRegulationMapper.toEntity(fuelEuRegulationMapper.toDto(expected));
        assertFuelEuRegulationAllPropertiesEquals(expected, actual);
    }
}
