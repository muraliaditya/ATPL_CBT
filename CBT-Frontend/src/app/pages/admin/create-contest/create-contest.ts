import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { AdminHeader } from '../../../components/UI/admin-header/admin-header';
import { CommonModule } from '@angular/common';
import { AppState } from '../../../store/main-store/app.state';
import {
  ContestCodingQuestion,
  ContestMCQQuestion,
} from '../../../models/admin/contest';
import {
  AddMcqSection,
  DeleteMcqQuestion,
  DeleteMCQSection,
  ReplaceMcqQuestion,
  AcceptAllMcqs,
  AcceptMcqQuestion,
  ReplaceSection,
  AddCodingQuestions,
  DeleteCodingQuestion,
  AcceptCodingQuestion,
  AcceptAllCodingQuestions,
  ReplaceCodingQuestion,
  ReplaceAllCodingquestions,
} from '../../../store/sub-stores/contest/contest.actions';
import { FormArray, ReactiveFormsModule } from '@angular/forms';
import { DatePicker } from 'primeng/datepicker';
import { ToggleSection } from '../../../components/UI/toggle-section/toggle-section';

import {
  FormBuilder,
  FormGroup,
  FormsModule,
  Validators,
} from '@angular/forms';
import { Select } from 'primeng/select';

import { FloatLabel } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { ToggleSwitch } from 'primeng/toggleswitch';

import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { TestcaseFilterPipe } from '../../../pipes/testcase-filter-pipe';
import { ContestTestcase } from '../../../models/admin/contest';

@Component({
  selector: 'app-create-contest',
  imports: [
    AdminHeader,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    FloatLabel,
    InputTextModule,
    Select,
    ToggleSwitch,
    DatePicker,
    ToggleSection,
    TestcaseFilterPipe,
  ],
  templateUrl: './create-contest.html',
  styleUrl: './create-contest.css',
})
export class CreateContest implements OnInit {
  codeQuestionsGeneratorForm: FormGroup;
  contestName: string = '';
  eligibility: string = '';
  active: boolean = false;
  eligibilityOptions: string[] = [
    'Student',
    'Priliminary',
    'Intermediate',
    'Advanced',
  ];
  constructShowSectionCondition(idx: number) {
    if (idx === 0) {
      return true;
    } else {
      return false;
    }
  }
  constructHeader(value: string) {
    return `Question - ${value}`;
  }
  filterTestCase(testCase: ContestTestcase): boolean {
    if (!testCase.explanation) {
      return false;
    }
    if (testCase.explanation.length && testCase.testcaseType === 'PUBLIC') {
      return true;
    }
    return false;
  }
  startTime: Date[] | undefined;
  endTime: Date[] | undefined;
  duration: number | '' = '';
  codingQuestions$: Observable<Record<number, ContestCodingQuestion>>;

  preferences: FormGroup;
  McqList$: Observable<Record<string, Record<number, ContestMCQQuestion>>>;
  finalisedIDs$: Observable<string[]>;
  finalisedIds: string[] = [];
  finalisedMcqs$: Observable<Record<string, ContestMCQQuestion[]>>;
  difficultyOptions: string[] = ['Easy', 'Medium', 'Difficult'];
  finalisedQuestionsSet$: Observable<Set<string>>;
  finalisedQuestionSet: Set<string> = new Set();
  finalisedCodingQuestions$: Observable<ContestCodingQuestion[]>;

  constructor(private store: Store<AppState>, private fb: FormBuilder) {
    this.McqList$ = this.store.select(
      (state) => state.contest.tempMcqQuestions
    );
    this.finalisedIDs$ = this.store.select(
      (state) => state.contest.finalisedQuestions
    );
    this.finalisedMcqs$ = this.store.select(
      (state) => state.contest.finalisedMcqQuestions
    );
    this.finalisedQuestionsSet$ = this.store.select(
      (state) => state.contest.finalisedQuestionsSet
    );
    this.finalisedCodingQuestions$ = this.store.select(
      (state) => state.contest.finalisedcodingQuestions
    );
    this.codeQuestionsGeneratorForm = this.fb.group({
      count: ['', [Validators.required, Validators.min(1)]],
      marks: ['', [Validators.required, Validators.min(10)]],
      difficulty: ['', Validators.required],
    });
    this.preferences = this.fb.group({
      choices: this.fb.array([]),
    });
    this.codingQuestions$ = this.store.select(
      (state) => state.contest.tempcodingQuestions
    );
  }

  checkIsSectionFinalised(
    sectionData: Record<number, ContestMCQQuestion>
  ): boolean {
    let questionKeys = Object.keys(sectionData);
    let questionMcqs = questionKeys.map(
      (key) => sectionData[Number(key)].mcqQuestionId
    );
    return questionMcqs.every((id) => this.finalisedQuestionSet.has(id));
  }
  currentSection: 'Mcqs' | 'Coding' = 'Mcqs';
  changeSection(value: 'Mcqs' | 'Coding') {
    this.currentSection = value;
  }

  replaceCodequestion(prevCodeId: string) {
    let ques: ContestCodingQuestion = {
      codeQuestionId: 'Q1',
      questionName: 'Hi all of you?',
      description: 'Write a function that returns the sum of two integers.',
      difficulty: 'Easy',
      inputParams: ['a', 'b'],
      inputType: ['number', 'number'],
      outputFormat: 'number',
      testcases: [
        {
          testcaseId: 'TC1',
          inputValues: [2, 3],
          expectedOutput: '5',
          weightage: 1,
          testcaseType: 'PUBLIC',
          explanation: '2 + 3 = 5',
        },
        {
          testcaseId: 'TC2',
          inputValues: [-1, 4],
          expectedOutput: '3',
          weightage: 1,
          testcaseType: 'PRIVATE',
        },
      ],
    };
    this.store.dispatch(
      ReplaceCodingQuestion({
        codeQuestion: ques,
        prevCodeId: parseInt(prevCodeId),
      })
    );
  }

  acceptAllCodingquestions(codes: Record<number, ContestCodingQuestion>) {
    const ques = [...Object.keys(codes)].map((id) => codes[Number(id)]);
    console.log(ques);
    this.store.dispatch(
      AcceptAllCodingQuestions({
        codeQuestions: ques,
      })
    );
  }
  replaceCodingSection() {
    const codingQuestions: ContestCodingQuestion[] = [
      {
        codeQuestionId: 'Q8',
        questionName: 'Sum of Four Numbers',
        description: 'Write a function that returns the sum of two integers.',
        difficulty: 'Easy',
        inputParams: ['a', 'b'],
        inputType: ['number', 'number'],
        outputFormat: 'number',
        testcases: [
          {
            testcaseId: 'TC1',
            inputValues: [2, 3],
            expectedOutput: '5',
            weightage: 1,
            testcaseType: 'PUBLIC',
            explanation: '2 + 3 = 5',
          },
          {
            testcaseId: 'TC2',
            inputValues: [-1, 4],
            expectedOutput: '3',
            weightage: 1,
            testcaseType: 'PRIVATE',
          },
        ],
      },
      {
        codeQuestionId: 'Q7',
        questionName: 'Reverse anfdksaf String',
        description: 'Create a function that reverses a given string.',
        difficulty: 'medium',
        inputParams: ['str'],
        inputType: ['string'],
        outputFormat: 'string',
        testcases: [
          {
            testcaseId: 'TC1',
            inputValues: ['hello'],
            expectedOutput: 'olleh',
            weightage: 2,
            testcaseType: 'PUBLIC',
            explanation: 'Reversing "hello" gives "olleh"',
          },
          {
            testcaseId: 'TC2',
            inputValues: ['world'],
            expectedOutput: 'dlrow',
            weightage: 2,
            testcaseType: 'PRIVATE',
          },
        ],
      },
      {
        codeQuestionId: 'Q4',
        questionName: 'Find Factorial',
        description:
          'Implement a function tdsiuhfjksdf find the factorial of a non-negative integer.',
        difficulty: 'difficult',
        inputParams: ['n'],
        inputType: ['number'],
        outputFormat: 'number',
        testcases: [
          {
            testcaseId: 'TC1',
            inputValues: [5],
            expectedOutput: '120',
            weightage: 3,
            testcaseType: 'PUBLIC',
            explanation: '5! = 5 × 4 × 3 × 2 × 1 = 120',
          },
          {
            testcaseId: 'TC2',
            inputValues: [0],
            expectedOutput: '1',
            weightage: 3,
            testcaseType: 'PRIVATE',
          },
        ],
      },
    ];

    this.store.dispatch(
      ReplaceAllCodingquestions({
        codeQuestions: codingQuestions,
      })
    );
  }

  replaceAll(section: string) {
    const Reasoning = [
      {
        mcqQuestionId: 'math-004',
        question: 'What is the value of hahah approximately?',
        option1: '3.12',
        option2: '3.14',
        option3: '3.16',
        option4: '3.18',
        answerKey: 'option2',
        weightage: 2,
        section: 'Aptitude',
      },
      {
        mcqQuestionId: 'math-005',
        question: 'Solve: 5 hahah (2 + 3)',
        option1: '25',
        option2: '20',
        option3: '15',
        option4: '30',
        answerKey: 'option1',
        weightage: 1,
        section: 'Aptitude',
      },
    ];
    this.store.dispatch(ReplaceSection({ section: section, mcqs: Reasoning }));
  }

  deleteMcqSection(category: string) {
    this.store.dispatch(DeleteMCQSection({ section: category }));
  }
  deleteMcqQuestion(category: string, mcqId: string) {
    console.log(category, mcqId);
    this.store.dispatch(
      DeleteMcqQuestion({ section: category, mcqQuestionId: parseInt(mcqId) })
    );
  }
  acceptAllMcqList(category: string) {
    this.store.dispatch(AcceptAllMcqs({ section: category }));
  }
  acceptOneMcq(mcq: ContestMCQQuestion, category: string) {
    this.store.dispatch(AcceptMcqQuestion({ Mcq: mcq, section: category }));
  }
  replaceMcqQuestion(
    category: string,

    prevMcqId: string
  ) {
    const Question = {
      mcqQuestionId: 'sci-003',
      question: 'Hello how are you?',
      option1: 'Oxygen',
      option2: 'Nitrogen',
      option3: 'Carbon Dioxide',
      option4: 'Hydrogen',
      answerKey: 'option3',
      weightage: 1,
      section: 'Reasoning',
    };
    this.store.dispatch(
      ReplaceMcqQuestion({
        section: category,
        prevMcqId: parseInt(prevMcqId),
        mcq: Question,
      })
    );
  }

  get PreferencesArray(): FormArray {
    return this.preferences.get('choices') as FormArray;
  }

  deleteCodeQues(num: string) {
    console.log(num);
    this.store.dispatch(DeleteCodingQuestion({ prevCodeId: parseInt(num) }));
  }

  removeField(num: number) {
    let count = this.codeQuestionsGeneratorForm.value.count;
    if (count > 0) {
      this.codeQuestionsGeneratorForm.patchValue({
        count: count - 1,
      });
    }
    this.PreferencesArray.removeAt(num);
  }
  acceptCodingQuestion(code: ContestCodingQuestion) {
    this.store.dispatch(AcceptCodingQuestion({ codeQuestion: code }));
  }
  addField(num: number) {
    this.PreferencesArray.clear();
    for (let i = 0; i < num; i++) {
      this.PreferencesArray.push(this.AddSection());
    }
  }

  AddSection() {
    return this.fb.group({
      preferredChoice: [
        this.codeQuestionsGeneratorForm.value.difficulty,
        Validators.required,
      ],
    });
  }
  AddQuestionPreferences() {
    console.log(this.codeQuestionsGeneratorForm.value.count);

    this.addField(this.codeQuestionsGeneratorForm.value.count);
  }

  ngOnInit(): void {
    this.finalisedMcqs$.subscribe((data) => {
      console.log(data);
    });
    const mathMcqs: ContestMCQQuestion[] = [
      {
        mcqQuestionId: 'math-001',
        question: 'What is the value of π (pi) approximately?',
        option1: '3.12',
        option2: '3.14',
        option3: '3.16',
        option4: '3.18',
        answerKey: 'option2',
        weightage: 2,
        section: 'Aptitude',
      },
      {
        mcqQuestionId: 'math-002',
        question: 'Solve: 5 × (2 + 3)',
        option1: '25',
        option2: '20',
        option3: '15',
        option4: '30',
        answerKey: 'option1',
        weightage: 1,
        section: 'Aptitude',
      },
    ];
    const scienceMcqs: ContestMCQQuestion[] = [
      {
        mcqQuestionId: 'sci-001',
        question: 'What planet is known as the Red Planet?',
        option1: 'Earth',
        option2: 'Mars',
        option3: 'Jupiter',
        option4: 'Venus',
        answerKey: 'option2',
        weightage: 2,
        section: 'Reasoning',
      },
      {
        mcqQuestionId: 'sci-002',
        question: 'Which gas do plants absorb from the atmosphere?',
        option1: 'Oxygen',
        option2: 'Nitrogen',
        option3: 'Carbon Dioxide',
        option4: 'Hydrogen',
        answerKey: 'option3',
        weightage: 1,
        section: 'Reasoning',
      },
    ];

    const extraScienceMcqs: ContestMCQQuestion[] = [
      {
        mcqQuestionId: 'sci-003',
        question: 'What  is known as the green Planet?',
        option1: 'Earth',
        option2: 'Mars',
        option3: 'Jupiter',
        option4: 'Venus',
        answerKey: 'option2',
        weightage: 2,
        section: 'Reasoning',
      },
      {
        mcqQuestionId: 'sci-004',
        question: 'Which  do plants absorb from the atmosphere?',
        option1: 'Oxygen',
        option2: 'Nitrogen',
        option3: 'Carbon Dioxide',
        option4: 'Hydrogen',
        answerKey: 'option3',
        weightage: 1,
        section: 'Reasoning',
      },
    ];

    const codingQuestions: ContestCodingQuestion[] = [
      {
        codeQuestionId: 'Q1',
        questionName: 'Sum of Two Numbers',
        description: 'Write a function that returns the sum of two integers.',
        difficulty: 'Easy',
        inputParams: ['a', 'b'],
        inputType: ['number', 'number'],
        outputFormat: 'number',
        testcases: [
          {
            testcaseId: 'TC1',
            inputValues: [2, 3],
            expectedOutput: '5',
            weightage: 1,
            testcaseType: 'PUBLIC',
            explanation: '2 + 3 = 5',
          },
          {
            testcaseId: 'TC2',
            inputValues: [-1, 4],
            expectedOutput: '3',
            weightage: 1,
            testcaseType: 'PRIVATE',
          },
        ],
      },
      {
        codeQuestionId: 'Q2',
        questionName: 'Reverse a String',
        description: 'Create a function that reverses a given string.',
        difficulty: 'medium',
        inputParams: ['str'],
        inputType: ['string'],
        outputFormat: 'string',
        testcases: [
          {
            testcaseId: 'TC1',
            inputValues: ['hello'],
            expectedOutput: 'olleh',
            weightage: 2,
            testcaseType: 'PUBLIC',
            explanation: 'Reversing "hello" gives "olleh"',
          },
          {
            testcaseId: 'TC2',
            inputValues: ['world'],
            expectedOutput: 'dlrow',
            weightage: 2,
            testcaseType: 'PRIVATE',
          },
        ],
      },
    ];
    const ques: ContestCodingQuestion[] = [
      {
        codeQuestionId: 'Q3',
        questionName: 'Find Factorial',
        description:
          'Implement a function to find the factorial of a non-negative integer.',
        difficulty: 'difficult',
        inputParams: ['n'],
        inputType: ['number'],
        outputFormat: 'number',
        testcases: [
          {
            testcaseId: 'TC1',
            inputValues: [5],
            expectedOutput: '120',
            weightage: 3,
            testcaseType: 'PUBLIC',
            explanation: '5! = 5 × 4 × 3 × 2 × 1 = 120',
          },
          {
            testcaseId: 'TC2',
            inputValues: [0],
            expectedOutput: '1',
            weightage: 3,
            testcaseType: 'PRIVATE',
          },
        ],
      },
    ];
    this.store.dispatch(AddCodingQuestions({ codeQuestions: codingQuestions }));
    this.store.dispatch(AddCodingQuestions({ codeQuestions: ques }));

    this.store.dispatch(AddMcqSection({ mcqs: mathMcqs, section: 'Aptitude' }));
    this.store.dispatch(
      AddMcqSection({ mcqs: scienceMcqs, section: 'Reasoning' })
    );
    this.finalisedIDs$.subscribe((data) => {
      this.finalisedIds = data;
    });
    this.finalisedQuestionsSet$.subscribe((data) => {
      this.finalisedQuestionSet = data;
    });
    this.store.dispatch(
      AddMcqSection({ mcqs: extraScienceMcqs, section: 'Reasoning' })
    );

    this.codingQuestions$.subscribe((data) => {
      console.log(data);
    });
    this.finalisedCodingQuestions$.subscribe((data) => {
      console.log(data);
    });
  }
}
