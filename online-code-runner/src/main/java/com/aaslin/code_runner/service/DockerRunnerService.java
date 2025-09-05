package com.aaslin.code_runner.service;

import com.aaslin.code_runner.RunRequest;
import com.aaslin.code_runner.RunResponse;
import com.aaslin.code_runner.dto.SubmissionRequest;
import com.aaslin.code_runner.dto.TestCase;
import com.aaslin.code_runner.dto.TestCaseResult;
import com.aaslin.code_runner.dto.TestCaseSet;
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
            Path work = Files.createTempDirectory("code-");

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

            // docker run command
            List<String> cmd = new ArrayList<>();
            cmd.addAll(List.of(
                    "docker", "run",
                    "--rm",
                    "--network=none",
                    "-v", work.toAbsolutePath() + ":/app:rw",
                    "-w", "/app",
                    config.image()
            ));
            cmd.addAll(config.runCmd());

            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            Process p = pb.start();

            String output = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            int exit = p.waitFor();

            String status = exit == 0 ? "OK" : "RUNTIME_ERROR";

            try {
                Files.walk(work).sorted(Comparator.reverseOrder())
                        .forEach(path -> { try { Files.delete(path);} catch (Exception ignored) {} });
            } catch (Exception ignored) {}

            return new RunResponse(output.trim(), "", exit, status);

        } catch (Exception e) {
            return new RunResponse("", e.getMessage(), 1, "INTERNAL_ERROR");
        }
    }
//    public TestCaseSet runTestCases(RunRequest req) {
//        List<TestCaseResult> results = new ArrayList<>();
//        int passedCount = 0;
//
//        for (int i = 0; i < req.getTestCases().size(); i++) {
//            TestCase tc = req.getTestCases().get(i);
//
//            RunRequest tcReq = new RunRequest();
//            tcReq.setLanguage(req.getLanguage());
//            tcReq.setCode(req.getCode());
//            tcReq.setStdin(tc.getInput());
//
//            RunResponse res = this.run(tcReq);
//
//            boolean passed = res.getOutput().trim().equals(tc.getExpectedOutput().trim());
//            if (passed) passedCount++;
//
//            results.add(new TestCaseResult(i, res.getOutput(), tc.getExpectedOutput(), passed));
//        }
//
//        return new TestCaseSet(results, passedCount);
//    }
    
    public TestCaseSet runTestCases(RunRequest req) {
        List<TestCaseResult> results = new ArrayList<>();
        int score = 0;

        for (int i = 0; i < req.getTestCases().size(); i++) {
            var tc = req.getTestCases().get(i);

            RunRequest tcReq = new RunRequest();
            tcReq.setLanguage(req.getLanguage());
            tcReq.setCode(req.getCode());
            tcReq.setStdin(tc.getInput());

            RunResponse res = this.run(tcReq);

            boolean passed = res.getOutput().trim().equals(tc.getExpectedOutput().trim());
            int awarded = passed ? tc.getMarks() : 0;

            if (passed) {
                score += awarded;
            }

            results.add(new TestCaseResult(i, res.getOutput(), tc.getExpectedOutput(), passed, awarded));
        }

        return new TestCaseSet(results, score);
    }

    public TestCaseSet runTestCases(SubmissionRequest req) {
        RunRequest runReq = new RunRequest();
        runReq.setLanguage(RunRequest.Language.fromString(req.getLanguage()));
        runReq.setCode(req.getCode());
        runReq.setStdin("");
        runReq.setTestCases(req.getTestCases());

        return runTestCases(runReq);
    }


    private ImageAndCommand buildConfig(RunRequest req) {
        return switch (req.getLanguage()) {
            case PYTHON -> new ImageAndCommand("code-runner:python",
                    List.of("python3", "main.py < stdin.txt"));
            case CPP -> new ImageAndCommand("code-runner:cpp",
                    List.of("sh", "-c", "g++ main.cpp -o main && ./main < stdin.txt"));
            case JAVA -> new ImageAndCommand("code-runner:java",
                    List.of("sh", "-c", "javac Main.java && java Main < stdin.txt"));
            case NODE -> new ImageAndCommand("code-runner:node",
                    List.of("node", "main.js < stdin.txt"));
            case C -> new ImageAndCommand("code-runner:c",
                    List.of("sh", "-c", "gcc main.c -o main && ./main < stdin.txt"));
        };
    }
}