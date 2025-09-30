package com.aaslin.cbt.super_admin.util;

public class CustomIdGenerator {
    public static String generateNextId(String prefix, String lastId) {
        if (lastId == null || lastId.isEmpty()) {
            return prefix + "001";
        }
        String numberPart = lastId.replaceAll("\\D", ""); 
        int num = Integer.parseInt(numberPart);
        return String.format(prefix + "%03d", num + 1);

    }

}