package com.aaslin.cbt.super_admin.idgenerator;

public class ContestIdGenerator {

    public static String generateContestId(int lastId) {
        int nextId = lastId + 1;
        return String.format("CON%03d", nextId);
    }
}
