package com.aaslin.cbt.participant.service;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class DockerExecutor {

    public String runCode(String language, String code, String input) throws Exception {
        if (language.equalsIgnoreCase("JAVA")) {
            return runInDocker("openjdk:17", "Solution.java", code, input);
        }
        throw new RuntimeException("Unsupported language: " + language);
    }

    private String runInDocker(String image, String fileName, String code, String input) throws Exception {
    	File dir=new File("D:\\codes");
        File file = new File(dir,"Solution.java");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(code);
        }

        // Run docker command
        ProcessBuilder pb = new ProcessBuilder(
                "docker", "run", "--rm",
                "-v", file.getParent() + ":/app",
                "-w", "/app",
                image,
                "sh", "-c",
                "javac " + fileName + " && echo \"" + input + "\" | java Solution"
        );

        Process process = pb.start();
        String output = new String(process.getInputStream().readAllBytes());
        String error = new String(process.getErrorStream().readAllBytes());

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Compilation/Execution Error: " + error);
        }

        return output.trim();
    }
}
