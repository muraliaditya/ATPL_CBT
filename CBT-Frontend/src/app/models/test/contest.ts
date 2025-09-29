type TestcaseStatus = 'PASSED' | 'FAILED';

interface PublicTestcaseResult {
  testcaseId: string;
  input: string[];
  expectedOutput: string;
  actualOutput: string;
  status: TestcaseStatus;
  weightage: number;
}

interface PrivateTestcaseResult {
  testcaseId: string;
  status: TestcaseStatus;
  weightage: number;
}

type CodeStatus =
  | 'SOLVED'
  | 'WRONG ANSWER'
  | 'COMPILATION_ERROR'
  | 'RUNTIME_ERROR';

interface CodeExecutionResponse {
  codeStatus: CodeStatus;
  message: string;
  publicTestcasePassed: number;
  privateTestcasePassed?: number;
  publicTestcaseResults: PublicTestcaseResult[];
  privateTestcaseResults?: PrivateTestcaseResult[];
}
