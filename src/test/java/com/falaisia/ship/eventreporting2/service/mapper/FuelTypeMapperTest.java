package com.falaisia.ship.eventreporting2.service.mapper;

import static com.falaisia.ship.eventreporting2.domain.FuelTypeAsserts.*;
import static com.falaisia.ship.eventreporting2.domain.FuelTypeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuelTypeMapperTest {

    private FuelTypeMapper fuelTypeMapper;

    @BeforeEach
    void setUp() {
        fuelTypeMapper = new FuelTypeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFuelTypeSample1();
        var actual = fuelTypeMapper.toEntity(fuelTypeMapper.toDto(expected));
        assertFuelTypeAllPropertiesEquals(expected, actual);
    }
}
