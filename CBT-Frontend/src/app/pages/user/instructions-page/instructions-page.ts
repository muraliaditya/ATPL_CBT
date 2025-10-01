import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

interface InstructionsHeader {
  instructionHeader: string;
  instructions: string[];
}
interface Section {
  sectionType: string;
  questionCount: number;
  totalMarks: number;
  subSections?: SubSection[];
}

interface SubSection {
  type: string;
  questionCount: number;
  marks: number;
}

@Component({
  selector: 'app-instructions-page',
  imports: [CommonModule, FormsModule],
  templateUrl: './instructions-page.html',
  styleUrl: './instructions-page.css',
})
export class InstructionsPage {
  contestName: string = 'CONTEST-1';

  sections: Section[] = [
    {
      sectionType: 'Coding',
      questionCount: 2,
      totalMarks: 100,
    },
    {
      sectionType: 'MCQ',
      questionCount: 20,
      totalMarks: 40,
      subSections: [
        {
          type: 'Quantitative',
          questionCount: 10,
          marks: 20,
        },
        {
          type: 'Aptitude',
          questionCount: 10,
          marks: 20,
        },
      ],
    },
  ];

  constructor(private router: Router) {}

  codingQuestions: number = 3;
  mcqsQuestions: number = 20;
  isChecked: boolean = false;
  showCheckBoxMessage: boolean = false;
  instructionsSet: InstructionsHeader[] = [
    {
      instructionHeader: 'Pre-Test Setup',
      instructions: [
        'Ensure a stable internet connection throughout the test. Interruptions may affect your submission.',
        'Use a desktop or laptop with a functional webcam and microphone (if required).',
      ],
    },
    {
      instructionHeader: 'Test Environment',
      instructions: [
        'You must be alone in a quiet room during the test. No other person should be present or assist you.',
        'Do not use mobile phones, smartwatches, or external devices unless explicitly allowed.',
      ],
    },
    {
      instructionHeader: 'Answer Submission',
      instructions: [
        'You must be alone in a quiet room during the test. No other person should be present or assist you.',
        'Do not use mobile phones, smartwatches, or external devices unless explicitly allowed.',
      ],
    },
    {
      instructionHeader: 'Integrity & Ethics',
      instructions: [
        'Cheating, plagiarism, or external help will result in disqualification.',
        'Do not share test content with others during or after the exam.Pre-Test Setup',
        'Ensure a stable internet connection throughout the test. Interruptions may affect your submission.',
        'Use a desktop or laptop with a functional webcam and microphone (if required).',
      ],
    },
    {
      instructionHeader: 'Integrity & Ethics',
      instructions: [
        'Cheating, plagiarism, or external help will result in disqualification.',
        'Do not share test content with others during or after the exam.Pre-Test Setup',
        'Ensure a stable internet connection throughout the test. Interruptions may affect your submission.',
        'Use a desktop or laptop with a functional webcam and microphone (if required).',
      ],
    },
    {
      instructionHeader: 'Answer Submission',
      instructions: [
        'You must be alone in a quiet room during the test. No other person should be present or assist you.',
        'Do not use mobile phones, smartwatches, or external devices unless explicitly allowed.',
      ],
    },
  ];
  goFullScreen() {
    const el = document.documentElement as any;
    console.log(el);
    if (el.requestFullscreen) {
      el.requestFullscreen();
    } else if (el.webkitRequestFullscreen) {
      el.webkitRequestFullscreen();
    } else if (el.mozRequestFullScreen) {
      el.mozRequestFullScreen();
    } else if (el.msRequestFullscreen) {
      el.msRequestFullscreen();
    }
  }

  proceedToTest() {
    if (!this.isChecked) {
      console.log('agree the instructions!');
      this.showCheckBoxMessage = true;
    } else {
      this.showCheckBoxMessage = false;
      this.goFullScreen();
      this.router.navigate(['/test']);
    }
  }
}
