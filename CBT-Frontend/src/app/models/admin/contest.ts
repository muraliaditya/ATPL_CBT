export interface Contest {}

export enum Status {
  SOLVED = 'SOLVED',
  PARTIALLY_SOLVED = 'PARTIALLY_SOLVED',
  COMPILATION_ERROR = 'COMPILATION_ERROR',
  RUNTIME_ERROR = 'RUNTIME_ERROR',
  WRONG_ANSWER = 'WRONG_ANSWER',
}

export interface ContestTestcase {
  testcaseId: string;
  inputValues: any[];
  expectedOutput: string;
  actualOutput?: string;
  weightage: number;
  testcaseType: 'PUBLIC' | 'PRIVATE';
  explanation?: string;
}

export interface ContestMCQQuestion {
  mcqQuestionId: string;
  questionText: string;
  option1: string;
  option2: string;
  option3: string;
  option4: string;
  answerKey: string;
  weightage: number;
  section: string;
}

export interface ContestCodingQuestion {
  codeQuestionId: string;
  questionName: string;
  description: string;
  javaBoilerCode?: String;
  PythonBoilerCode?: string;
  difficulty: 'EASY' | 'DIFFICULT' | 'MEDIUM';
  inputParams: string[];
  weightage?: number;
  inputType: string[];
  outputType: string;
  testcases: ContestTestcase[];
}
