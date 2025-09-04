package com.aaslin.code_runner;

import lombok.Data;

@Data
public class RunRequest {
    private Language language;
    private String code;
    private String stdin;
    private int timeLimitSec = 2; // default

    public enum Language {
        PYTHON, CPP, JAVA, NODE, C;

        public static Language fromString(String lang) {
            return switch (lang.toLowerCase()) {
                case "python" -> PYTHON;
                case "cpp" -> CPP;
                case "java" -> JAVA;
                case "node" -> NODE;
                case "c" -> C;
                default -> throw new IllegalArgumentException("Unsupported language: " + lang);
            };
        }
    }
}
