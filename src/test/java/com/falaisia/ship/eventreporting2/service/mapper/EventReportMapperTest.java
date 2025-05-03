package com.falaisia.ship.eventreporting2.service.mapper;

import static com.falaisia.ship.eventreporting2.domain.EventReportAsserts.*;
import static com.falaisia.ship.eventreporting2.domain.EventReportTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventReportMapperTest {

    private EventReportMapper eventReportMapper;

    @BeforeEach
    void setUp() {
        eventReportMapper = new EventReportMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEventReportSample1();
        var actual = eventReportMapper.toEntity(eventReportMapper.toDto(expected));
        assertEventReportAllPropertiesEquals(expected, actual);
    }
}
