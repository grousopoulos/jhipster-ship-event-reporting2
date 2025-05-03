package com.falaisia.ship.eventreporting2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FlagTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Flag getFlagSample1() {
        return new Flag().id(1L).code("code1").name("name1");
    }

    public static Flag getFlagSample2() {
        return new Flag().id(2L).code("code2").name("name2");
    }

    public static Flag getFlagRandomSampleGenerator() {
        return new Flag().id(longCount.incrementAndGet()).code(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
