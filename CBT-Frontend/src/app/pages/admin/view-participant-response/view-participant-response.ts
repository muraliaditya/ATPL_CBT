import { Component } from '@angular/core';
import {
  MCQQuestionResponse,
  CodingQuestionResponse,
  Status,
  Testcase,
} from '../../../models/admin/participant-result';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Participant } from '../../../models/admin/participant-result';
import { TestcaseFilterPipe } from '../../../pipes/testcase-filter-pipe';
import { ToggleSection } from '../../../components/UI/toggle-section/toggle-section';
import { AdminHeader } from '../../../components/UI/admin-header/admin-header';
import { CodeBlock } from '../../../components/UI/code-block/code-block';
import { DynamicLayout } from '../../../components/UI/dynamic-layout/dynamic-layout';

@Component({
  selector: 'app-view-participant-response',
  imports: [
    CommonModule,
    TestcaseFilterPipe,
    ToggleSection,
    AdminHeader,
    CodeBlock,
    DynamicLayout,
  ],
  templateUrl: './view-participant-response.html',
  styleUrl: './view-participant-response.css',
})
export class ViewParticipantResponse {
  currentSection: 'Mcqs' | 'Coding' = 'Mcqs';
  constructor(private route: ActivatedRoute) {}

  constructShowSectionCondition(idx: number) {
    if (idx === 0) {
      return true;
    } else {
      return false;
    }
  }

  constructHeader(value: string) {
    return `Question - ${value}`;
  }
  participant: Participant | null = {
    participantId: 'UD-0441',
    userName: 'John deo',
    email: 'john@gmail.com',
    college: 'LENDI',
    collegeRegdNo: '21KD1A0566',
    percentage: 88,
    codingMarks: 23,
    mcqMarks: 23,
    totalMarks: 56,
  };
  experiencedData: Participant | null = {
    participantId: 'UD-0441',
    userName: 'hello deo',
    email: 'johndeo@gmail.com',
    company: 'Maron',
    designation: 'SDE1',
    overallExperience: 10,
    codingMarks: 23,
    mcqMarks: 23,
    totalMarks: 40,
  };
  filterTestCase(testCase: Testcase): boolean {
    if (!testCase.explanation) {
      return false;
    }
    if (testCase.explanation.length && testCase.testcaseType === 'PUBLIC') {
      return true;
    }
    return false;
  }

  mcqQuestions: MCQQuestionResponse[] = [
    {
      mcqQuestionId: 'mcq001',
      question: 'What is the capital of France?',
      option1: 'Berlin',
      option2: 'Madrid',
      option3: 'Paris',
      option4: 'Rome',
      selectedAnswer: 'Paris',
      answerKey: 'Paris',
      isCorrect: true,
      weightage: 2,
      section: 'Geography',
    },
    {
      mcqQuestionId: 'mcq002',
      question: 'Which planet is known as the Red Planet?',
      option1: 'Earth',
      option2: 'Mars',
      option3: 'Jupiter',
      option4: 'Venus',
      selectedAnswer: 'Mars',
      answerKey: 'Mars',
      isCorrect: true,
      weightage: 3,
      section: 'Science',
    },
    {
      mcqQuestionId: 'mcq003',
      question: "Who wrote 'Romeo and Juliet'?",
      option1: 'Charles Dickens',
      option2: 'William Shakespeare',
      option3: 'Jane Austen',
      option4: 'Mark Twain',
      selectedAnswer: 'Charles Dickens',
      answerKey: 'William Shakespeare',
      isCorrect: false,
      weightage: 1,
      section: 'Literature',
    },
  ];
  codingQuestions: CodingQuestionResponse[] = [
    {
      codeQuestionId: 'code001',
      questionName: 'Reverse a string',
      difficulty: 'Easy',
      description:
        'Write a function that takes a string and returns it reversed.',
      submittedCode:
        "function reverse(str) { return str.split('').reverse().join(''); }",
      languageType: 'JavaScript',
      status: Status.SOLVED,
      publicTestCasesPassed: 2,
      privateTestCasesPassed: 1,
      inputParams: ['str', 'str2'],
      inputType: ['string'],
      outputFormat: 'string',
      testcases: [
        {
          testcaseId: 'tc1',
          inputValues: ['hello', 'haha'],
          expectedOutput: 'olleh',
          actualOutput: 'olleh',
          weightage: 1,
          testcaseStatus: 'PASSED',
          testcaseType: 'PUBLIC',
          explanation: ' The only possible triplet does not sum up to 0.',
        },
        {
          testcaseId: 'tc2',
          inputValues: ['world', 'hahah'],
          expectedOutput: 'dlrow',
          actualOutput: 'dlrow',
          weightage: 1,
          testcaseStatus: 'PASSED',
          testcaseType: 'PUBLIC',
        },
      ],
    },
    {
      codeQuestionId: 'code002',
      questionName: 'Check for palindrome',
      difficulty: 'difficult',
      description: 'Determine if a given string is a palindrome.',
      submittedCode: `
        function isPalindrome(str) { 
        return str === str.split('').reverse().join(''); 
        }
        function isPalindrome(str) { 
        return str === str.split('').reverse().join(''); 
        }
        function isPalindrome(str) { 
        return str === str.split('').reverse().join(''); 
        }
        function isPalindrome(str) { 
        return str === str.split('').reverse().join(''); 
        }
        function isPalindrome(str) { 
        return str === str.split('').reverse().join(''); 
        }
        function isPalindrome(str) { 
        return str === str.split('').reverse().join(''); 
        }
        function isPalindrome(str) { 
        return str === str.split('').reverse().join(''); 
        }
        `,
      languageType: 'JavaScript',
      status: Status.WRONG_ANSWER,
      publicTestCasesPassed: 1,
      privateTestCasesPassed: 0,
      inputParams: ['str'],
      inputType: ['string'],
      outputFormat: 'boolean',
      testcases: [
        {
          testcaseId: 'tc1',
          inputValues: ['madam'],
          expectedOutput: 'true',
          actualOutput: 'true',
          weightage: 1,
          testcaseStatus: 'PASSED',
          testcaseType: 'PUBLIC',
          explanation: ' The only possible triplet does not sum up to 0.',
        },
        {
          testcaseId: 'tc2',
          inputValues: ['hello'],
          expectedOutput: 'false',
          actualOutput: 'true',
          weightage: 2,
          testcaseStatus: 'FAILED',
          testcaseType: 'PRIVATE',
        },
      ],
    },
  ];
  changeSection(value: 'Mcqs' | 'Coding') {
    this.currentSection = value;
  }
}
