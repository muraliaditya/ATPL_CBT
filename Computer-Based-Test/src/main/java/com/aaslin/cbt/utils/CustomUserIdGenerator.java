package com.aaslin.cbt.utils;

public class CustomUserIdGenerator {
    public static String generateNextUserId(String lastId) {
        if (lastId == null || lastId.isEmpty()) {
            return "UID001"; // First user
        }

        // Extract digits only
        String numberPart = lastId.replaceAll("\\D", ""); // e.g. UID007 → "007"

        int num = 0;
        try {
            num = Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            num = 0;
        }

        return String.format("UID%03d", num + 1); // always UID prefix
    }
}
