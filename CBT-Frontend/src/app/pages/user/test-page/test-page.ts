import { Component, OnInit } from '@angular/core';
import { Navbar } from '../../../components/UI/navbar/navbar';
import { CommonModule } from '@angular/common';
import { CodingQuestions, McqQuestions } from '../../../models/test/questions';
import { McqSection } from '../../../components/test-page/mcq-section/mcq-section';
import { CodeSection } from '../../../components/test-page/code-section/code-section';
import { MonacoEditor } from '../../../components/UI/monoco-editor/monoco-editor';

@Component({
  selector: 'app-test-page',
  imports: [Navbar, CommonModule, McqSection, CodeSection],
  templateUrl: './test-page.html',
  styleUrl: './test-page.css',
})
export class TestPage implements OnInit {
  hours: number = 0;
  minutes: number = 32;
  seconds: number = 34;
  currentSection: 'Mcqs' | 'Coding' = 'Mcqs';
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
          input1: 'hello',
          output: 'olleh',
        },
        {
          input1: 'world',
          output: 'dlrow',
        },
        {
          input1: 'OpenAI',
          output: 'IAnepO',
        },
      ],
    },
    {
      codingQuestionId: 'code2',
      question: "Find the maximum subarray sum using Kadane's Algorithm.",
      description:
        'Given an array of integers, write a function to find the contiguous subarray which has the largest sum.',
      difficulty: 'Medium',
      inputType: 'number[]',
      outputType: 'number',
      testcases: [
        {
          input1: '[1, -2, 3, 4, -1, 2, 1, -5, 4]',
          output: '9',
        },
        {
          input1: '[-2, -3, -1, -5]',
          output: '-1',
        },
        {
          input1: '[5, 4, -1, 7, 8]',
          output: '23',
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
  changeSection(section: 'Mcqs' | 'Coding') {
    this.currentSection = section;
  }
  pad(num: number) {
    return num < 10 ? '0' + num : num.toString();
  }
  getDisplayTime(): string {
    return `${this.pad(this.hours)}:${this.pad(this.minutes)}:${this.pad(
      this.seconds
    )}`;
  }
  addTimer() {
    let timeoutId = setInterval(() => {
      let totalSeconds = this.hours * 3600 + this.minutes * 60 + this.seconds;

      if (totalSeconds > 0) {
        totalSeconds--;
        this.hours = Math.floor(totalSeconds / 3600);
        this.minutes = Math.floor((totalSeconds % 3600) / 60);
        this.seconds = totalSeconds % 60;
      } else {
        clearTimeout(timeoutId);
      }
    }, 1000);
  }
  ngOnInit(): void {
    this.addTimer();
  }
}
