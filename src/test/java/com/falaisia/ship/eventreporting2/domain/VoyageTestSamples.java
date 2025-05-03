package com.falaisia.ship.eventreporting2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VoyageTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Voyage getVoyageSample1() {
        return new Voyage().id(1L).number("number1");
    }

    public static Voyage getVoyageSample2() {
        return new Voyage().id(2L).number("number2");
    }

    public static Voyage getVoyageRandomSampleGenerator() {
        return new Voyage().id(longCount.incrementAndGet()).number(UUID.randomUUID().toString());
    }
}
