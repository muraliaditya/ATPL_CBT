import { createAction, props } from '@ngrx/store';
import {
  ContestCodingQuestion,
  ContestMCQQuestion,
} from '../../../models/admin/contest';
export const AddMcqSection = createAction(
  '[Contest] AddMcqSection',
  props<{ mcqs: ContestMCQQuestion[]; section: string }>()
);

export const DeleteMCQSection = createAction(
  '[Contest] DeleteSection',
  props<{ section: string }>()
);

export const changeMcqWeightage = createAction(
  '[Contest] Mcq Weightage',
  props<{ section: string; prevMcqId: number; weightage: number }>()
);

export const DeleteMcqQuestion = createAction(
  '[Contest] DeleteMcq',
  props<{ section: string; mcqQuestionId: number }>()
);

export const ReplaceMcqQuestion = createAction(
  '[Contest] ReplaceMcq',
  props<{ section: string; mcq: ContestMCQQuestion; prevMcqId: number }>()
);

export const AcceptAllMcqs = createAction(
  '[Contest] AcceptMcqs',
  props<{ section: string }>()
);

export const AcceptMcqQuestion = createAction(
  '[Contest] AcceptMcq',
  props<{ Mcq: ContestMCQQuestion; section: string }>()
);

export const ReplaceSection = createAction(
  '[Contest] Accept Section',
  props<{ section: string; mcqs: ContestMCQQuestion[] }>()
);

export const AcceptCodingQuestion = createAction(
  '[Contest] Accept Coding Question',
  props<{ codeQuestion: ContestCodingQuestion }>()
);

export const DeleteCodingQuestion = createAction(
  '[Contest] Delete Coding Question',
  props<{ prevCodeId: number }>()
);

export const ReplaceCodingQuestion = createAction(
  '[Contest] Replace Question',
  props<{ codeQuestion: ContestCodingQuestion; prevCodeId: number }>()
);

export const AcceptAllCodingQuestions = createAction(
  '[Contest] Accept All Coding Ques',
  props<{ codeQuestions: ContestCodingQuestion[] }>()
);

export const ReplaceAllCodingquestions = createAction(
  '[Contest] Replace All',
  props<{ codeQuestions: ContestCodingQuestion[] }>()
);

export const DeleteCodingSection = createAction('[Contest] Delete All');

export const AddCodingQuestions = createAction(
  '[Contest] Add Coding Questions',
  props<{ codeQuestions: ContestCodingQuestion[] }>()
);

export const changeCodeWeightage = createAction(
  '[Contest] changeCodeWeightage',
  props<{ codingQuestionId: number; weightage: number }>()
);

export const acceptIdsIntoRegenerateIds = createAction(
  '[Contest] regenerateIds',
  props<{ Ids: string[] }>()
);
