import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CodeQuestions } from '../../../models/admin/admin';
import {Testcase} from '../../../models/admin/admin';
import { ToggleSection } from '../../../components/UI/toggle-section/toggle-section';

@Component({
  selector: 'app-view-contest',
  imports: [FormsModule,CommonModule,ToggleSection],
  templateUrl: './view-contest.html',
  styleUrl: './view-contest.css'
})
export class ViewContest {
  goBack(){
    console.log("Back");
  }
  closeDialog(){
    console.log("closed");
  }
 currentContestId: string = '';
 currentSection: 'Mcqs' | 'Coding' = 'Mcqs';
 changeSection(section: 'Mcqs' | 'Coding') {
    this.currentSection = section;
  }
  constructHeading(value:string){
    return `Question -  ${value}`
  }
 contest=
  {
  "contest_id": "CT-0441",
  "contest_name": "Contest 1",
  "status": "ACTIVE",
  "start_time": "2025-01-24T17:00:00",
  "end_time": "2025-01-24T19:00:00",
  "duration": 120,
  "eligibility": "Student",
  "mcqs": {
    "mcqSections": [
      {
        "section": "MCQ",
        "mcqQuestions": [
          {
            "mcq_question_id": "mcq1",
            "question_text": "What is the chemical symbol for gold?",
            "option_1": "AU",
            "option_2": "CU", 
            "option_3": "AG",
            "option_4": "FE",
            "correctAnswer": "AU",
            "weightage": 1
          },
          {
            "mcq_question_id": "mcq1",
            "question_text": "What is the chemical symbol for gold?",
            "option_1": "AU",
            "option_2": "CU", 
            "option_3": "AG",
            "option_4": "FE",
            "correctAnswer": "AU",
            "weightage": 1
          },
          {
            "mcq_question_id": "mcq1",
            "question_text": "What is the chemical symbol for gold?",
            "option_1": "AU",
            "option_2": "CU", 
            "option_3": "AG",
            "option_4": "FE",
            "correctAnswer": "AU",
            "weightage": 1
          },
          {
            "mcq_question_id": "mcq1",
            "question_text": "What is the chemical symbol for gold?",
            "option_1": "AU",
            "option_2": "CU", 
            "option_3": "AG",
            "option_4": "FE",
            "correctAnswer": "AU",
            "weightage": 1
          },
          {
            "mcq_question_id": "mcq1",
            "question_text": "What is the chemical symbol for gold?",
            "option_1": "AU",
            "option_2": "CU", 
            "option_3": "AG",
            "option_4": "FE",
            "correctAnswer": "AU",
            "weightage": 1
          },
          {
            "mcq_question_id": "mcq1",
            "question_text": "What is the chemical symbol for gold?",
            "option_1": "AU",
            "option_2": "CU", 
            "option_3": "AG",
            "option_4": "FE",
            "correctAnswer": "AU",
            "weightage": 1
          }
        ]
      }
    ]
  }
}
coding:CodeQuestions[]= [
       {
      codeQuestionId: 'code001',
      questionName: 'Reverse a string',
      difficulty: 'Easy',
      description:
        'Write a function that takes a string and returns it reversed.',
      example:'Input : Teacher Output: rehcaeT',
      inputParams: ['str'],
      inputType: ['string'],
      outputType: 'string',
      testcases: [
        {
          testcaseId: 'tc1',
          inputValues: ['hello'],
          expectedOutput: 'olleh',
          testcaseType: 'PUBLIC',
          explanation: ' The only possible triplet does not sum up to 0.',
        },
        {
          testcaseId: 'tc2',
          inputValues: ['world'],
          expectedOutput: 'dlrow',
          testcaseType: 'PUBLIC',
          explanation: ' The only possible triplet does not sum up to 0.',
        },
      ],
    } ,
    {
      codeQuestionId: 'code001',
      questionName: 'Reverse a string',
      difficulty: 'Easy',
      description:
        'Write a function that takes a string and returns it reversed.',
      example:'str',
      inputParams: ['str'],
      inputType: ['string'],
      outputType: 'string',
      testcases: [
        {
          testcaseId: 'tc1',
          inputValues: ['hello'],
          expectedOutput: 'olleh',
          testcaseType: 'PUBLIC',
          explanation: ' The only possible triplet does not sum up to 0.',
        },
        {
          testcaseId: 'tc2',
          inputValues: ['world'],
          expectedOutput: 'dlrow',
          testcaseType: 'PUBLIC',
          explanation: ' The only possible triplet does not sum up to 0.',
        },
        {
          testcaseId: 'tc3',
          inputValues: ['wod'],
          expectedOutput: 'dow',
          testcaseType: 'PUBLIC',
          explanation: ' The only possible triplet does not sum up to 0.',
        },
      ],
    } 
]
}
