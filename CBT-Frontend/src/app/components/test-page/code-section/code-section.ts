import { Component, Input, OnInit, ViewChild, viewChild } from '@angular/core';
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
  language: string = 'python';
  @ViewChild(MonacoEditor) codeEditor!: MonacoEditor;

  currentCode = `

   `;
  @Input() currentQuestionNo: number = -1;
  @Input() questions: CodingQuestions[] = [];
  languagesSupported: string[] = ['python', 'java'];
  currentTestCases: Testcase[] = [];
  viewCurrentTestCase: Testcase | null = null;
  pythonBoilerCode = `def reverse_string_slicing(s):
    return s[::-1]

original_string = "hello"
reversed_string = reverse_string_slicing(original_string)
print(f"Original: {original_string}, Reversed: {reversed_string}")`;
  javaBoilerCode = `public class ReverseStringUsingForLoop {
    public static void main(String[] args) {
        String originalString = "Programming";
        String reversedString = "";

        for (int i = originalString.length() - 1; i >= 0; i--) {
            reversedString += originalString.charAt(i);
        }

        System.out.println("Original String: " + originalString);
        System.out.println("Reversed String: " + reversedString);
    }
}`;

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
      this.currentCode = this.getCurrentQuestion()?.pythonBoilerCode ?? '';
    } else if (this.language === 'java') {
      this.currentCode = this.getCurrentQuestion()?.javaBoilerCode ?? '';
    }
  }
  ngOnInit(): void {
    this.setCurrentTestCases(this.getCurrentQuestion());
    this.showFirstTestCase(this.getCurrentQuestion());
    this.setBoilerPlateCode();
  }
}
