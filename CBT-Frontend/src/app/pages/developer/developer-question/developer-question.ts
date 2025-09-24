import { Component } from '@angular/core';
import { DynamicLayout } from '../../../components/UI/dynamic-layout/dynamic-layout';
import { AdminHeader } from '../../../components/UI/admin-header/admin-header';
import { MonacoEditor } from '../../../components/UI/monoco-editor/monoco-editor';
import { CodeSection } from '../../../components/test-page/code-section/code-section';
import { CodingQuestions } from '../../../models/test/questions';
import { McqQuestions } from '../../../models/test/questions';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TestPage } from '../../user/test-page/test-page';
import { Progress } from '../../../components/progress/progress';
import { Subscription } from 'rxjs';
import { Editor } from '../../../components/admin/editor/editor';
@Component({
  selector: 'app-developer-question',
  imports: [Editor,TestPage, CommonModule, FormsModule, CodeSection, AdminHeader, Progress],
  templateUrl: './developer-question.html',
  styleUrl: './developer-question.css'
})
export class DeveloperQuestion {

  currentCodingQuestionNo: number = -1;
    currentSection: 'Mcqs' | 'Coding' = 'Mcqs';
    themeSubcription!: Subscription;
    currentTheme: string = '';
  
    codingQuestions: CodingQuestions[] = [
      {
        codingQuestionId: 'code1',
        question: 'Implement a function to reverse a string.',
        description:
          'Write a program that takes a string as input and returns the reversed version of it.',
        difficulty: 'Easy',
        inputType: 'string',
        outputType: 'string',
        testcases: [
          {
            id: 't11',
            input1: 'hello',
            output: 'olleh',
            explanation: ' The only possible triplet does not sum up to 0.',
          },
          {
            id: 't22',
  
            input1: 'world',
            output: 'dlrow',
            explanation: ' The only possible triplet does not sum up to 0.',
          },
          {
            id: 't33',
  
            input1: 'OpenAI',
            output: 'IAnepO',
            explanation: ' The only possible triplet does not sum up to 0.',
          },
        ],
      },
    
    ];
    mcqQuestions: McqQuestions[] = [
      {
        mcqId: 'mcq1',
        question: 'Which of the following is a JavaScript framework?',
        options: ['React', 'Django', 'Laravel', 'Spring Boot'],
      },
      {
        mcqId: 'mcq2',
        question: 'What does CSS stand for?',
        options: [
          'Cascading Style Sheets',
          'Creative Style Syntax',
          'Colorful Styling System',
          'Control Style Sheet',
        ],
      },
      {
        mcqId: 'mcq3',
        question: 'Which of the following is NOT a primitive data type in Java?',
        options: ['int', 'float', 'String', 'boolean'],
      },
      {
        mcqId: 'mcq4',
        question: 'Which HTML tag is used to define a hyperlink?',
        options: ['<link>', '<a>', '<href>', '<url>'],
      },
      {
        mcqId: 'mcq5',
        question:
          'What is the time complexity of binary search in a sorted array?',
        options: ['O(n)', 'O(log n)', 'O(n log n)', 'O(1)'],
      },
      {
        mcqId: 'mcq6',
        question: 'Which protocol is used to transfer web pages?',
        options: ['FTP', 'SMTP', 'HTTP', 'SSH'],
      },
      {
        mcqId: 'mcq7',
        question:
          'In relational databases, which command is used to extract data?',
        options: ['INSERT', 'SELECT', 'UPDATE', 'DELETE'],
      },
      {
        mcqId: 'mcq8',
        question:
          'Which sorting algorithm has the best average case time complexity?',
        options: ['Quick Sort', 'Merge Sort', 'Bubble Sort', 'Selection Sort'],
      },
      {
        mcqId: 'mcq9',
        question: 'What does SQL stand for?',
        options: [
          'Structured Query Language',
          'Simple Query Logic',
          'Standard Query Line',
          'Sequential Query Language',
        ],
      },
      {
        mcqId: 'mcq10',
        question: 'Which company developed the C programming language?',
        options: ['Microsoft', 'Bell Labs', 'Apple', 'Sun Microsystems'],
      },
    ];
  
}

