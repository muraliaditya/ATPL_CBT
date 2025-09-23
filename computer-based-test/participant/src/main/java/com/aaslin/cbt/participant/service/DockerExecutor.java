package com.aaslin.cbt.participant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("ParticipantDockerExecutor")
public class DockerExecutor {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String SAVE_BASE_PATH = "D:\\codes";

    public String runTemporaryCode(String language, String code, String inputJson) throws Exception {
        if(language.equalsIgnoreCase("JAVA")) {
            return runJavaInDocker(code, inputJson, false, null);
        } else if(language.equalsIgnoreCase("PYTHON")) {
            return runPythonInDocker(code, inputJson, false, null);
        }
        throw new RuntimeException("Unsupported language: " + language);
    }

    public String runAndSaveCode(String participantId, String questionId, String language, String code) throws Exception {
        String savePath = SAVE_BASE_PATH + File.separator + participantId + File.separator + questionId;
        File dir = new File(savePath);
        if(!dir.exists()) dir.mkdirs();

        String fileName;
        if(language.equalsIgnoreCase("JAVA")) {
            String className = extractClassName(code);
            fileName = participantId + "_" + questionId + ".java";
        } else if(language.equalsIgnoreCase("PYTHON")) {
            fileName = participantId + "_" + questionId + ".py";
        } else {
            throw new RuntimeException("Unsupported language: " + language);
        }

        File file = new File(dir, fileName);
        try(FileWriter fw = new FileWriter(file)) {
            fw.write(code);
        }
        return file.getAbsolutePath();
    }

    public String executeUserCode(String language, String code, String inputJson) throws Exception {
        if(language.equalsIgnoreCase("JAVA")) {
            return runJavaInDocker(code, inputJson, false, null);
        } else if(language.equalsIgnoreCase("PYTHON")) {
            return runPythonInDocker(code, inputJson, false, null);
        }
        throw new RuntimeException("Unsupported language: " + language);
    }


    private String runJavaInDocker(String code, String inputJson, boolean saveFile, String savePath) throws Exception {

        Map<String, Object> inputMap = objectMapper.readValue(inputJson, Map.class);
        List<String> argsList = new ArrayList<>();
        for(Object value : inputMap.values()) {
            argsList.add(String.valueOf(value));
        }
        String stdin = String.join("\n", argsList);

        String className;
        Matcher m = Pattern.compile("public\\s+class\\s+(\\w+)").matcher(code);
        if(m.find()) {
            className = m.group(1);
        } else {
            className = "Solution";
        }

        String fileName = className + ".java";
        File file;
        if(saveFile && savePath != null) {
            File dir = new File(savePath);
            if(!dir.exists()) dir.mkdirs();
            file = new File(dir, fileName);
        } else {
            file = new File(fileName);
        }

        StringBuilder javaWrapper = new StringBuilder();
        if(!code.contains("public class " + className)) {
            javaWrapper.append("public class ").append(className).append(" {\n")
                    .append(code).append("\n")
                    .append("}");
        } else {
            javaWrapper.append(code);
        }

        try(FileWriter fw = new FileWriter(file)) {
            fw.write(javaWrapper.toString());
        }

        String command = "javac " + file.getName() + " && echo \"" 
                + stdin.replace("\"", "\\\"") + "\" | java " + className;

        // Execute in Docker
        String parentPath = file.getAbsoluteFile().getParent();
        String output = executeDocker("openjdk:17", parentPath, command);

        if(!saveFile) file.delete();
        return output.trim();
    }

    private String runPythonInDocker(String code, String inputJson, boolean saveFile, String savePath) throws Exception {
        File file;
        if(saveFile && savePath != null) {
            File dir = new File(savePath);
            if(!dir.exists()) dir.mkdirs();
            file = new File(dir, "solution_" + System.currentTimeMillis() + ".py");
        } else {
            file = File.createTempFile("Solution", ".py");
        }

        StringBuilder pythonWrapper = new StringBuilder();
        pythonWrapper.append("import json, sys, io\n")
                .append("data = json.loads('").append(inputJson.replace("'", "\\'")).append("')\n")
                .append("globals().update(data)\n")
                .append("output = None\n")
                .append("_stdout = sys.stdout\n")
                .append("sys.stdout = io.StringIO()\n")
                .append("try:\n")
                .append(indentCode(code))
                .append("    printed = sys.stdout.getvalue().strip()\n")
                .append("    if output is None and printed:\n")
                .append("        output = printed\n")
                .append("except Exception as e:\n")
                .append("    sys.stdout = _stdout\n")
                .append("    print(json.dumps({\"error\": str(e)}))\n")
                .append("    sys.exit(1)\n")
                .append("sys.stdout = _stdout\n")
                .append("print(output)\n");

        try(FileWriter fw = new FileWriter(file)) {
            fw.write(pythonWrapper.toString());
        }

        String command = "python " + file.getName();
        String parentPath = file.getAbsoluteFile().getParent();
        String output = executeDocker("python:3.10", parentPath, command);

        if(!saveFile) file.delete();
        return output.trim();
    }


    private String executeDocker(String image, String dir, String command) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
                "docker", "run", "--rm",
                "-v", dir + ":/app",
                "-w", "/app",
                image,
                "sh", "-c",
                command
        );

        Process process = pb.start();
        String stdout = new String(process.getInputStream().readAllBytes());
        String stderr = new String(process.getErrorStream().readAllBytes());

        int exitCode = process.waitFor();
        if(exitCode != 0) throw new RuntimeException("Compilation/Execution Error: " + stderr);

        return stdout.trim();
    }


    private String extractClassName(String code) {
        Matcher m = Pattern.compile("public\\s+class\\s+(\\w+)").matcher(code);
        if(m.find()) return m.group(1);
        return "Solution";
    }

    private String indentCode(String code) {
        StringBuilder indented = new StringBuilder();
        for(String line : code.split("\n")) {
            if(!line.trim().isEmpty()) {
                indented.append("    ").append(line).append("\n");
            } else {
                indented.append("\n");
            }
        }
        return indented.toString();
    }
}