export interface Contest {
  id: string;
  name: string;
  status: 'ACTIVE' | 'COMPLETED';
  startTime?: string;
  endTime?: string;
  duration?: string;
  eligibility?: string;
}
export interface McqQuestions {
  mcqId: string;
  question: string;
  options: string[];
}

export interface CodeQuestions {
  codeQuestionId: string;
  questionName: string;
  difficulty: string;
  description:string;
  inputParams:string[];
  inputType: string[];
  outputType: string;
  testcases: Testcase[];
}

export interface Testcase {
  testcaseId: string;
  inputValues: string[];
  expectedOutput:string;
  testcaseType: string;
  explanation: string;
}
