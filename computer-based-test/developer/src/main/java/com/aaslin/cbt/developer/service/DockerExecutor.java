package com.aaslin.cbt.developer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class DockerExecutor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String runTemporaryCode(String language, String userId, String questionId,
                                   String code, String inputParams, String inputValues) throws Exception {
        if ("JAVA".equalsIgnoreCase(language)) {
            return runJava(userId, questionId, code, inputParams, inputValues);
        } else if ("PYTHON".equalsIgnoreCase(language)) {
            return runPython(userId, questionId, code, inputParams, inputValues);
        }
        throw new RuntimeException("Unsupported language: " + language);
    }

    private String runJava(String userId, String questionId, String userCode,
                           String inputParams, String inputValues) throws Exception {

        String className = extractClassName(userCode);
        String userDir = "D:/developer-submissions/" + userId + "/" + questionId;
        Files.createDirectories(Paths.get(userDir));

        File javaFile = new File(userDir + "/" + className + ".java");

        
        try (FileWriter fw = new FileWriter(javaFile)) {
            fw.write(userCode);
        }

        
        JsonNode paramsNode = objectMapper.readTree(inputParams).get("params");
        JsonNode valuesNode = objectMapper.readTree(inputValues);

        StringBuilder feedInput = new StringBuilder();
        for (JsonNode param : paramsNode) {
            JsonNode val = valuesNode.get(param.asText());
            if (val != null) {
                if (val.isArray()) {
                    for (JsonNode element : val) {
                        feedInput.append(element.asText()).append(" ");
                    }
                    feedInput.setLength(feedInput.length() - 1);
                } else {
                    feedInput.append(val.asText());
                }
                feedInput.append("\n");
            }
        }

        File inputFile = new File(userDir + "/input.txt");
        try (FileWriter fw = new FileWriter(inputFile)) {
            fw.write(feedInput.toString());
        }

        // Compile & run with redirected input
        String command = "javac " + javaFile.getName() + " && java " + className + " < input.txt";
        return executeDocker("openjdk:17", userDir, command);
    }

    private String runPython(String userId, String questionId, String userCode,
                             String inputParams, String inputValues) throws Exception {

        String pythonDir = "D:/developer-submissions/" + userId + "/" + questionId;
        Files.createDirectories(Paths.get(pythonDir));

        File file = new File(pythonDir + "/Solution.py");

        // User code written as-is
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(userCode);
        }

        // Pass input via input.txt (stdin)
        File inputFile = new File(pythonDir + "/input.txt");
        try (FileWriter fw = new FileWriter(inputFile)) {
            fw.write(inputValues);
        }

        return executeDocker("python:3.10", pythonDir, "python " + file.getName() + " < input.txt");
    }

    private String executeDocker(String image, String dir, String command) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
                "docker", "run", "--rm",
                "-v", dir + ":/app",
                "-w", "/app",
                image,
                "sh", "-c", command
        );

        Process process = pb.start();
        String stdout = new String(process.getInputStream().readAllBytes());
        String stderr = new String(process.getErrorStream().readAllBytes());
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("Compilation/Execution Error: " + stderr);
        }
        return stdout.trim();
    }

    private String extractClassName(String code) {
        var m = java.util.regex.Pattern.compile("public\\s+class\\s+(\\w+)").matcher(code);
        if (m.find()) return m.group(1);
        throw new RuntimeException("No public class found in code");
    }
}
