package com.falaisia.ship.eventreporting2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BunkerReceivedNoteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BunkerReceivedNote getBunkerReceivedNoteSample1() {
        return new BunkerReceivedNote().id(1L).documentDisplayNumber("documentDisplayNumber1");
    }

    public static BunkerReceivedNote getBunkerReceivedNoteSample2() {
        return new BunkerReceivedNote().id(2L).documentDisplayNumber("documentDisplayNumber2");
    }

    public static BunkerReceivedNote getBunkerReceivedNoteRandomSampleGenerator() {
        return new BunkerReceivedNote().id(longCount.incrementAndGet()).documentDisplayNumber(UUID.randomUUID().toString());
    }
}
