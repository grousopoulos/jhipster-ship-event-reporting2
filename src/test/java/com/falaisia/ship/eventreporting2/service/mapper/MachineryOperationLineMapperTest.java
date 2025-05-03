package com.falaisia.ship.eventreporting2.service.mapper;

import static com.falaisia.ship.eventreporting2.domain.MachineryOperationLineAsserts.*;
import static com.falaisia.ship.eventreporting2.domain.MachineryOperationLineTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MachineryOperationLineMapperTest {

    private MachineryOperationLineMapper machineryOperationLineMapper;

    @BeforeEach
    void setUp() {
        machineryOperationLineMapper = new MachineryOperationLineMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMachineryOperationLineSample1();
        var actual = machineryOperationLineMapper.toEntity(machineryOperationLineMapper.toDto(expected));
        assertMachineryOperationLineAllPropertiesEquals(expected, actual);
    }
}
