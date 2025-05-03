package com.falaisia.ship.eventreporting2.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BunkerReceivedNoteLineTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BunkerReceivedNoteLine getBunkerReceivedNoteLineSample1() {
        return new BunkerReceivedNoteLine().id(1L);
    }

    public static BunkerReceivedNoteLine getBunkerReceivedNoteLineSample2() {
        return new BunkerReceivedNoteLine().id(2L);
    }

    public static BunkerReceivedNoteLine getBunkerReceivedNoteLineRandomSampleGenerator() {
        return new BunkerReceivedNoteLine().id(longCount.incrementAndGet());
    }
}
