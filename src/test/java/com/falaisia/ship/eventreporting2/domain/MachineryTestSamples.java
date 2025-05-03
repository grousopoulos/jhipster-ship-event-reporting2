package com.falaisia.ship.eventreporting2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MachineryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Machinery getMachinerySample1() {
        return new Machinery().id(1L).name("name1");
    }

    public static Machinery getMachinerySample2() {
        return new Machinery().id(2L).name("name2");
    }

    public static Machinery getMachineryRandomSampleGenerator() {
        return new Machinery().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
