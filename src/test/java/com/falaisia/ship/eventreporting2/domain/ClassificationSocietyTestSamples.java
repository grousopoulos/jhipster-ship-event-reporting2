package com.falaisia.ship.eventreporting2.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClassificationSocietyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ClassificationSociety getClassificationSocietySample1() {
        return new ClassificationSociety().id(1L).code("code1").name("name1");
    }

    public static ClassificationSociety getClassificationSocietySample2() {
        return new ClassificationSociety().id(2L).code("code2").name("name2");
    }

    public static ClassificationSociety getClassificationSocietyRandomSampleGenerator() {
        return new ClassificationSociety()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString());
    }
}
