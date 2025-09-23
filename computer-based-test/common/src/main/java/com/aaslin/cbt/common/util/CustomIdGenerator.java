package com.aaslin.cbt.common.util;
public class CustomIdGenerator {

    public static String generateNextId(String prefix, String lastId) {
        if (lastId == null || lastId.isEmpty()) {
            return prefix + "001"; // First ID
        }

        // Extract numeric part (remove non-digits)
        String numberPart = lastId.replaceAll("\\D", "");
        int num = 0;
        try {
            num = Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            num = 0; // fallback
        }

        return String.format("%s%03d", prefix, num + 1);
    }
}

