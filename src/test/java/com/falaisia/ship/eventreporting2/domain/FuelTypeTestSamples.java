package com.falaisia.ship.eventreporting2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FuelTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FuelType getFuelTypeSample1() {
        return new FuelType().id(1L).name("name1");
    }

    public static FuelType getFuelTypeSample2() {
        return new FuelType().id(2L).name("name2");
    }

    public static FuelType getFuelTypeRandomSampleGenerator() {
        return new FuelType().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
