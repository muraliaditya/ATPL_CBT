export interface data {
  codingQuestionId: string;
  questionName: string;
  adminId: string;
  adminName: string;
  createdAt: string;
  updatedAt: string;
}
export interface recentquestion {
  codingQuestionId: string;
  questionName: string;
  adminId: string;
  adminName: string;
}
export interface devSummary {
  devId: string;
  devName: string;
  solvedQuestionsCount: number;
}
export interface codingQuestions{
  codingQuestionId:string,
    question:string,
    description:string,
    Example1:string,
    difficulty:string,
    inputType:string,
    outputType:string,
    testcases:[];
}
export interface testcases{
  id:string;
  input1:string;
  output:string;
  explanation:string;
}