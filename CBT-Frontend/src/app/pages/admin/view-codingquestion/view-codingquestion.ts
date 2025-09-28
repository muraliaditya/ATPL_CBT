import { Component } from '@angular/core';
import {
  ContestCodingQuestion,
  ContestTestcase,
} from '../../../models/admin/contest';
import { CommonModule } from '@angular/common';
import { DynamicLayout } from '../../../components/UI/dynamic-layout/dynamic-layout';
import { TestcaseFilterPipe } from '../../../pipes/testcase-filter-pipe';

@Component({
  selector: 'app-view-codingquestion',
  imports: [CommonModule, DynamicLayout, TestcaseFilterPipe],
  templateUrl: './view-codingquestion.html',
  styleUrl: './view-codingquestion.css',
})
export class ViewCodingquestion {
  codingQuestion: ContestCodingQuestion = {
    codeQuestionId: 'code001',
    questionName: 'Reverse a string',
    difficulty: 'Easy',
    description:
      'Write a function that takes a string and returns it reversed.',

    inputParams: ['str', 'str2'],
    inputType: ['string'],
    outputFormat: 'string',
    testcases: [
      {
        testcaseId: 'tc1',
        inputValues: ['hello', 'haha'],
        expectedOutput: 'olleh',
        weightage: 1,
        testcaseType: 'PUBLIC',
        explanation: ' The only possible triplet does not sum up to 0.',
      },
      {
        testcaseId: 'tc2',
        inputValues: ['world', 'hahah'],
        expectedOutput: 'dlrow',
        weightage: 1,
        testcaseType: 'PUBLIC',
      },
    ],
  };
  filterTestCase(testCase: ContestTestcase): boolean {
    if (!testCase.explanation) {
      return false;
    }
    if (testCase.explanation.length && testCase.testcaseType === 'PUBLIC') {
      return true;
    }
    return false;
  }
}
