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
  inputType: string;
  outputType: string;
  testcases: Testcase[];
}

export interface Testcase {
  id: string;
  input1: string;
  input2?: string;
  output: string;
  explanation?: string;
}
