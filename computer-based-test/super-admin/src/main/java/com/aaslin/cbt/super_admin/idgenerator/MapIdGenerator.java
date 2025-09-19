package com.aaslin.cbt.super_admin.idgenerator;

public class MapIdGenerator {
    public static String generateMapId(int lastId) {
        int nextId = lastId + 1;
        return String.format("MAP%03d", nextId);
    }
}