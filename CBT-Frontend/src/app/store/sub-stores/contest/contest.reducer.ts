import { createReducer, on } from '@ngrx/store';
import { CreateContestQuestions } from './contest.state';
import {
  AcceptAllCodingQuestions,
  AcceptMcqQuestion,
  DeleteCodingQuestion,
  ReplaceSection,
} from './contest.actions';
import {
  AddMcqSection,
  DeleteMCQSection,
  DeleteMcqQuestion,
  ReplaceMcqQuestion,
  AcceptAllMcqs,
  AcceptCodingQuestion,
  ReplaceCodingQuestion,
  ReplaceAllCodingquestions,
} from './contest.actions';
import { max } from 'rxjs';
import { ContestCodingQuestion } from '../../../models/admin/contest';

export const initialContestState: CreateContestQuestions = {
  tempMcqQuestions: {},
  tempcodingQuestions: {},
  finalisedQuestions: [],
  finalisedcodingQuestions: [],
  finalisedMcqQuestions: {},
  finalisedQuestionsSet: new Set(),
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
    let finalisedMcqs = { ...state.finalisedMcqQuestions };
    if (!finalisedMcqs[section]) {
      finalisedMcqs[section] = [];
    } else {
      finalisedMcqs[section] = [...finalisedMcqs[section]];
    }
    let finalisedQuestions = [...state.finalisedQuestions];
    if (data[section]) {
      let key;
      for (let mcq of Object.keys(data[section])) {
        let key = data[section][Number(mcq)].mcqQuestionId;
        if (!finalisedQuestions.includes(key)) {
          finalisedMcqs[section] = [
            ...finalisedMcqs[section],
            data[section][Number(mcq)],
          ];
          finalisedQuestions.push(key);
        }
      }
    }
    return {
      ...state,
      finalisedQuestions: finalisedQuestions,
      finalisedMcqQuestions: finalisedMcqs,
    };
  }),

  on(AcceptMcqQuestion, (state, { Mcq, section }) => {
    let finalisedQuestions = [...state.finalisedQuestions];
    let finalisedMcqs = {
      ...state.finalisedMcqQuestions,
    };
    if (!finalisedMcqs[section]) {
      finalisedMcqs[section] = [];
    } else {
      finalisedMcqs[section] = [...finalisedMcqs[section]];
    }

    if (!finalisedQuestions.includes(Mcq.mcqQuestionId)) {
      finalisedQuestions.push(Mcq.mcqQuestionId);
      finalisedMcqs[section].push(Mcq);
    }
    return {
      ...state,
      finalisedQuestions: finalisedQuestions,
      finalisedMcqQuestions: finalisedMcqs,
    };
  }),
  on(ReplaceSection, (state, { section, mcqs }) => {
    let data = {
      ...state.tempMcqQuestions,
      [section]: {
        ...state.tempMcqQuestions[section],
      },
    };
    let maxQuestionId = 0;
    let finalisedMcqs = {
      ...state.finalisedMcqQuestions,
    };
    if (!finalisedMcqs[section]) {
      finalisedMcqs[section] = [];
    } else {
      finalisedMcqs[section] = [];
    }

    if (data[section]) {
      data[section] = { ...data[section] };
    }
    let finalisedQuestionsIds = [...state.finalisedQuestions];
    let matchedIds: string[] = Object.keys(data[section]).map(
      (id) => data[section][Number(id)].mcqQuestionId
    );
    if (matchedIds.length) {
      finalisedQuestionsIds = finalisedQuestionsIds.filter(
        (id) => !matchedIds.includes(id)
      );
    }
    for (let mcq of mcqs) {
      data[section][maxQuestionId + 1] = mcq;
      maxQuestionId++;
    }
    return {
      ...state,
      tempMcqQuestions: data,
      finalisedQuestions: finalisedQuestionsIds,
      finalisedMcqQuestions: finalisedMcqs,
    };
  }),

  on(AcceptCodingQuestion, (state, { codeQuestion }) => {
    let codingQuesData = { ...state.tempcodingQuestions };
    let finalisedQuestions = [...state.finalisedQuestions];
    let finalcodingquestions = [...state.finalisedcodingQuestions];

    if (!finalisedQuestions.includes(codeQuestion.codeQuestionId)) {
      finalisedQuestions.push(codeQuestion.codeQuestionId);
      finalcodingquestions.push(codeQuestion);
    }

    return {
      ...state,
      finalisedQuestions,
      finalisedcodingQuestions: finalcodingquestions,
    };
  }),

  on(ReplaceCodingQuestion, (state, { codeQuestion, prevCodeId }) => {
    let codingQuesData = { ...state.tempcodingQuestions };
    if (codingQuesData[Number(prevCodeId)]) {
      codingQuesData[Number(prevCodeId)] = codeQuestion;
    }

    return {
      ...state,
      tempcodingQuestions: codingQuesData,
    };
  }),

  on(DeleteCodingQuestion, (state, { prevCodeId }) => {
    let codingQuesData = {
      ...state.tempcodingQuestions,
      [prevCodeId]: state.tempcodingQuestions[prevCodeId],
    };
    if (codingQuesData[Number(prevCodeId)]) {
      delete codingQuesData[Number(prevCodeId)];
    }
    return { ...state, tempcodingQuestions: codingQuesData };
  }),

  on(AcceptAllCodingQuestions, (state, { codeQuestions }) => {
    let codingQuesData = {};
    let finalisedcodeQuestions = [];
    let maxQuestionId = 0;
    for (let question of codeQuestions) {
      maxQuestionId = maxQuestionId + 1;
      codeQuestions[maxQuestionId] = question;
      finalisedcodeQuestions.push(question);
    }

    return {
      ...state,
      tempcodingQuestions: codingQuesData,
      finalisedcodingQuestions: finalisedcodeQuestions,
    };
  }),
  on(ReplaceAllCodingquestions, (state, { codeQuestions }) => {
    let codingQuesData = {};
    let maxQuestionId = 0;
    for (let question of codeQuestions) {
      maxQuestionId = maxQuestionId + 1;
      codeQuestions[maxQuestionId] = question;
    }

    return {
      ...state,
      tempcodingQuestions: codingQuesData,
    };
  })
);
