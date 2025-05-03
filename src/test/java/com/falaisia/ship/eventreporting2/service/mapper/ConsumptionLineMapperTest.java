package com.falaisia.ship.eventreporting2.service.mapper;

import static com.falaisia.ship.eventreporting2.domain.ConsumptionLineAsserts.*;
import static com.falaisia.ship.eventreporting2.domain.ConsumptionLineTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsumptionLineMapperTest {

    private ConsumptionLineMapper consumptionLineMapper;

    @BeforeEach
    void setUp() {
        consumptionLineMapper = new ConsumptionLineMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getConsumptionLineSample1();
        var actual = consumptionLineMapper.toEntity(consumptionLineMapper.toDto(expected));
        assertConsumptionLineAllPropertiesEquals(expected, actual);
    }
}
