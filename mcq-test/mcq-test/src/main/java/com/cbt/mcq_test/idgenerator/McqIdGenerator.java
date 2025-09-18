package com.cbt.mcq_test.idgenerator;

import java.util.concurrent.atomic.AtomicInteger;

public class McqIdGenerator {

    private static final AtomicInteger counter = new AtomicInteger(0);

    public static String generateMcqId() {
        int number = counter.incrementAndGet();
        return String.format("MCQ%03d", number);  
    }
}
