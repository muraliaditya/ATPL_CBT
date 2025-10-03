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
  description: string;
  inputParams: string[];
  inputType: string[];
  outputType: string;
  testcases: Testcase[];
}

export interface Testcase {
  testcaseId: string;
  inputValues: string[];
  expectedOutput: string;
  testcaseType: 'PUBLIC' | 'PRIVATE';
  explanation: string;
}

export interface mcqSections {
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

export interface data {
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
export interface submissions {
  devId: string;
  userName: string;
  questionType: string;
  questionId: string;
}

export interface codingQuestions {
  questionId: string;
  questionName: string;
  difficulty: 'EASY' | 'MEDIUM' | 'HARD';
}

export interface codeListingResponse {
  pageNo: number;
  totalPages: Number;
  codingQuestions: codingQuestions;
}
