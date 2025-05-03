package com.falaisia.ship.eventreporting2.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FuelEuRegulationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static FuelEuRegulation getFuelEuRegulationSample1() {
        return new FuelEuRegulation().id(1L).year(1).co2Gwp(1).methaneGwp(1).nitrousGwp(1);
    }

    public static FuelEuRegulation getFuelEuRegulationSample2() {
        return new FuelEuRegulation().id(2L).year(2).co2Gwp(2).methaneGwp(2).nitrousGwp(2);
    }

    public static FuelEuRegulation getFuelEuRegulationRandomSampleGenerator() {
        return new FuelEuRegulation()
            .id(longCount.incrementAndGet())
            .year(intCount.incrementAndGet())
            .co2Gwp(intCount.incrementAndGet())
            .methaneGwp(intCount.incrementAndGet())
            .nitrousGwp(intCount.incrementAndGet());
    }
}
