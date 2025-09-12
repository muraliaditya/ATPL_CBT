import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../../../components/UI/navbar/navbar';

interface InstructionsHeader {
  instructionHeader: string;
  instructions: string[];
}

@Component({
  selector: 'app-instructions-page',
  imports: [CommonModule, FormsModule],
  templateUrl: './instructions-page.html',
  styleUrl: './instructions-page.css',
})
export class InstructionsPage {
  contestName: string = 'CONTEST-1';

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

  proceedToTest() {
    if (!this.isChecked) {
      console.log('agree the instructions!');
      this.showCheckBoxMessage = true;
    } else {
      this.showCheckBoxMessage = false;
    }
  }
}
