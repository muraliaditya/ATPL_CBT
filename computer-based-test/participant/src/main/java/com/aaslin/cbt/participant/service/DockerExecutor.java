package com.aaslin.cbt.participant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
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


    public String runAndSaveCode(String participantId, String questionId, String language, String code, String savePath) throws Exception {
        File file;
        File dir = new File(savePath);
        if(!dir.exists()) dir.mkdirs();

        if(language.equalsIgnoreCase("JAVA")) {
            String className = extractClassName(code);
            file = new File(dir, participantId + "_" + questionId + ".java");
            try(FileWriter fw = new FileWriter(file)) {
                fw.write(code);
            }
        } else if(language.equalsIgnoreCase("PYTHON")) {
            file = new File(dir, participantId + "_" + questionId + ".py");
            try(FileWriter fw = new FileWriter(file)) {
                fw.write(code);
            }
        } else {
            throw new RuntimeException("Unsupported language: " + language);
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
        String className = extractClassName(code);
        String fileName = className + ".java";
        File file;

        if (saveFile) {
            File dir = new File(savePath);
            if (!dir.exists()) dir.mkdirs();
            file = new File(dir, fileName);
        } else {
            file = File.createTempFile(className, ".java");
        }

        StringBuilder javaWrapper = new StringBuilder();
        javaWrapper.append("import java.util.*;\n")
                   .append("import com.fasterxml.jackson.databind.*;\n")
                   .append("public class ").append(className).append(" {\n")
                   .append("  public static void main(String[] args) throws Exception {\n")
                   .append("    ObjectMapper mapper = new ObjectMapper();\n")
                   .append("    Map<String,Object> input = mapper.readValue(\"")
                   .append(inputJson.replace("\"", "\\\""))
                   .append("\", Map.class);\n")
                   .append("    ").append(code).append("\n")
                   .append("  }\n")
                   .append("}\n");

        try (FileWriter fw = new FileWriter(file)) {
            fw.write(javaWrapper.toString());
        }

        String command = "javac " + file.getName() + " && java " + className;
        String output = executeDocker("openjdk:17", file.getParent(), command);

        if (!saveFile) file.delete();
        return output.trim();
    }

    private String runPythonInDocker(String code, String inputJson, boolean saveFile, String savePath) throws Exception {
        File file;
        if (saveFile) {
            File dir = new File(savePath);
            if (!dir.exists()) dir.mkdirs();
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
                     .append("        if printed.lower() in ['true','false']:\n")
                     .append("            output = printed.lower() == 'true'\n")
                     .append("        else:\n")
                     .append("            output = printed\n")
                     .append("except Exception as e:\n")
                     .append("    sys.stdout = _stdout\n")
                     .append("    print(json.dumps({\"error\": str(e)}))\n")
                     .append("    sys.exit(1)\n")
                     .append("sys.stdout = _stdout\n")
                     .append("print(json.dumps({\"output\": output}))\n");
        
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(pythonWrapper.toString());
        }

        String command = "python " + file.getName();
        String output = executeDocker("python:3.10", file.getParent(), command);

        if (!saveFile) file.delete();
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
        if (exitCode != 0) throw new RuntimeException("Compilation/Execution Error: " + stderr);

        return stdout.trim();
    }

    private String extractClassName(String code) {
        Matcher m = Pattern.compile("public\\s+class\\s+(\\w+)").matcher(code);
        if (m.find()) return m.group(1);
        throw new RuntimeException("No class name found in code");
    }

    private String indentCode(String code) {
        StringBuilder indented = new StringBuilder();
        for (String line : code.split("\n")) {
            if (!line.trim().isEmpty()) {
                indented.append("    ").append(line).append("\n");
            } else {
                indented.append("\n");
            }
        }
        return indented.toString();
    }
}