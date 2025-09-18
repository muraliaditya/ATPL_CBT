import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { AdminHeader } from '../../../components/UI/admin-header/admin-header';
import { CommonModule } from '@angular/common';
import { AppState } from '../../../store/main-store/app.state';
import { ContestMCQQuestion } from '../../../models/admin/contest';
import {
  AddMcqSection,
  DeleteMcqQuestion,
  DeleteMCQSection,
  ReplaceMcqQuestion,
  AcceptAllMcqs,
  AcceptMcqQuestion,
  ReplaceSection,
} from '../../../store/sub-stores/contest/contest.actions';
import { FormArray, ReactiveFormsModule } from '@angular/forms';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  Validators,
} from '@angular/forms';
import { Select } from 'primeng/select';

import { FloatLabel } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';

import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
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
  ],
  templateUrl: './create-contest.html',
  styleUrl: './create-contest.css',
})
export class CreateContest implements OnInit {
  codeQuestionsGeneratorForm: FormGroup;
  preferences: FormGroup;
  McqList$: Observable<Record<string, Record<number, ContestMCQQuestion>>>;
  finalisedIDs$: Observable<string[]>;
  finalisedIds: string[] = [];
  finalisedMcqs$: Observable<Record<string, ContestMCQQuestion[]>>;
  difficultyOptions: string[] = ['Easy', 'Medium', 'Difficult'];

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
    this.codeQuestionsGeneratorForm = this.fb.group({
      count: ['', [Validators.required, Validators.min(1)]],
      marks: ['', [Validators.required, Validators.min(10)]],
      difficulty: ['', Validators.required],
    });
    this.preferences = this.fb.group({
      choices: this.fb.array([]),
    });
  }

  checkIsSectionFinalised(
    sectionData: Record<number, ContestMCQQuestion>
  ): boolean {
    let questionKeys = Object.keys(sectionData);
    let questionMcqs = questionKeys.map(
      (key) => sectionData[Number(key)].mcqQuestionId
    );
    return questionMcqs.every((id) => this.finalisedIds?.includes(id));
  }
  currentSection: 'Mcqs' | 'Coding' = 'Mcqs';
  changeSection(value: 'Mcqs' | 'Coding') {
    this.currentSection = value;
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

  removeField(num: number) {
    let count = this.codeQuestionsGeneratorForm.value.count;
    if (count > 0) {
      this.codeQuestionsGeneratorForm.patchValue({
        count: count - 1,
      });
    }
    this.PreferencesArray.removeAt(num);
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
    this.store.dispatch(AddMcqSection({ mcqs: mathMcqs, section: 'Aptitude' }));
    this.store.dispatch(
      AddMcqSection({ mcqs: scienceMcqs, section: 'Reasoning' })
    );
    this.finalisedIDs$.subscribe((data) => {
      this.finalisedIds = data;
    });
    this.store.dispatch(
      AddMcqSection({ mcqs: extraScienceMcqs, section: 'Reasoning' })
    );
  }
}
