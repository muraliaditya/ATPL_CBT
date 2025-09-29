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
  DeleteCodingSection,
  changeMcqWeightage,
  changeCodeWeightage,
  acceptIdsIntoRegenerateIds,
} from '../../../store/sub-stores/contest/contest.actions';
import { debounceTime, distinctUntilChanged, filter } from 'rxjs/operators';

import { FormArray, FormControl, ReactiveFormsModule } from '@angular/forms';
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
import { ContestTestcase } from '../../../models/admin/contest';
import { DynamicLayout } from '../../../components/UI/dynamic-layout/dynamic-layout';
import { TestcaseFilterPipe } from '../../../pipes/testcase-filter-pipe';

@Component({
  selector: 'app-edit-contest',
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
    DynamicLayout,
    TestcaseFilterPipe,
  ],
  templateUrl: './edit-contest.html',
  styleUrl: './edit-contest.css',
})
export class EditContest {
  codeQuestionsGeneratorForm: FormGroup;
  codingWeightageForm: FormGroup;
  totalMarks: number = 0;
  minEndDate: Date = new Date();

  totalMcqMarks: number = 0;
  totalcodingMarks: number = 0;
  today: Date = new Date();

  mcqQuestionGenerateForm: FormGroup;

  eligibilityOptions: string[] = [
    'Student',
    'Priliminary',
    'Intermediate',
    'Advanced',
  ];
  McqOptions: string[] = ['Aptitude', 'Reasoning', 'Quant'];
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

  codingQuestions$: Observable<Record<number, ContestCodingQuestion>>;
  sectionWeightageForm: FormGroup;

  preferences: FormGroup;
  contestDetailsForm: FormGroup;
  McqList$: Observable<Record<string, Record<number, ContestMCQQuestion>>>;
  finalisedIDs$: Observable<string[]>;
  finalisedIds: string[] = [];
  finalisedMcqs$: Observable<Record<string, ContestMCQQuestion[]>>;
  difficultyOptions: string[] = ['Easy', 'Medium', 'Difficult'];
  finalisedQuestionsSet$: Observable<Set<string>>;
  finalisedQuestionSet: Set<string> = new Set();
  finalisedCodingQuestions$: Observable<ContestCodingQuestion[]>;
  regenerateIds$: Observable<Set<string>>;
  regenerateIds: Set<string> = new Set();

  constructor(private store: Store<AppState>, private fb: FormBuilder) {
    this.contestDetailsForm = this.fb.group({
      contestName: ['', [Validators.required]],
      eligibility: ['', [Validators.required]],
      active: [false, [Validators.required]],
      startTime: [null, [Validators.required]],
      endTime: [null, [Validators.required]],
      duration: ['', [Validators.required, Validators.min(1)]],
    });
    this.sectionWeightageForm = this.fb.group({});
    this.codingWeightageForm = this.fb.group({});

    this.McqList$ = this.store.select(
      (state) => state.contest.tempMcqQuestions
    );
    this.regenerateIds$ = this.store.select(
      (state) => state.contest.regenerateOnceListIds
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
    this.mcqQuestionGenerateForm = this.fb.group({
      mcqSection: ['', Validators.required],
      count: ['', [Validators.required, Validators.min(1)]],
      marks: ['', [Validators.required]],
    });
    this.preferences = this.fb.group({
      choices: this.fb.array([]),
    });
    this.codingQuestions$ = this.store.select(
      (state) => state.contest.tempcodingQuestions
    );
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
  checkIsSectionRegeneratable(
    sectionData: Record<number, ContestMCQQuestion>
  ): boolean {
    let questionKeys = Object.keys(sectionData);
    let questionMcqs = questionKeys.map(
      (key) => sectionData[Number(key)].mcqQuestionId
    );
    console.log(questionMcqs);
    return questionMcqs.some((id) => this.regenerateIds.has(id));
  }

  checkIsCodingSectionRegeneratable(
    sectionData: Record<number, ContestCodingQuestion>
  ): boolean {
    let questionKeys = Object.keys(sectionData);
    let questionMcqs = questionKeys.map(
      (key) => sectionData[Number(key)].codeQuestionId
    );

    return questionMcqs.some((id) => this.regenerateIds.has(id));
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
  checkIsCodingSectionFinalised(
    sectionData: Record<number, ContestCodingQuestion>
  ): boolean {
    let questionKeys = Object.keys(sectionData);
    let questionMcqs = questionKeys.map(
      (key) => sectionData[Number(key)].codeQuestionId
    );
    return questionMcqs.every((id) => this.finalisedQuestionSet.has(id));
  }
  currentSection: 'Mcqs' | 'Coding' = 'Mcqs';
  changeSection(value: 'Mcqs' | 'Coding') {
    this.currentSection = value;
  }

  onStartTimeChange(startTime: Date) {
    if (startTime) {
      this.minEndDate = new Date(startTime);
    } else {
      this.minEndDate = new Date(this.today);
    }
  }

  getCodingQuestionControl(questionKey: string): FormGroup {
    let controlGroup = this.codingWeightageForm.get(questionKey) as FormGroup;

    if (!controlGroup) {
      controlGroup = this.fb.group({
        weightage: [0, [Validators.required, Validators.min(1)]],
        difficulty: ['Easy', [Validators.required]],
      });
      this.codingWeightageForm.addControl(questionKey, controlGroup);
    }

    return controlGroup;
  }

  onCodingDifficultyChange(questionKey: string, event: any) {
    const value = event.value;
    console.log(`Difficulty changed for question ${questionKey}:`, value);
  }

  onCodingWeightageChange(questionKey: string, event: Event) {
    const inputElement = event.target as HTMLInputElement;
    const value = Number(inputElement.value);

    const controlGroup = this.getCodingQuestionControl(questionKey);
    const weightageControl = controlGroup.get('weightage');

    if (weightageControl && weightageControl.valid) {
      this.store.dispatch(
        changeCodeWeightage({
          codingQuestionId: Number(questionKey),
          weightage: value,
        })
      );
    }
  }

  isCodingQuestionInvalid(questionKey: string): boolean {
    const control = this.codingWeightageForm.get(questionKey);
    return control ? control.invalid : false;
  }

  checkIfCodingSectionInvalid(): boolean {
    return this.codingWeightageForm.invalid;
  }

  replaceCodequestion(prevCodeId: string) {
    const questionControl = this.getCodingQuestionControl(prevCodeId);
    const currentDifficulty = questionControl.get('difficulty')?.value;

    console.log('Current question difficulty:', currentDifficulty);

    let ques: ContestCodingQuestion = {
      codeQuestionId: 'Q5',
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
    this.store.dispatch(AcceptAllCodingQuestions());
  }
  trackByCodingQuestion(index: number, item: any) {
    return `coding-${item.key}-${index}`;
  }
  deleteCodingSection() {
    this.store.dispatch(DeleteCodingSection());
    this.codingWeightageForm.reset();
    this.codingWeightageForm = this.fb.group({});
  }
  replaceCodingSection() {
    const allPreferences: { [key: string]: string } = {};

    Object.keys(this.codingWeightageForm.controls).forEach((questionKey) => {
      const questionControl = this.codingWeightageForm.get(
        questionKey
      ) as FormGroup;
      allPreferences[questionKey] = questionControl.get('difficulty')?.value;
    });

    console.log('All section preferences:', allPreferences);

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
  generateMcqSection() {
    console.log(this.mcqQuestionGenerateForm.value);
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
    if (this.sectionWeightageForm.contains(category)) {
      this.sectionWeightageForm.removeControl(category);
    }
    console.log(this.sectionWeightageForm.controls);
  }
  deleteMcqQuestion(category: string, mcqId: string) {
    console.log(category, mcqId);
    this.store.dispatch(
      DeleteMcqQuestion({ section: category, mcqQuestionId: parseInt(mcqId) })
    );

    const sectionGroup = this.sectionWeightageForm.get(category) as FormGroup;

    if (!sectionGroup) return;

    const questionCount = Object.keys(sectionGroup.controls).length;

    if (questionCount === 1) {
      this.sectionWeightageForm.removeControl(category);
    } else if (sectionGroup.contains(mcqId)) {
      sectionGroup.removeControl(mcqId);
    }
    console.log(this.sectionWeightageForm.controls);
  }
  acceptAllMcqList(category: string) {
    this.store.dispatch(AcceptAllMcqs({ section: category }));
  }
  acceptOneMcq(mcq: ContestMCQQuestion, category: string) {
    this.store.dispatch(AcceptMcqQuestion({ Mcq: mcq, section: category }));
  }
  replaceMcqQuestion(category: string, prevMcqId: string) {
    let questionId = Math.floor(Math.random() * 100);
    const Question = {
      mcqQuestionId: 'sci-009' + questionId,
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

    if (this.codingWeightageForm.contains(num)) {
      this.codingWeightageForm.removeControl(num);
    }
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
  calculateTotalMcqMarks(mcqsQues: Record<string, ContestMCQQuestion[]>) {
    let keys = Object.keys(mcqsQues);
    let totalMarks = 0;
    for (let key of keys) {
      let sectionData = mcqsQues[key];
      totalMarks = sectionData.reduce(
        (sum, ques) => sum + ques.weightage,
        totalMarks
      );
    }
    this.totalMcqMarks = totalMarks;
  }
  AddQuestionPreferences() {
    console.log(this.codeQuestionsGeneratorForm.value.count);

    this.addField(this.codeQuestionsGeneratorForm.value.count);
  }
  getQuestionControl(sectionKey: string, questionKey: string): FormControl {
    const sectionGroup = this.sectionWeightageForm.get(sectionKey) as FormGroup;

    if (!sectionGroup) {
      const newGroup = this.fb.group({});
      this.sectionWeightageForm.addControl(sectionKey, newGroup);
      return this.getQuestionControl(sectionKey, questionKey);
    }

    let control = sectionGroup.get(questionKey) as FormControl;

    if (!control) {
      control = this.fb.control(0, [Validators.required, Validators.min(1)]);
      sectionGroup.addControl(questionKey, control);
    }

    return control;
  }
  weightageOptions = [
    { label: '2', value: 2 },
    { label: '3', value: 3 },
    { label: '4', value: 4 },
    { label: '5', value: 5 },
  ];
  onWeightageChange(sectionKey: string, questionKey: string, event: any) {
    const value = Number(event.value);

    const control = this.getQuestionControl(sectionKey, questionKey);

    if (control.valid) {
      this.store.dispatch(
        changeMcqWeightage({
          section: sectionKey,
          prevMcqId: Number(questionKey),
          weightage: value,
        })
      );
    }
  }

  isQuestionInvalid(section: string, questionKey: string): boolean {
    const sectionGroup = this.sectionWeightageForm.get(section) as FormGroup;
    if (!sectionGroup) return false;

    const control = sectionGroup.get(questionKey);
    return control ? control.invalid : false;
  }

  checkIfSectionInvalid(section: string): boolean {
    const sectionGroup = this.sectionWeightageForm.get(section) as FormGroup;
    if (sectionGroup?.invalid) {
      return true;
    }
    return false;
  }
  trackByQuestion(index: number, question: any) {
    return `${question.key}-${index}`;
  }
  trackByKey(index: number, item: any) {
    return `${item.key}-${index}`;
  }
  calculateTotalCodingMarks(ques: ContestCodingQuestion[]) {
    let codingMarks = 0;
    if (ques.length) {
      codingMarks = ques.reduce((sum, q) => sum + (q.weightage || 0), 0);
      this.totalcodingMarks = codingMarks;
    } else {
      this.totalcodingMarks = 0;
    }
    console.log('coding Marks ', codingMarks);
  }
  ngOnInit(): void {
    this.finalisedMcqs$.subscribe((data) => {
      this.calculateTotalMcqMarks(data);
      console.log('finalised mcqs data', data);
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
        weightage: 0,
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
        weightage: 0,
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
        weightage: 0,
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
    this.regenerateIds$.subscribe((data) => {
      console.log('renegerate ', data);
      this.regenerateIds = data;
    });
    this.codingQuestions$.subscribe((codingData) => {
      const questionKeys = Object.keys(codingData);
      questionKeys.forEach((key) => {
        if (!this.codingWeightageForm.get(key)) {
          const question = codingData[Number(key)];
          const questionGroup = this.fb.group({
            weightage: [
              Number(question.weightage || 0),
              [Validators.required, Validators.min(1)],
            ],
            difficulty: ['Easy', [Validators.required]],
          });
          this.codingWeightageForm.addControl(key, questionGroup);
        }
      });
    });
    this.McqList$.subscribe((mcqData) => {
      for (const section of Object.keys(mcqData)) {
        let sectionGroup = this.sectionWeightageForm.get(section) as FormGroup;
        if (!sectionGroup) {
          sectionGroup = this.fb.group({});
          this.sectionWeightageForm.addControl(section, sectionGroup);
        }

        const questionKeys = Object.keys(mcqData[section]);
        questionKeys.forEach((key) => {
          if (!sectionGroup.get(key)) {
            const q = mcqData[section][Number(key)];
            sectionGroup.addControl(
              key,
              this.fb.control(Number(q.weightage), [
                Validators.required,
                Validators.min(1),
              ])
            );
          }
        });
      }
    });

    this.store.dispatch(AddCodingQuestions({ codeQuestions: codingQuestions }));

    let codeIDs: string[] = codingQuestions.map((ques) => ques.codeQuestionId);
    this.store.dispatch(acceptIdsIntoRegenerateIds({ Ids: codeIDs }));

    this.store.dispatch(AddCodingQuestions({ codeQuestions: ques }));
    codeIDs = ques.map((ques) => ques.codeQuestionId);
    this.store.dispatch(acceptIdsIntoRegenerateIds({ Ids: codeIDs }));

    this.store.dispatch(AcceptAllCodingQuestions());
    this.store.dispatch(AcceptAllCodingQuestions());

    this.store.dispatch(AddMcqSection({ mcqs: mathMcqs, section: 'Aptitude' }));
    let mcqIDs: string[] = mathMcqs.map((ques) => ques.mcqQuestionId);
    this.store.dispatch(acceptIdsIntoRegenerateIds({ Ids: mcqIDs }));
    this.store.dispatch(
      AddMcqSection({ mcqs: scienceMcqs, section: 'Reasoning' })
    );
    mcqIDs = scienceMcqs.map((ques) => ques.mcqQuestionId);
    this.store.dispatch(acceptIdsIntoRegenerateIds({ Ids: mcqIDs }));
    this.finalisedIDs$.subscribe((data) => {
      this.finalisedIds = data;
    });
    this.finalisedQuestionsSet$.subscribe((data) => {
      this.finalisedQuestionSet = data;
      console.log('finalised ques ids', data);
    });
    this.store.dispatch(
      AddMcqSection({ mcqs: extraScienceMcqs, section: 'Reasoning' })
    );
    mcqIDs = extraScienceMcqs.map((ques) => ques.mcqQuestionId);
    this.store.dispatch(acceptIdsIntoRegenerateIds({ Ids: mcqIDs }));
    this.store.dispatch(AcceptAllMcqs({ section: 'Aptitude' }));
    this.store.dispatch(AcceptAllMcqs({ section: 'Reasoning' }));

    this.codingQuestions$.subscribe((data) => {
      console.log('tempcoding Ques', data);
    });
    this.finalisedCodingQuestions$.subscribe((data) => {
      this.calculateTotalCodingMarks(data);
      console.log('finalisedcodingQuestions', data);
    });
  }
}
