import { createAction, props } from '@ngrx/store';
import { ContestMCQQuestion } from '../../../models/admin/contest';
export const AddMcqSection = createAction(
  '[Contest] AddMcqSection',
  props<{ mcqs: ContestMCQQuestion[]; section: string }>()
);

export const DeleteMCQSection = createAction(
  '[Contest] DeleteSection',
  props<{ section: string }>()
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
  props<{ McqId: string }>()
);
