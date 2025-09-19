package com.aaslin.cbt.super_admin.idgenerator;

public class McqIdGenerator {

    public static String generateMcqId(int lastId) {
        int nextId = lastId + 1;
        return String.format("MCQ%03d", nextId);
    }
}
