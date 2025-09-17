import { ContestCodingQuestion } from '../../../models/admin/contest';
import { ContestMCQQuestion } from '../../../models/admin/contest';
import { CodingQuestions } from '../../../models/test/questions';

export interface CreateContestQuestions {
  tempMcqQuestions: Record<string, Record<string, ContestMCQQuestion>>;
  tempcodingQuestions: Record<string, CodingQuestions>;
  finalisedQuestions: string[];
  finalisedMcqQuestions: Record<string, Record<string, ContestMCQQuestion>>;
  finalisedcodingQuestions: Record<string, CodingQuestions>;
}
