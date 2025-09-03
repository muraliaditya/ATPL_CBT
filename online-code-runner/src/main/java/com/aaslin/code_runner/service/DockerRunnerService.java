package com.aaslin.code_runner.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aaslin.code_runner.RunRequest;
import com.aaslin.code_runner.RunResponse;

@Service
public class DockerRunnerService {

	private record ImageAndCommand(String image,List<String> runCmd) {}
    public RunResponse run(RunRequest req) {
        try {
            Path work = Files.createTempDirectory("code-");
            String fileName = switch (req.getLanguage()) {
                case PYTHON -> "main.py";
                case CPP -> "main.cpp";
                case JAVA -> "Main.java";
                case NODE -> "main.js";
            };
            Path source = work.resolve(fileName);
            Files.writeString(source, req.getCode(), StandardCharsets.UTF_8);
            if (req.getStdin() != null) {
                Files.writeString(work.resolve("stdin.txt"), req.getStdin(), StandardCharsets.UTF_8);
            }

            ImageAndCommand config = buildConfig(req);

            List<String> cmd = new ArrayList<>();
            cmd.addAll(List.of("docker", "run",
                    "--rm",
                    "--network=none",
                    "--cpus=0.5",
                    "-m", "256m",
                    "--pids-limit", "128",
                    "--read-only",
                    "-v", work.toAbsolutePath() + ":/app:rw",
                    "-w", "/app"));
            cmd.addAll(List.of("--tmpfs", "/tmp:rw,noexec,nosuid,size=64m"));
            cmd.add(config.image());

            String stdinPart = (req.getStdin() != null && !req.getStdin().isEmpty()) ? " < stdin.txt" : "";
            String joined = String.join(" ", config.runCmd()) + stdinPart;
            String shell = String.format("sh -lc 'timeout %ds %s'", Math.max(1, req.getTimeLimitSec()), joined);
            cmd.addAll(List.of("sh", "-lc", shell));

            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(false);
            Process p = pb.start();

            String stdout = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            String stderr = new String(p.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
            int exit = p.waitFor();

            String status;
            if (exit == 124 || stderr.contains("Timed out")) status = "TLE";
            else if (exit == 0) status = "OK";
            else status = "RUNTIME_ERROR";

            try { Files.walk(work).sorted(Comparator.reverseOrder()).forEach(path -> { try { Files.delete(path);} catch (Exception ignored) {} }); } catch (Exception ignored) {}

            return new RunResponse(stdout, stderr, exit, status);
        } catch (Exception e) {
            return new RunResponse("", e.getMessage(), 1, "INTERNAL_ERROR");
        }
    }

    private ImageAndCommand buildConfig(RunRequest req) {
        return switch (req.getLanguage()) {
            case PYTHON -> new ImageAndCommand("code-runner:python", List.of("python", "main.py"));
            case CPP -> new ImageAndCommand("code-runner:cpp", List.of("sh", "-lc", "g++ -O2 -std=c++17 main.cpp -o main && ./main"));
            case JAVA -> new ImageAndCommand("code-runner:java", List.of("sh", "-lc", "javac Main.java && java Main"));
            case NODE -> new ImageAndCommand("code-runner:node", List.of("node", "main.js"));
        };
    }
}
