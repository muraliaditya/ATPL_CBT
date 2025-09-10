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
  language: string = 'cpp';
  @Input() currentQuestionNo: number = 0;
  @Input() questions: CodingQuestions[] = [];
  currentTestCases: Testcase[] = [];
  viewCurrentTestCase: Testcase | null = null;

  getToQuestionNo(questionNo: number) {
    if (questionNo > -1 && questionNo < this.questions.length - 1) {
      this.currentQuestionNo = questionNo;
    }
  }

  changeQuestion(testcase: Testcase) {
    if (testcase) {
      this.viewCurrentTestCase = testcase;
    }
  }

  getCurrentQuestion(): CodingQuestions | undefined {
    return this.questions[this.currentQuestionNo];
  }

  getQuestionAtIndex(idx: number): CodingQuestions | undefined {
    return this.questions[idx];
  }

  setCurrentTestCases(initialQuestion?: CodingQuestions) {
    if (initialQuestion?.testcases.length) {
      console.log(initialQuestion);
      console.log(
        [...initialQuestion.testcases].filter(
          (testcase) => testcase.explanation
        )
      );
      this.currentTestCases = [...initialQuestion.testcases].filter(
        (testcase) => testcase.explanation
      );
    } else {
      this.currentTestCases = [];
    }
  }
  showFirstTestCase(initialQuestion?: CodingQuestions) {
    if (initialQuestion?.testcases.length) {
      this.viewCurrentTestCase = initialQuestion.testcases[0];
    } else {
      this.viewCurrentTestCase = null;
    }
  }
  ngOnInit(): void {
    this.setCurrentTestCases(this.getCurrentQuestion());
    this.showFirstTestCase(this.getCurrentQuestion());
  }
}
