package com.falaisia.ship.eventreporting2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Port getPortSample1() {
        return new Port().id(1L).name("name1").unlocode("unlocode1");
    }

    public static Port getPortSample2() {
        return new Port().id(2L).name("name2").unlocode("unlocode2");
    }

    public static Port getPortRandomSampleGenerator() {
        return new Port().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).unlocode(UUID.randomUUID().toString());
    }
}
