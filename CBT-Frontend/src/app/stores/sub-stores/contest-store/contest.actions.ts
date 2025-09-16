import { createAction, props } from '@ngrx/store';
import { McqQuestions } from '../../../models/test/questions';

export const ChangeList = createAction(
  '[Contest] ChangeQuestion',
  props<{ id: string; Mcq: McqQuestions }>()
);

export const AddToList = createAction(
  '[Contest] AddQuestion',
  props<{ Mcq: McqQuestions }>()
);
