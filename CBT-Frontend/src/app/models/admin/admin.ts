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

export interface mcqSections{
  mcqQuestionId: string;
  question: string;
  option1: string;
  option2: string;
  option3: string;
  option4: string;
  answerKey: string;
  weightage: number;
  section: string;
}

export interface qs{
  Question:any;
  ContestName:any;
  value1:any;
  value2:any;
  value3:any;
  value4:any;
  Weightage:any;
}