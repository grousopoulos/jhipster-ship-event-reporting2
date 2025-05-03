package com.falaisia.ship.eventreporting2.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ConsumptionLineTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ConsumptionLine getConsumptionLineSample1() {
        return new ConsumptionLine().id(1L);
    }

    public static ConsumptionLine getConsumptionLineSample2() {
        return new ConsumptionLine().id(2L);
    }

    public static ConsumptionLine getConsumptionLineRandomSampleGenerator() {
        return new ConsumptionLine().id(longCount.incrementAndGet());
    }
}
