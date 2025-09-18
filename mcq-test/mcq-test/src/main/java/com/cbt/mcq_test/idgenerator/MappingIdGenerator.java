package com.cbt.mcq_test.idgenerator;

import java.util.concurrent.atomic.AtomicInteger;

public class MappingIdGenerator {

    private static final AtomicInteger counter = new AtomicInteger(0);

    public static String generateMappingId() {
        int number = counter.incrementAndGet();
        return String.format("MAP%03d", number);  
    }
}


