package com.aaslin.code_runner.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "coding_testcases")
@Getter
@Setter
public class CodingTestcase {
    @Id
    private String testcaseId;

    private String codingQuestionId;

    @Lob
    private String input;

    @Lob
    private String expectedOutput;

    private int marks;
}