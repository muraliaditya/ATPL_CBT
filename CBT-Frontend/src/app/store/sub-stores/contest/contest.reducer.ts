import { createReducer, on } from '@ngrx/store';
import { CreateContestQuestions } from './contest.state';
import {
  AcceptAllCodingQuestions,
  AcceptMcqQuestion,
  DeleteCodingQuestion,
  ReplaceSection,
  DeleteCodingSection,
  AddCodingQuestions,
  changeMcqWeightage,
  changeCodeWeightage,
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
import { ContestCodingQuestion } from '../../../models/admin/contest';
import { distributeMarks } from '../../../utils/splitMarks';

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

  on(changeMcqWeightage, (state, { section, prevMcqId, weightage }) => {
    console.log('changing');
    console.log(section);
    console.log(prevMcqId);
    console.log(weightage);
    let isChanged: boolean = false;
    let data = {
      ...state.tempMcqQuestions,
      [section]: {
        ...state.tempMcqQuestions[section],
      },
    };
    if (data[section] && data[section][prevMcqId]) {
      data[section][prevMcqId] = { ...data[section][prevMcqId], weightage };
    }
    let finalisedMcqs = { ...state.finalisedMcqQuestions };
    let finalisedQuesSet = new Set(state.finalisedQuestionsSet);

    if (finalisedMcqs[section]) {
      finalisedMcqs[section] = [...finalisedMcqs[section]];

      let key = data[section][prevMcqId].mcqQuestionId;
      if (finalisedQuesSet.has(data[section][prevMcqId].mcqQuestionId)) {
        isChanged = true;
        finalisedMcqs[section] = [...finalisedMcqs[section]].map((ques) => {
          if (ques.mcqQuestionId === key) {
            return { ...ques, weightage };
          } else {
            return ques;
          }
        });
      }
    }
    console.log(data);
    console.log(finalisedMcqs);

    return {
      ...state,
      tempMcqQuestions: data,
      finalisedMcqQuestions: isChanged
        ? finalisedMcqs
        : state.finalisedMcqQuestions,
    };
  }),
  on(AddMcqSection, (state, { mcqs, section }) => {
    let marks = 31;
    let data = { ...state.tempMcqQuestions };
    let sectionLength = mcqs.length;
    let marksDistribution: number[] = distributeMarks(marks, sectionLength);

    if (!data[section]) {
      data[section] = {};
    } else {
      data[section] = { ...data[section] };
    }
    if (data[section]) {
      const questionIds = Object.keys(data[section]).map(Number);
      let maxQuestionId = questionIds.length ? Math.max(...questionIds) : 0;
      for (const [index, mcq] of mcqs.entries()) {
        maxQuestionId++;

        data[section][maxQuestionId] = {
          ...mcq,
          weightage: marksDistribution[index],
        };
      }
    }
    return {
      ...state,
      tempMcqQuestions: data,
    };
  }),

  on(DeleteMCQSection, (state, { section }) => {
    let data = { ...state.tempMcqQuestions };
    let finalisedMcqs = { ...state.finalisedMcqQuestions };
    let finalisedQuesSet = new Set(state.finalisedQuestionsSet);

    if (!finalisedMcqs[section]) {
      finalisedMcqs[section] = [];
    } else {
      finalisedMcqs[section] = [...finalisedMcqs[section]];
    }
    if (data[section]) {
      let quesId;
      for (let key of Object.keys(data[section])) {
        quesId = data[section][Number(key)].mcqQuestionId;
        if (finalisedQuesSet.has(quesId)) {
          finalisedQuesSet.delete(quesId);
        }
      }
      delete data[section];
      delete finalisedMcqs[section];
    }

    return {
      ...state,
      tempMcqQuestions: data,
      finalisedMcqQuestions: finalisedMcqs,
      finalisedQuestionsSet: finalisedQuesSet,
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
    let finalisedQuesSet = new Set(state.finalisedQuestionsSet);
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
        if (!finalisedQuesSet.has(key)) {
          finalisedQuesSet.add(key);
          finalisedMcqs[section] = [
            ...finalisedMcqs[section],
            data[section][Number(mcq)],
          ];
        }
      }
    }
    return {
      ...state,
      finalisedMcqQuestions: finalisedMcqs,
      finalisedQuestionsSet: finalisedQuesSet,
    };
  }),

  on(AcceptMcqQuestion, (state, { Mcq, section }) => {
    let finalisedQuestions = [...state.finalisedQuestions];
    let finalisedMcqs = {
      ...state.finalisedMcqQuestions,
    };
    let finalisedQuesSet = new Set(state.finalisedQuestionsSet);

    if (!finalisedMcqs[section]) {
      finalisedMcqs[section] = [];
    } else {
      finalisedMcqs[section] = [...finalisedMcqs[section]];
    }
    if (!finalisedQuesSet.has(Mcq.mcqQuestionId)) {
      finalisedQuesSet.add(Mcq.mcqQuestionId);
      finalisedMcqs[section].push(Mcq);
    }

    return {
      ...state,
      finalisedQuestions: finalisedQuestions,
      finalisedMcqQuestions: finalisedMcqs,
      finalisedQuestionsSet: finalisedQuesSet,
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
    if (finalisedMcqs[section]) {
      delete finalisedMcqs[section];
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

  on(changeCodeWeightage, (state, { codingQuestionId, weightage }) => {
    let finalisedQuesSet = new Set(state.finalisedQuestionsSet);
    let isChanged: boolean = false;
    let finalcodingquestions = [...state.finalisedcodingQuestions];
    let codingQuesData = { ...state.tempcodingQuestions };
    if (codingQuesData[codingQuestionId]) {
      codingQuesData[codingQuestionId] = {
        ...codingQuesData[codingQuestionId],
        weightage: weightage,
      };
    }
    let key = codingQuesData[codingQuestionId].codeQuestionId;
    if (finalisedQuesSet.has(key)) {
      isChanged = true;
      finalcodingquestions = [...finalcodingquestions].map((ques) => {
        if (ques.codeQuestionId === key) {
          return { ...ques, weightage };
        } else {
          return ques;
        }
      });
    }
    return {
      ...state,
      tempcodingQuestions: codingQuesData,
      finalisedcodingQuestions: isChanged
        ? finalcodingquestions
        : state.finalisedcodingQuestions,
    };
  }),

  on(AcceptCodingQuestion, (state, { codeQuestion }) => {
    let finalisedQuesSet = new Set(state.finalisedQuestionsSet);
    let finalcodingquestions = [...state.finalisedcodingQuestions];

    if (!finalisedQuesSet.has(codeQuestion.codeQuestionId)) {
      finalisedQuesSet.add(codeQuestion.codeQuestionId);
      finalcodingquestions.push(codeQuestion);
    }

    return {
      ...state,
      finalisedQuestionsSet: finalisedQuesSet,
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
    //console.log(prevCodeId);
    if (codingQuesData[Number(prevCodeId)]) {
      delete codingQuesData[Number(prevCodeId)];
    }
    return { ...state, tempcodingQuestions: codingQuesData };
  }),

  on(AcceptAllCodingQuestions, (state, { codeQuestions }) => {
    //console.log(codeQuestions);
    let finalQuesData = new Set(state.finalisedQuestionsSet);
    let finalisedcodeQuestions = [...state.finalisedcodingQuestions];
    for (let question of codeQuestions) {
      if (!finalQuesData.has(question.codeQuestionId)) {
        finalQuesData.add(question.codeQuestionId);
        finalisedcodeQuestions.push(question);
      }
    }

    return {
      ...state,
      finalisedcodingQuestions: finalisedcodeQuestions,
      finalisedQuestionsSet: finalQuesData,
    };
  }),
  on(ReplaceAllCodingquestions, (state, { codeQuestions }) => {
    let codingQuesData: Record<number, ContestCodingQuestion> = {};
    let maxQuestionId = 0;
    let finalisedQuesSet = new Set(state.finalisedQuestionsSet);
    //console.log(finalisedQuesSet);

    let codingFinalQuestionsIds = [...state.finalisedcodingQuestions].map(
      (ques) => ques.codeQuestionId
    );
    //console.log(codingFinalQuestionsIds);
    for (let id of codingFinalQuestionsIds) {
      if (finalisedQuesSet.has(id)) {
        finalisedQuesSet.delete(id);
      }
    }
    for (let question of codeQuestions) {
      maxQuestionId = maxQuestionId + 1;
      codingQuesData[maxQuestionId] = question;
    }

    return {
      ...state,
      finalisedQuestionsSet: finalisedQuesSet,
      tempcodingQuestions: codingQuesData,
      finalisedcodingQuestions: [],
    };
  }),
  on(DeleteCodingSection, (state) => {
    let finalisedQuesSet = new Set(state.finalisedQuestionsSet);
    let codingFinalQuestionsIds = [...state.finalisedcodingQuestions].map(
      (ques) => ques.codeQuestionId
    );
    for (let id of codingFinalQuestionsIds) {
      if (finalisedQuesSet.has(id)) {
        finalisedQuesSet.delete(id);
      }
    }

    return {
      ...state,
      tempcodingQuestions: {},
      finalisedQuestionsSet: finalisedQuesSet,
      finalisedcodingQuestions: [],
    };
  }),
  on(AddCodingQuestions, (state, { codeQuestions }) => {
    //console.log(codeQuestions);
    let maxQuestionId = 0;
    let marks = 33;
    let sectionLength = codeQuestions.length;
    let marksDistribution: number[] = distributeMarks(marks, sectionLength);

    let tempCodingquestions = { ...state.tempcodingQuestions };

    for (let [index, question] of codeQuestions.entries()) {
      if (Object.keys(tempCodingquestions).length) {
        const questionIds = Object.keys(tempCodingquestions).map(Number);
        maxQuestionId = Math.max(...questionIds);
      }
      tempCodingquestions[maxQuestionId + 1] = {
        ...question,
        weightage: marksDistribution[index],
      };
      //console.log(question);
      //console.log(tempCodingquestions);
    }

    return {
      ...state,
      tempcodingQuestions: tempCodingquestions,
    };
  })
);
