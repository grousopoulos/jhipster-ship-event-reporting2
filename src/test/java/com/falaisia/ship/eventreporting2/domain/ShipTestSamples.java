package com.falaisia.ship.eventreporting2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ShipTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ship getShipSample1() {
        return new Ship().id(1L).name("name1").callSign("callSign1");
    }

    public static Ship getShipSample2() {
        return new Ship().id(2L).name("name2").callSign("callSign2");
    }

    public static Ship getShipRandomSampleGenerator() {
        return new Ship().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).callSign(UUID.randomUUID().toString());
    }
}
