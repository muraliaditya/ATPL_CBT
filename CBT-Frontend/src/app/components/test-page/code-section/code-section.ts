import {
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
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
export class CodeSection implements OnInit, OnChanges {
  language: string = 'python';
  currentSelectedTab: 'TestCases' | 'Test Results' = 'TestCases';
  @ViewChild(MonacoEditor) codeEditor!: MonacoEditor;

  currentCode = `

   `;
  @Input() currentQuestionNo: number = -1;
  @Input() questions: CodingQuestions[] = [];

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['currentQuestionNo']) {
      this.setBoilerPlateCode();
    }
  }

  findTestCaseResult(testcaseId: string) {
    if (this.codingResponse[this.currentQuestionNo]) {
      let questionResult = this.codingResponse[this.currentQuestionNo];
      let testcaseFound = questionResult.publicTestcaseResults.find(
        (testcase) => testcase.testcaseId === testcaseId
      );
      if (testcaseFound && testcaseFound.status === 'PASSED') {
        return true;
      }
    }
    return false;
  }
  findTestCase(testcaseId: string) {
    if (this.codingResponse[this.currentQuestionNo]) {
      let questionResult = this.codingResponse[this.currentQuestionNo];
      let testcaseFound = questionResult.publicTestcaseResults.find(
        (testcase) => testcase.testcaseId === testcaseId
      );
      return testcaseFound;
    }
    return null;
  }

  checkIfCodingReponseExist(): CodeExecutionResponse | undefined {
    return this.codingResponse[this.currentQuestionNo];
  }

  getCurrentCodeStatus() {
    return this.codingResponse[this.currentQuestionNo].codeStatus;
  }
  changeTab(val: 'TestCases' | 'Test Results') {
    this.currentSelectedTab = val;
  }

  showErrorMessage() {
    let status = this.getCurrentCodeStatus();
    if (status === 'COMPILATION_ERROR') {
      return 'Compilation Error :(';
    } else if (status === 'RUNTIME_ERROR') {
      return 'Runtime Error :(';
    }
    return null;
  }

  codingResponse: Record<number, CodeExecutionResponse> = {
    0: {
      codeStatus: 'WRONG ANSWER',
      message: 'Compiled and executed successfully',
      publicTestcasePassed: 1,
      publicTestcaseResults: [
        {
          testcaseId: 't11',
          input: ['2 3'],
          expectedOutput: '5',
          actualOutput: '5',
          status: 'PASSED',
          weightage: 2,
        },
        {
          testcaseId: 't22',
          input: ['10 20'],
          expectedOutput: '30',
          actualOutput: '25',
          status: 'FAILED',
          weightage: 3,
        },
        {
          testcaseId: 't33',
          input: ['10 20'],
          expectedOutput: '30',
          actualOutput: '25',
          status: 'FAILED',
          weightage: 3,
        },
      ],
    },
    1: {
      codeStatus: 'COMPILATION_ERROR',
      message: 'Compiled and executed successfully',
      publicTestcasePassed: 2,
      privateTestcasePassed: 2,
      publicTestcaseResults: [
        {
          testcaseId: 't44',
          input: ['2 3'],
          expectedOutput: '5',
          actualOutput: '5',
          status: 'PASSED',
          weightage: 2,
        },
        {
          testcaseId: 't55',
          input: ['10 20'],
          expectedOutput: '30',
          actualOutput: '30',
          status: 'PASSED',
          weightage: 3,
        },
      ],
      privateTestcaseResults: [
        {
          testcaseId: 't77',
          status: 'PASSED',
          weightage: 2,
        },
        {
          testcaseId: 't88',
          status: 'PASSED',
          weightage: 3,
        },
      ],
    },
  };

  languagesSupported: string[] = ['python', 'java'];
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
  changeLanguage(val: string) {
    this.language = val;
    this.codeEditor.changeEditorOptions(val);

    this.setBoilerPlateCode();
  }
  codeChange(value: Event) {
    console.log(value);
    let index = `${this.currentQuestionNo}-${
      this.getCurrentQuestion()?.codingQuestionId
    }-${this.language}`;
    let codes = localStorage.getItem('codes');
    let parsedData = codes ? JSON.parse(codes) : {};
    parsedData[index] = value.toString();
    localStorage.setItem('codes', JSON.stringify(parsedData));
  }

  getCurrentCode() {
    let index = `${this.currentQuestionNo}-${
      this.getCurrentQuestion()?.codingQuestionId
    }-${this.language}`;
    let codes = localStorage.getItem('codes');
    let parsedData = codes ? JSON.parse(codes) : {};
    console.log(parsedData);
    if (parsedData[index]) {
      return parsedData[index];
    }
    return null;
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
  setBoilerPlateCode() {
    if (this.language === 'python') {
      this.currentCode = this.getCurrentCode()
        ? this.getCurrentCode()
        : this.getCurrentQuestion()?.pythonBoilerCode ?? '';
    } else if (this.language === 'java') {
      this.currentCode = this.getCurrentCode()
        ? this.getCurrentCode()
        : this.getCurrentQuestion()?.javaBoilerCode ?? '';
    }
  }
  ngOnInit(): void {
    this.setCurrentTestCases(this.getCurrentQuestion());
    this.showFirstTestCase(this.getCurrentQuestion());
    this.setBoilerPlateCode();
  }
}
