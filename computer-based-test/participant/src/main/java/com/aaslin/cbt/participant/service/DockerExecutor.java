package com.aaslin.cbt.participant.service;

import org.springframework.stereotype.Component;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.common.model.TestcaseResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component("ParticipantDockerExecutor")
public class DockerExecutor {

    private static final String SAVE_BASE_PATH = "D:\\codes";

    public String runTemporaryCode(String language, String code, String stdin) throws Exception {
        if (language.equalsIgnoreCase("JAVA")) {
            return runJavaInDocker(code, stdin, false, null);
        } else if (language.equalsIgnoreCase("PYTHON")) {
            return runPythonInDocker(code, stdin, false, null);
        }
        throw new RuntimeException("Unsupported language: " + language);
    }

    public String runAndSaveCode(String participantId, String questionId, String language, String code) throws Exception {
        String savePath = SAVE_BASE_PATH + File.separator + participantId + File.separator + questionId;
        File dir = new File(savePath);
        if (!dir.exists()) dir.mkdirs();

        String fileName;
        if (language.equalsIgnoreCase("JAVA")) {
            String className = extractClassName(code);
            fileName = participantId + "_" + questionId + ".java";
        } else if (language.equalsIgnoreCase("PYTHON")) {
            fileName = participantId + "_" + questionId + ".py";
        } else {
            throw new RuntimeException("Unsupported language: " + language);
        }

        File file = new File(dir, fileName);
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(code);
        }
        return file.getAbsolutePath();
    }

    private String runJavaInDocker(String code, String stdin, boolean saveFile, String savePath) throws Exception {
        String className;
        Matcher m = Pattern.compile("public\\s+class\\s+(\\w+)").matcher(code);
        if (m.find()) className = m.group(1);
        else className = "Solution";

        String fileName = className + ".java";
        File dir=saveFile && savePath !=null ? new File(savePath) :new File(".");
        if(!dir.exists()) dir.mkdirs();
        File file=new File(dir,fileName);
        
        String javaCode;
        if (!code.contains("public class " + className)) {
            javaCode = "public class " + className + " {\n" + code + "\n}";
        } else {
            javaCode = code;
        }

        try (FileWriter fw = new FileWriter(file)) {
            fw.write(javaCode);
        }
        
        File inputFile=new File(dir,"input.txt");
        try(FileWriter fw=new FileWriter(inputFile)){
        	fw.write(stdin);
        }

        String command = String.format("javac %s && java %s < %s", file.getName(),className,inputFile.getName());
        String output = executeDocker("openjdk:17", file.getAbsoluteFile().getParent(), command);

        if (!saveFile) file.delete();
        inputFile.delete();
        return output.trim();
    }

    private String runPythonInDocker(String code, String stdin, boolean saveFile, String savePath) throws Exception {
    	ObjectMapper mapper=new ObjectMapper();
    	System.out.println("stdin "+stdin);
    	String stdinput;
    	try {
    	Map<String,Object>inputs=mapper.readValue(stdin,new TypeReference<Map<String,Object>>() {});
    	stdinput=inputs.values().stream().map(Object::toString).collect(Collectors.joining("\n"));     
    	}catch(Exception e) {
    		stdinput=stdin.trim();
    	}
    			File file = saveFile && savePath != null ? new File(savePath, "solution_" + ".py")
                : File.createTempFile("Solution", ".py");

        try (FileWriter fw = new FileWriter(file)) {
            fw.write(code);
        }
        
        File inputFile=new File(file.getParent(),"input.txt");
       Files.writeString(inputFile.toPath(),stdinput);

        String command = "python " +file.getName()+ " < "+inputFile.getName();
        String output = executeDocker("python:3.10", file.getAbsoluteFile().getParent(), command);

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
        return "Solution";
    }
}