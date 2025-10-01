export interface Participants {
  [key: string]: any;

  submissionId: string;
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

export interface contestResultsResponse {
  results: Participants[];
  eligibility: string;
}
