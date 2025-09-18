import { ContestCodingQuestion } from '../../../models/admin/contest';
import { ContestMCQQuestion } from '../../../models/admin/contest';
import { CodingQuestions } from '../../../models/test/questions';

export interface CreateContestQuestions {
  tempMcqQuestions: Record<string, Record<number, ContestMCQQuestion>>;
  tempcodingQuestions: Record<number, ContestCodingQuestion>;
  finalisedQuestions: string[];
  finalisedQuestionsSet: Set<string>;
  finalisedMcqQuestions: Record<string, ContestMCQQuestion[]>;
  finalisedcodingQuestions: ContestCodingQuestion[];
}
