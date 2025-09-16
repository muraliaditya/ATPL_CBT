import { createReducer, on } from '@ngrx/store';
import { McqQuestions } from '../../../models/test/questions';
import { ChangeList } from './contest.actions';
import { AddToList } from './contest.actions';
export interface McqList {
  count: number;
  McqQuestions: McqQuestions[];
}

export const contestInitialState: McqList = {
  count: 0,
  McqQuestions: [],
};

export const contestReducer = createReducer(
  contestInitialState,
  on(ChangeList, (state, { id, Mcq }) => ({
    ...state,
    McqQuestions: state.McqQuestions.map((item) =>
      id === item.mcqId ? Mcq : item
    ),
  })),
  on(AddToList, (state, { Mcq }) => ({
    ...state,
    McqQuestions: [...state.McqQuestions, Mcq],
  }))
);
