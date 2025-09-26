import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { codingQuestions,testcases } from '../../../models/developer/developer';
@Component({
  selector: 'app-testcase',
  imports: [CommonModule],
  templateUrl: './testcase.html',
  styleUrl: './testcase.css'
})
export class Testcase {
  currentSection: 'TestCase' | 'Result' = 'TestCase';
  inputId: any;
  outputId: any;
      sectionchange(section: 'TestCase' | 'Result') {
        this.currentSection = section;
  }
  codingQuestions=
      {
        codingQuestionId: 'code1',
        question: 'Implement a function to reverse a string.',
        description:
          'Write a program that takes a string as input and returns the reversed version of it.',
        Example1:`Consider a string world is must be printed in reverse order of dlrow 
         input=world 
         output=dlrow`,
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
      }
  testcase(i:number){
        const test=this.codingQuestions.testcases[i];
        this.inputId=`${test.input1}`;
        this.outputId=`${test.output}`
      }
}
