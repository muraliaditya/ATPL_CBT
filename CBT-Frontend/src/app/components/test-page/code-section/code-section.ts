import { Component, Input, OnInit } from '@angular/core';
import { SplitterModule } from 'primeng/splitter';
import { CardModule } from 'primeng/card';
import { PanelModule } from 'primeng/panel';
import { MonacoEditor } from '../../UI/monoco-editor/monoco-editor';
import { CodingQuestions, Testcase } from '../../../models/test/questions';
import { CommonModule } from '@angular/common';
import { Select } from 'primeng/select';
import { FormsModule } from '@angular/forms';

interface languageCodes {
  name: string;
  code: string;
}

@Component({
  selector: 'app-code-section',
  imports: [
    MonacoEditor,
    PanelModule,
    CardModule,
    SplitterModule,
    CommonModule,
    Select,
    FormsModule,
  ],
  templateUrl: './code-section.html',
  styleUrl: './code-section.css',
})
export class CodeSection implements OnInit {
  language: string = 'cpp';

  currentCode = `
  // C++ program to check if the number is even
// or odd using modulo operator
#include <bits/stdc++.h>
using namespace std;

int main() {
    int n = 11;

    // If n is completely divisible by 2
    if (n % 2 == 0)
        cout << "Even";

    // If n is NOT completely divisible by 2
    else
        cout << "Odd";
    return 0;
}
   `;
  @Input() currentQuestionNo: number = -1;
  @Input() questions: CodingQuestions[] = [];
  languagesSupported: string[] = ['cpp', 'python', 'java', 'javascript'];
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
      this.currentTestCases = [...initialQuestion.testcases].filter(
        (testcase) => testcase.explanation?.trim()
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
