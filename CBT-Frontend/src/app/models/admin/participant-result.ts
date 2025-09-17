export interface MCQQuestionResponse {
  mcqQuestionId: string;
  question: string;
  option1: string;
  option2: string;
  option3: string;
  option4: string;
  selectedAnswer: string;
  answerKey: string;
  isCorrect: boolean;
  weightage: number;
  section: string;
}

export enum Status {
  SOLVED = 'SOLVED',
  PARTIALLY_SOLVED = 'PARTIALLY_SOLVED',
  COMPILATION_ERROR = 'COMPILATION_ERROR',
  RUNTIME_ERROR = 'RUNTIME_ERROR',
  WRONG_ANSWER = 'WRONG_ANSWER',
}

export interface Testcase {
  testcaseId: string;
  inputValues: any[];
  expectedOutput: string;
  actualOutput?: string;
  weightage: number;
  testcaseStatus: 'PASSED' | 'FAILED';
  testcaseType: 'PUBLIC' | 'PRIVATE';
  explanation?: string;
}

export interface Participant {
  [key: string]: any;

  submissionId?: string;
  participantId: string;
  userName: string;
  email: string;
  college?: string;
  collegeRegdNo?: string;
  percentage?: number;
  codingMarks: number;
  company?: string;
  overallExperience?: number;
  designation?: string;
  mcqMarks: number;
  totalMarks: number;
}

export interface CodingQuestionResponse {
  codeQuestionId: string;
  questionName: string;
  description: string;
  submittedCode: string;
  difficulty: 'Easy' | 'difficult' | 'medium';
  languageType: string;
  status: Status;
  publicTestCasesPassed: number;
  privateTestCasesPassed: number;
  inputParams: string[];
  inputType: string[];
  outputFormat: string;
  testcases: Testcase[];
}
