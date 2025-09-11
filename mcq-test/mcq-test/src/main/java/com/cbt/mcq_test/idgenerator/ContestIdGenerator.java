package com.cbt.mcq_test.idgenerator;

import java.util.concurrent.atomic.AtomicInteger;

public class ContestIdGenerator {

    private static final AtomicInteger counter = new AtomicInteger(0);

    public static String generateContestId() {
        int number = counter.incrementAndGet();
        return String.format("CON%03d", number);  
    }
}
