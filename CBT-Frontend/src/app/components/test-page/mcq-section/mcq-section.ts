import { Component, Input, OnInit } from '@angular/core';
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

  goToNextQuestion() {
    this.currentQuestionNo = this.currentQuestionNo + 1;
  }
  goToPrevQuestion() {
    this.currentQuestionNo = this.currentQuestionNo - 1;
  }

  getCurrentQuestion(): McqQuestions | undefined {
    return this.questions[this.currentQuestionNo];
  }

  onChange(value: string) {
    console.log(value);
    let answers: any = localStorage.getItem('mcq-answers');
    let selectedMcqAnswers;
    if (answers) {
      selectedMcqAnswers = JSON.parse(answers);
      console.log(selectedMcqAnswers);
      selectedMcqAnswers = {
        ...selectedMcqAnswers,

        [this.questions[this.currentQuestionNo].mcqId]: {
          ...this.questions[this.currentQuestionNo],
          selectedOption: value,
        },
      };
      localStorage.setItem('mcq-answers', JSON.stringify(selectedMcqAnswers));
    } else {
      localStorage.setItem(
        'mcq-answers',
        JSON.stringify({
          [this.questions[this.currentQuestionNo].mcqId]: {
            ...this.questions[this.currentQuestionNo],
            selectedOption: value,
          },
        })
      );
    }
  }
  changeQuestionNumber(num: number) {
    this.currentQuestionNo = num;
  }
  ngOnInit(): void {
    this.currentQuestionNo = 0;
  }
}
