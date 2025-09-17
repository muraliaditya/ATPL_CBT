import { createReducer, on } from '@ngrx/store';
import { CreateContestQuestions } from './contest.state';
import { AcceptMcqQuestion } from './contest.actions';
import {
  AddMcqSection,
  DeleteMCQSection,
  DeleteMcqQuestion,
  ReplaceMcqQuestion,
  AcceptAllMcqs,
} from './contest.actions';

export const initialContestState: CreateContestQuestions = {
  tempMcqQuestions: {},
  tempcodingQuestions: {},
  finalisedQuestions: [],
  finalisedcodingQuestions: {},
  finalisedMcqQuestions: {},
};

export const contestReducer = createReducer(
  initialContestState,
  on(AddMcqSection, (state, { mcqs, section }) => {
    console.log(initialContestState);
    let data = { ...state.tempMcqQuestions };
    if (!data[section]) {
      data[section] = {};
    } else {
      data[section] = { ...data[section] };
    }
    if (data[section]) {
      let maxQuestionId = 0;
      for (let mcq of mcqs) {
        if (Object.keys(data[section]).length) {
          const questionIds = Object.keys(data[section]).map(Number);
          maxQuestionId = Math.max(...questionIds);
        }

        data[section][maxQuestionId + 1] = mcq;
      }
    }
    return {
      ...state,
      tempMcqQuestions: data,
    };
  }),

  on(DeleteMCQSection, (state, { section }) => {
    let data = { ...state.tempMcqQuestions };
    if (data[section]) {
      delete data[section];
    }
    return {
      ...state,
      tempMcqQuestions: data,
    };
  }),

  on(DeleteMcqQuestion, (state, { section, mcqQuestionId }) => {
    let data = {
      ...state.tempMcqQuestions,
      [section]: {
        ...state.tempMcqQuestions[section],
      },
    };

    if (data[section] && data[section][mcqQuestionId]) {
      delete data[section][mcqQuestionId];
    }
    return {
      ...state,
      tempMcqQuestions: data,
    };
  }),

  on(ReplaceMcqQuestion, (state, { section, mcq, prevMcqId }) => {
    let data = {
      ...state.tempMcqQuestions,
      [section]: {
        ...state.tempMcqQuestions[section],
      },
    };
    if (data[section] && data[section][prevMcqId]) {
      data[section][prevMcqId] = mcq;
    }
    return {
      ...state,
      tempMcqQuestions: data,
    };
  }),
  on(AcceptAllMcqs, (state, { section }) => {
    let data = {
      ...state.tempMcqQuestions,
      [section]: {
        ...state.tempMcqQuestions[section],
      },
    };
    let finalisedQuestions = [...state.finalisedQuestions];
    if (data[section]) {
      let key;
      for (let mcq of Object.keys(data[section])) {
        let key = data[section][Number(mcq)].mcqQuestionId;
        if (!finalisedQuestions.includes(key)) {
          finalisedQuestions.push(key);
        }
      }
    }
    return { ...state, finalisedQuestions: finalisedQuestions };
  }),
  on(AcceptMcqQuestion, (state, { McqId }) => {
    let finalisedQuestions = [...state.finalisedQuestions];
    if (!finalisedQuestions.includes(McqId)) {
      finalisedQuestions.push(McqId);
    }
    return {
      ...state,
      finalisedQuestions: finalisedQuestions,
    };
  })
);
