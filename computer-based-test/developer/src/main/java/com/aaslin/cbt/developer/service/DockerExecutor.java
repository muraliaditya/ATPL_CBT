package com.aaslin.cbt.developer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        boolean hasMain = userCode.contains("public static void main");
        boolean usesScanner = userCode.contains("Scanner");

        if (hasMain && usesScanner) {
            // Build input.txt dynamically
            JsonNode paramsNode = objectMapper.readTree(inputParams).get("params");
            JsonNode valuesNode = objectMapper.readTree(inputValues);

            StringBuilder feedInput = new StringBuilder();
            for (Iterator<JsonNode> it = paramsNode.iterator(); it.hasNext(); ) {
                String param = it.next().asText();
                JsonNode val = valuesNode.get(param);

                // Array or List → space separated
                if (val.isArray()) {
                    for (JsonNode element : val) {
                        feedInput.append(element.asText()).append(" ");
                    }
                    feedInput.setLength(feedInput.length() - 1); // remove trailing space
                } else {
                    feedInput.append(val.asText());
                }
                feedInput.append("\n"); // each param on new line
            }

            // Write input.txt
            File inputFile = new File(userDir + "/input.txt");
            try (FileWriter fw = new FileWriter(inputFile)) {
                fw.write(feedInput.toString());
            }

            // Write user code as is
            try (FileWriter fw = new FileWriter(javaFile)) {
                fw.write(userCode);
            }

            // Compile & run with input redirection
            String command = "javac " + javaFile.getName() + " && java " + className + " < input.txt";
            return executeDocker("openjdk:17", userDir, command);

        } else {
            // Method-only → wrap user code
            JsonNode paramsNode = objectMapper.readTree(inputParams).get("params");
            JsonNode valuesNode = objectMapper.readTree(inputValues);

            StringBuilder assignments = new StringBuilder();
            StringBuilder callArgs = new StringBuilder();
            for (Iterator<JsonNode> it = paramsNode.iterator(); it.hasNext(); ) {
                String param = it.next().asText();
                JsonNode val = valuesNode.get(param);

                if (val.isTextual()) {
                    assignments.append("String ").append(param)
                            .append(" = (String) input.get(\"").append(param).append("\");\n");
                } else if (val.isInt() || val.isLong()) {
                    assignments.append("int ").append(param)
                            .append(" = (Integer) input.get(\"").append(param).append("\");\n");
                } else if (val.isArray()) {
                    assignments.append("int[] ").append(param).append(" = mapper.convertValue(input.get(\"")
                            .append(param).append("\"), int[].class);\n");
                } else {
                    assignments.append("Object ").append(param)
                            .append(" = input.get(\"").append(param).append("\");\n");
                }

                if (callArgs.length() > 0) callArgs.append(", ");
                callArgs.append(param);
            }

            String wrapper = ""
                    + "import java.util.*;\n"
                    + "import com.fasterxml.jackson.databind.*;\n"
                    + "public class " + className + "Wrapper {\n"
                    + "  public static void main(String[] args) throws Exception {\n"
                    + "    ObjectMapper mapper = new ObjectMapper();\n"
                    + "    Map<String,Object> input = mapper.readValue(\"" + inputValues.replace("\"", "\\\"") + "\", Map.class);\n"
                    + assignments.toString()
                    + "    Object output = null;\n"
                    + userCode + "\n"
                    + "    " + className + " sol = new " + className + "();\n"
                    + "    output = sol." + detectMethod(userCode) + "(" + callArgs + ");\n"
                    + "    if(output != null){\n"
                    + "      if(output instanceof List || output instanceof int[] || output instanceof Object[]){\n"
                    + "        System.out.println(mapper.writeValueAsString(output));\n"
                    + "      } else {\n"
                    + "        System.out.println(output);\n"
                    + "      }\n"
                    + "    }\n"
                    + "  }\n"
                    + "}";

            try (FileWriter fw = new FileWriter(javaFile)) {
                fw.write(wrapper);
            }

            String command = "javac " + javaFile.getName() + " && java " + className + "Wrapper";
            return executeDocker("openjdk:17", userDir, command);
        }
    }

    private String runPython(String userId, String questionId, String userCode,
                             String inputParams, String inputValues) throws Exception {

        String pythonDir = "D:/developer-submissions/" + userId + "/" + questionId;
        Files.createDirectories(Paths.get(pythonDir));

        File file = new File(pythonDir + "/Solution.py");

        JsonNode paramsNode = objectMapper.readTree(inputParams).get("params");
        StringBuilder callArgs = new StringBuilder();
        for (Iterator<JsonNode> it = paramsNode.iterator(); it.hasNext(); ) {
            if (callArgs.length() > 0) callArgs.append(", ");
            callArgs.append(it.next().asText());
        }

        String wrapper = ""
                + "import json\n"
                + "data = json.loads('" + inputValues.replace("'", "\\'") + "')\n"
                + "locals().update(data)\n"
                + "output = None\n"
                + userCode + "\n"
                + "try:\n"
                + "    output = " + detectMethodPython(userCode) + "(" + callArgs + ")\n"
                + "except Exception:\n"
                + "    pass\n"
                + "if output is not None:\n"
                + "    if isinstance(output, (list, tuple, dict)):\n"
                + "        print(json.dumps(output))\n"
                + "    else:\n"
                + "        print(output)\n";

        try (FileWriter fw = new FileWriter(file)) {
            fw.write(wrapper);
        }

        return executeDocker("python:3.10", pythonDir, "python " + file.getName());
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
        Matcher m = Pattern.compile("public\\s+class\\s+(\\w+)").matcher(code);
        if (m.find()) return m.group(1);
        throw new RuntimeException("No public class found in code");
    }

    private String detectMethod(String code) {
        Matcher m = Pattern.compile("public\\s+\\w+\\s+(\\w+)\\s*\\(").matcher(code);
        if (m.find()) return m.group(1);
        return "main";
    }

    private String detectMethodPython(String code) {
        Matcher m = Pattern.compile("def\\s+(\\w+)\\s*\\(").matcher(code);
        if (m.find()) return m.group(1);
        return "";
    }
}