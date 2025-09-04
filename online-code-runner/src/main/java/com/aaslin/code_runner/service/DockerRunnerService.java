package com.aaslin.code_runner.service;

import com.aaslin.code_runner.RunRequest;
import com.aaslin.code_runner.RunResponse;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class DockerRunnerService implements CodeRunnerService {

    private record ImageAndCommand(String image, List<String> runCmd) {}

    @Override
    public RunResponse run(RunRequest req) {
        try {
            // Create temporary working directory
            Path work = Files.createTempDirectory("code-");

            // Determine filename
            String fileName = switch (req.getLanguage()) {
                case PYTHON -> "main.py";
                case CPP -> "main.cpp";
                case JAVA -> "Main.java";
                case NODE -> "main.js";
                case C -> "main.c";
            };

            Path source = work.resolve(fileName);
            Files.writeString(source, req.getCode(), StandardCharsets.UTF_8);

            if (req.getStdin() != null && !req.getStdin().isEmpty()) {
                Files.writeString(work.resolve("stdin.txt"), req.getStdin(), StandardCharsets.UTF_8);
            }

            ImageAndCommand config = buildConfig(req);

            // Build docker run command
            List<String> cmd = new ArrayList<>();
            cmd.addAll(List.of(
                    "docker", "run",
                    "--rm",
                    "--network=none",
                    "-v", work.toAbsolutePath() + ":/app:rw",
                    "-w", "/app",
                    config.image()
            ));

            // Append language-specific command
            cmd.addAll(config.runCmd());

            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true); // combine stdout & stderr
            Process p = pb.start();

            String output = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            int exit = p.waitFor();

            String status = exit == 0 ? "OK" : "RUNTIME_ERROR";

            // Cleanup temp files
            try {
                Files.walk(work).sorted(Comparator.reverseOrder())
                        .forEach(path -> { try { Files.delete(path);} catch (Exception ignored) {} });
            } catch (Exception ignored) {}

            return new RunResponse(output.trim(), "", exit, status);

        } catch (Exception e) {
            return new RunResponse("", e.getMessage(), 1, "INTERNAL_ERROR");
        }
    }

    private ImageAndCommand buildConfig(RunRequest req) {
        return switch (req.getLanguage()) {
            case PYTHON -> new ImageAndCommand("code-runner:python", List.of("python3", "main.py"));
            case CPP -> new ImageAndCommand("code-runner:cpp", List.of("sh", "-c", "g++ main.cpp -o main && ./main < stdin.txt"));
            case JAVA -> new ImageAndCommand("code-runner:java", List.of("sh", "-c", "javac Main.java && java Main < stdin.txt"));
            case NODE -> new ImageAndCommand("code-runner:node", List.of("node", "main.js"));
            case C -> new ImageAndCommand("code-runner:c", List.of("sh", "-c", "gcc main.c -o main && ./main < stdin.txt"));
        };
    }
}