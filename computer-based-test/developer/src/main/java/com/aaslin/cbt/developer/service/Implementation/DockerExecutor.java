package com.aaslin.cbt.developer.service.Implementation;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.aaslin.cbt.developer.exception.CompilationException;
import com.aaslin.cbt.developer.exception.RuntimeExecutionException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class DockerExecutor {

    private final JdbcTemplate jdbcTemplate;

    public DockerExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Executes user code for a specific testcase input
    public String executeUserCode(String language, String code, String questionId, String userId, String inputValuesJson) throws Exception {

        //Fetch testcase metadata for method signature
        String sql = "SELECT input_params, input_type, method_name " +
                     "FROM coding_questions_cbt WHERE coding_question_id = ? LIMIT 1";
        Map<String, Object> meta = jdbcTemplate.queryForMap(sql, questionId);

        String inputParamsJson = (String) meta.get("input_params");
        String inputTypeJson   = (String) meta.get("input_type");
        String methodName      = (String) meta.get("method_name");

        //Define a shared base directory for both Java and Python
        String baseDir = "D://developer-submissions/" + userId + "/" + questionId;
        File dir = new File(baseDir);
        if (!dir.exists()) dir.mkdirs();

        if ("JAVA".equalsIgnoreCase(language)) {
            return runJavaInDocker(code, inputParamsJson, inputTypeJson, methodName, inputValuesJson, dir);
        } else if ("PYTHON".equalsIgnoreCase(language)) {
            return runPythonInDocker(code, inputParamsJson, inputTypeJson, methodName, inputValuesJson, dir);
        }

        throw new RuntimeException("Unsupported language: " + language);
    }

    private String runJavaInDocker(String code,
                                   String inputParamsJson,
                                   String inputTypeJson,
                                   String methodName,
                                   String inputValuesJson,
                                   File dir) throws Exception {

        // Save user Solution.java
        File userFile = new File(dir, "Solution.java");
        try (FileWriter fw = new FileWriter(userFile)) {
            fw.write(code);
        }

        // Parse input types and values
        Map<String, String> typesMap = new ObjectMapper().readValue(inputTypeJson, Map.class);
        Map<String, Object> valuesMap = new ObjectMapper().readValue(inputValuesJson, Map.class);

        // Parse params list
        JsonNode root = new ObjectMapper().readTree(inputParamsJson);
        List<String> paramsList = new ArrayList<>();
        for (JsonNode node : root.get("params")) paramsList.add(node.asText());

        // Build method call
        StringBuilder callBuilder = new StringBuilder();
        callBuilder.append("sol.").append(methodName.substring(0, methodName.indexOf("("))).append("(");
        for (int i = 0; i < paramsList.size(); i++) {
            String param = paramsList.get(i);
            String type = typesMap.get(param);
            if (type == null) throw new RuntimeException("Type not found for param: " + param);

            switch (type.toLowerCase()) {
                case "int":
                    callBuilder.append("(Integer)input.get(\"").append(param).append("\")");
                    break;
                case "string":
                    callBuilder.append("(String)input.get(\"").append(param).append("\")");
                    break;
                case "boolean":
                    callBuilder.append("(Boolean)input.get(\"").append(param).append("\")"); 
                    break;
                case "int[]":
                    callBuilder.append("((java.util.List<Integer>)input.get(\"")
                               .append(param)
                               .append("\")).stream().mapToInt(x->x).toArray()");
                    break;
                case "string[]":
                    callBuilder.append("((java.util.List<String>) input.get(\"")
                               .append(param)
                               .append("\")).toArray(new String[0])");
                    break;
                default: throw new RuntimeException("Unsupported type: " + type);
            }

            if (i < paramsList.size() - 1) callBuilder.append(", ");
        }
        callBuilder.append(");");

        // Build SolutionRunner.java
        StringBuilder runner = new StringBuilder();
        runner.append("import java.util.*;\n");
        runner.append("class JavaRunner {\n");
        runner.append("  public static void main(String[] args) throws Exception {\n");
        runner.append("    Map<String,Object> input = new HashMap<>();\n");

        for (Map.Entry<String, Object> entry : valuesMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Integer || value instanceof Boolean || value instanceof Double || value instanceof Long) {
                runner.append("    input.put(\"").append(key).append("\", ").append(value).append(");\n");
            } else if (value instanceof String) {
                runner.append("    input.put(\"").append(key).append("\", \"").append(value).append("\");\n");
            } else if (value instanceof List) {
                runner.append("    input.put(\"").append(key).append("\", Arrays.asList(");
                List<?> list = (List<?>) value;
                for (int i = 0; i < list.size(); i++) {
                    Object elem = list.get(i);
                    if (elem instanceof String) {
                        runner.append("\"").append(elem).append("\"");
                    } else {
                        runner.append(elem);
                    }
                    if (i < list.size() - 1) runner.append(", ");
                }
                runner.append("));\n");
            }
        }

        runner.append("    Solution sol = new Solution();\n");
        runner.append("    Object output = ").append(callBuilder).append(";\n");
        runner.append("    if(output instanceof int[]) System.out.println(Arrays.toString((int[]) output).replace(\" \", \"\"));\n");
        runner.append("    else if(output instanceof String[]) System.out.println(Arrays.toString((String[]) output));\n");
        runner.append("    else if(output instanceof boolean[]) System.out.println(Arrays.toString((boolean[]) output));\n");
        runner.append("    else if(output instanceof double[]) System.out.println(Arrays.toString((double[]) output));\n");
        runner.append("    else if(output instanceof long[]) System.out.println(Arrays.toString((long[]) output));\n");
        runner.append("    else System.out.println(output);\n");
        runner.append("  }\n");
        runner.append("}\n");

        File runnerFile = new File(dir, "JavaRunner.java");
        try (FileWriter fw = new FileWriter(runnerFile)) {
            fw.write(runner.toString());
        }

        // Compile first
        executeDockerWithCheck("openjdk:17", dir.getAbsolutePath(), "javac Solution.java JavaRunner.java", true);
        // Then run
        return executeDockerWithCheck("openjdk:17", dir.getAbsolutePath(), "java JavaRunner", false);
    }

    private String runPythonInDocker(String code,
            String inputParamsJson,
            String inputTypeJson,
            String methodName,
            String inputValuesJson,
            File dir) throws Exception {

        File userFile = new File(dir, "Solution.py");
        try (FileWriter fw = new FileWriter(userFile)) {
            fw.write(code);
        }

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> valuesMap = mapper.readValue(inputValuesJson, Map.class);

        JsonNode root = mapper.readTree(inputParamsJson);
        List<String> paramsList = new ArrayList<>();
        for (JsonNode node : root.get("params")) paramsList.add(node.asText());

        StringBuilder runner = new StringBuilder();
        runner.append("import json\n");
        runner.append("from Solution import Solution\n\n");
        runner.append("sol = Solution()\n");
        runner.append("input = ").append(mapper.writeValueAsString(valuesMap)).append("\n\n");
        runner.append("output = sol.").append(methodName.substring(0, methodName.indexOf("("))).append("(");
        for (int i = 0; i < paramsList.size(); i++) {
            runner.append("input['").append(paramsList.get(i)).append("']");
            if (i < paramsList.size() - 1) runner.append(", ");
        }
        runner.append(")\n");
        runner.append("if isinstance(output, list):\n");
        runner.append("    print(json.dumps(output, separators=(',', '')))\n");
        runner.append("elif isinstance(output, bool):\n");
        runner.append("    print(str(output).lower())\n");
        runner.append("else:\n");
        runner.append("    print(output)\n");

        File runnerFile = new File(dir, "PythonRunner.py");
        try (FileWriter fileWriter = new FileWriter(runnerFile)) {
            fileWriter.write(runner.toString());
        }

        return executeDockerWithCheck("python:3.10-slim", dir.getAbsolutePath(), "python3 PythonRunner.py", false);
    }

    // 🧠 Enhanced docker execution with error classification
    private String executeDockerWithCheck(String image, String dir, String command, boolean isCompileStep) throws Exception {
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
            if (isCompileStep) {
                throw new CompilationException("Compilation failed:\n" + stderr);
            } else {
                throw new RuntimeExecutionException("Runtime error:\n" + stderr);
            }
        }
        return stdout.trim();
    }
}

