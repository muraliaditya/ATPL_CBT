package com.aaslin.code_runner.controller;

import org.springframework.web.bind.annotation.*;
import com.aaslin.code_runner.dto.CodeRequest;

import java.io.*;

@RestController
@RequestMapping("/api/code")
public class CodeController {

    @PostMapping("/run")
    public String runCode(@RequestBody CodeRequest request) throws IOException, InterruptedException {
        String language = request.language;
        String code = request.code;

        File folder = new File(System.getProperty("java.io.tmpdir"), "code-" + System.currentTimeMillis());
        folder.mkdirs();

        String filename;
        if (language.equalsIgnoreCase("java")) {
            filename = "Main.java";
            code = code.replaceFirst("public class [A-Za-z0-9_]+", "public class Main");
        } else if (language.equalsIgnoreCase("python")) {
            filename = "script.py";
        } else if (language.equalsIgnoreCase("cpp")) {
            filename = "program.cpp";
        } else if (language.equalsIgnoreCase("node")) {
            filename = "app.js";
        } else {
            throw new IllegalArgumentException("Unsupported language: " + language);
        }

        File codeFile = new File(folder, filename);
        try (FileWriter writer = new FileWriter(codeFile)) {
            writer.write(code);
        }

        String dockerPath = folder.getAbsolutePath().replace("\\", "/");
        dockerPath = "/" + Character.toLowerCase(dockerPath.charAt(0)) + dockerPath.substring(2);

        String baseCommand;
        switch (language.toLowerCase()) {
            case "python":
                baseCommand = String.format(
                        "docker run --rm -v \"%s:/app\" code-runner:python python /app/%s",
                        dockerPath, filename);
                break;
            case "cpp":
                baseCommand = String.format(
                        "docker run --rm -v \"%s:/app\" code-runner:cpp sh -c \"g++ /app/%s -o /app/a.out && /app/a.out < /app/input.txt\"",
                        dockerPath, filename);
                break;
            case "java":
                baseCommand = String.format(
                        "docker run --rm -v \"%s:/app\" code-runner:java sh -c \"javac /app/%s && java -cp /app Main < /app/input.txt\"",
                        dockerPath, filename);
                break;
            case "node":
                baseCommand = String.format(
                        "docker run --rm -v \"%s:/app\" code-runner:node node /app/%s < /app/input.txt",
                        dockerPath, filename);
                break;
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }

        StringBuilder allResults = new StringBuilder();
        int testCaseNumber = 1;

        for (CodeRequest.TestCase testCase : request.getTestCases()) {
            File inputFile = new File(folder, "input.txt");
            try (FileWriter writer = new FileWriter(inputFile)) {
                writer.write(testCase.getInput() != null ? testCase.getInput() : "");
            }
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", baseCommand);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            process.waitFor();

            String actualOutput = output.toString().trim();
            String expectedOutput = testCase.getExpectedOutput() != null ? testCase.getExpectedOutput().trim() : "";

            allResults.append("Test Case #").append(testCaseNumber);

            if (testCase.isPublic()) {
                allResults.append(" (Public):\n");
                allResults.append("Input:\n").append(testCase.getInput()).append("\n");
                allResults.append("Expected:\n").append(expectedOutput).append("\n");
                allResults.append("Got:\n").append(actualOutput).append("\n");
            } else {
                allResults.append(" (Private):\n");
            }

            if (expectedOutput.equals(actualOutput)) {
                allResults.append("✅ Passed\n\n");
            } else {
                allResults.append("❌ Failed\n\n");
            }

            testCaseNumber++;
        }

        return allResults.toString();
    }
}