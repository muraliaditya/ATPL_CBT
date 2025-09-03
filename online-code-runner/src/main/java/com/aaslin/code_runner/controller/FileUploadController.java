package com.aaslin.code_runner.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/api/code")
public class FileUploadController {

    @PostMapping("/upload")
    public String uploadCode(
            @RequestParam("language") String language,
            @RequestParam("file") MultipartFile file) throws IOException, InterruptedException {

        File folder = new File(System.getProperty("java.io.tmpdir"), "code-" + System.currentTimeMillis());
        folder.mkdirs();

        String filename = file.getOriginalFilename();
        File codeFile = new File(folder, filename);
        file.transferTo(codeFile);

        if (language.equalsIgnoreCase("java")) {
            String className = filename.replace(".java", "");
            return runDocker(language, folder.getAbsolutePath(), filename, className);
        } else {
            return runDocker(language, folder.getAbsolutePath(), filename, null);
        }
    }

    private String runDocker(String language, String folder, String filename, String className)
            throws IOException, InterruptedException {

        String command;
        switch (language.toLowerCase()) {
            case "python":
                command = String.format("docker run --rm -v \"%s:/app\" code-runner:python python /app/%s",
                        folder, filename);
                break;
            case "cpp":
                command = String.format("docker run --rm -v \"%s:/app\" code-runner:cpp sh -c \"g++ /app/%s -o /app/a.out && /app/a.out\"",
                        folder, filename);
                break;
            case "java":
                command = String.format("docker run --rm -v \"%s:/app\" code-runner:java sh -c \"javac /app/%s && java -cp /app %s\"",
                        folder, filename, className);
                break;
            case "node":
                command = String.format("docker run --rm -v \"%s:/app\" code-runner:node node /app/%s",
                        folder, filename);
                break;
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }

        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        builder.redirectErrorStream(true);
        Process process = builder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        int exitCode = process.waitFor();
        return "ExitCode: " + exitCode + "\nOutput:\n" + output.toString();
    }
}