package com.falaisia.ship.eventreporting2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EventReportTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static EventReport getEventReportSample1() {
        return new EventReport()
            .id(1L)
            .documentDisplayNumber("documentDisplayNumber1")
            .leg(1)
            .coordinatesLatitude("coordinatesLatitude1")
            .coordinatesLongitude("coordinatesLongitude1")
            .shipsHeading("shipsHeading1")
            .beaufortNo(1)
            .weatherCondition("weatherCondition1");
    }

    public static EventReport getEventReportSample2() {
        return new EventReport()
            .id(2L)
            .documentDisplayNumber("documentDisplayNumber2")
            .leg(2)
            .coordinatesLatitude("coordinatesLatitude2")
            .coordinatesLongitude("coordinatesLongitude2")
            .shipsHeading("shipsHeading2")
            .beaufortNo(2)
            .weatherCondition("weatherCondition2");
    }

    public static EventReport getEventReportRandomSampleGenerator() {
        return new EventReport()
            .id(longCount.incrementAndGet())
            .documentDisplayNumber(UUID.randomUUID().toString())
            .leg(intCount.incrementAndGet())
            .coordinatesLatitude(UUID.randomUUID().toString())
            .coordinatesLongitude(UUID.randomUUID().toString())
            .shipsHeading(UUID.randomUUID().toString())
            .beaufortNo(intCount.incrementAndGet())
            .weatherCondition(UUID.randomUUID().toString());
    }
}
