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
