import { Component } from '@angular/core';
import { AppState } from '../../stores/main-store/app.state';
import { Store } from '@ngrx/store';
import { McqQuestions } from '../../models/test/questions';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import {
  AddToList,
  ChangeList,
} from '../../stores/sub-stores/contest-store/contest.actions';
@Component({
  selector: 'app-practice',
  imports: [CommonModule],
  templateUrl: './practice.html',
  styleUrl: './practice.css',
})
export class Practice {
  mcqs$: Observable<McqQuestions[]>;
  constructor(private store: Store<AppState>) {
    this.mcqs$ = this.store.select((state) => state.contest.McqQuestions);
  }
  addQuestion() {
    // create a new MCQ
    const newQ: McqQuestions = {
      mcqId: 'q1',
      question: 'What is Angular?',
      options: ['Library', 'Framework', 'Language', 'Tool'],
    };

    this.store.dispatch(AddToList({ Mcq: newQ }));
  }
  changeQuestion() {
    const newQ: McqQuestions = {
      mcqId: 'q1',
      question: 'What is Angular?',
      options: ['hi', 'Framework', 'Language', 'Tool'],
    };
    this.store.dispatch(ChangeList({ id: 'q1', Mcq: newQ }));
  }
}
