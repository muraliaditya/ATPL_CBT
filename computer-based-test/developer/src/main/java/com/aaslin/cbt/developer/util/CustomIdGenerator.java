package com.aaslin.cbt.developer.util;

public class CustomIdGenerator {

    public static String generateNextId(String prefix, String lastId) {
        if (lastId == null || lastId.isEmpty()) {
            return prefix + "001"; // First ID
        }

        // Extract numeric part
        String numberPart = lastId.replaceAll("\\D", "");
        int num = Integer.parseInt(numberPart);

        // Always increment sequentially
        return String.format(prefix + "%03d", num + 1);
    }
}
