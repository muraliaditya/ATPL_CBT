import {
  ChangeDetectorRef,
  Component,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { McqQuestions } from '../../../models/test/questions';
import { CommonModule } from '@angular/common';
import { RadioButton } from 'primeng/radiobutton';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-mcq-section',
  imports: [CommonModule, RadioButton, FormsModule],
  templateUrl: './mcq-section.html',
  styleUrl: './mcq-section.css',
})
export class McqSection implements OnInit {
  @Input() questions: McqQuestions[] = [];
  currentQuestionNo: number = -1;
  selectedOption: string = '';

  markUserAnswer() {
    let answers: any = localStorage.getItem('mcq-answers');
    let selectedMcqAnswers;
    if (answers) {
      selectedMcqAnswers = JSON.parse(answers);
      console.log(answers);
      let index = this.questions[this.currentQuestionNo].mcqId;
      console.log(index);
      if (selectedMcqAnswers[index]) {
        this.selectedOption = selectedMcqAnswers[index];
      }
    }
  }

  constructor(private cdr: ChangeDetectorRef) {}

  goToNextQuestion() {
    this.currentQuestionNumber = this.currentQuestionNo + 1;
  }
  goToPrevQuestion() {
    this.currentQuestionNumber = this.currentQuestionNo - 1;
  }

  getCurrentQuestion(): McqQuestions | undefined {
    return this.questions[this.currentQuestionNo];
  }

  set currentQuestionNumber(value: number) {
    if (this.currentQuestionNo !== value) {
      this.currentQuestionNo = value;
      this.markUserAnswer();
    }
  }

  onChange(value: string) {
    console.log(value);
    let answers: any = localStorage.getItem('mcq-answers');
    let selectedMcqAnswers;
    if (answers) {
      selectedMcqAnswers = JSON.parse(answers);
      selectedMcqAnswers = {
        ...selectedMcqAnswers,

        [this.questions[this.currentQuestionNo].mcqId]: value,
      };
      localStorage.setItem('mcq-answers', JSON.stringify(selectedMcqAnswers));
    } else {
      localStorage.setItem(
        'mcq-answers',
        JSON.stringify({
          [this.questions[this.currentQuestionNo].mcqId]: value,
        })
      );
    }
  }
  changeQuestionNumber(num: number) {
    this.currentQuestionNumber = num;
  }
  ngOnInit(): void {
    this.currentQuestionNo = 0;
  }
}
