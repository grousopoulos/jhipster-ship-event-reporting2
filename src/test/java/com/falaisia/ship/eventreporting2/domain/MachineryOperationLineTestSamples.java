package com.falaisia.ship.eventreporting2.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MachineryOperationLineTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MachineryOperationLine getMachineryOperationLineSample1() {
        return new MachineryOperationLine().id(1L);
    }

    public static MachineryOperationLine getMachineryOperationLineSample2() {
        return new MachineryOperationLine().id(2L);
    }

    public static MachineryOperationLine getMachineryOperationLineRandomSampleGenerator() {
        return new MachineryOperationLine().id(longCount.incrementAndGet());
    }
}
