import { Component, Input, OnInit } from '@angular/core';
import { SplitterModule } from 'primeng/splitter';
import { CardModule } from 'primeng/card';
import { PanelModule } from 'primeng/panel';
import { MonacoEditor } from '../../UI/monoco-editor/monoco-editor';
import { CodingQuestions, Testcase } from '../../../models/test/questions';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-code-section',
  imports: [
    MonacoEditor,
    PanelModule,
    CardModule,
    SplitterModule,
    CommonModule,
  ],
  templateUrl: './code-section.html',
  styleUrl: './code-section.css',
})
export class CodeSection implements OnInit {
  currentQuestionNo: number = 0;
  @Input() questions: CodingQuestions[] = [];
  currentTestCases: Testcase[] = [];

  getToQuestionNo(questionNo: number) {
    if (questionNo > -1 && questionNo < this.questions.length - 1) {
      this.currentQuestionNo = questionNo;
    }
  }

  getCurrentQuestion(): CodingQuestions | undefined {
    return this.questions[this.currentQuestionNo];
  }

  setCurrentTestCases(initialQuestion?: CodingQuestions) {
    if (initialQuestion?.testcases.length) {
      console.log(initialQuestion);
      console.log(
        [...initialQuestion.testcases].filter((testcase) => testcase.desc)
      );
      this.currentTestCases = [...initialQuestion.testcases].filter(
        (testcase) => testcase.desc
      );
    } else {
      this.currentTestCases = [];
    }
  }
  ngOnInit(): void {
    this.setCurrentTestCases(this.getCurrentQuestion());
  }
}
