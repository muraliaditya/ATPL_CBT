export interface McqQuestions {
  mcqId: string;
  question: string;
  options: string[];
}

export interface CodingQuestions {
  codingQuestionId: string;
  question: string;
  description: string;
  difficulty: string;
  inputType: string[];
  javaBoilerCode: string;
  pythonBoilerCode: string;

  inputParams: string[];
  outputType: string;
  testcases: Testcase[];
}

export interface Testcase {
  id: string;
  inputs: string[];
  output: string;
  explanation?: string;
  weightage?: number;
}
