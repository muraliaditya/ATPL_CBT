import { Component, ViewChild } from '@angular/core';
import { AdminHeader } from '../../../components/UI/admin-header/admin-header';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Editor } from '../../../components/admin/editor/editor';
import { FloatLabel } from 'primeng/floatlabel';
import { Select } from 'primeng/select';
@Component({
  selector: 'app-developer-question',
  imports: [FloatLabel,Select,Editor, CommonModule, FormsModule, AdminHeader],
  templateUrl: './developer-question.html',
  styleUrl: './developer-question.css'
})
export class DeveloperQuestion { 
    inputId:string='';
    outputId:string='';
    useroutput:string='';
    language=['c','python','java','c++'];
    selectedlanguage:string='c';
    
    @ViewChild(Editor) Editorref!: Editor;
    languageChange(val:Event){
      this.Editorref.changeLanguage(val.toString());
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
            user_output:'olleh',
            explanation: ' The only possible triplet does not sum up to 0.',
          },
          {
            id: 't22',
            input1: 'world',
            output: 'dlrow',
            user_output:'dlrow',
            explanation: ' The only possible triplet does not sum up to 0.',
          },
          {
            id: 't33',
            input1: 'OpenAI',
            output: 'IAnepO',
            user_output:'i',
            explanation: ' The only possible triplet does not sum up to 0.',
          },
        ],
      }
      testcase(i:number){
        const test=this.codingQuestions.testcases[i];
        this.inputId=`${test.input1}`;
        this.outputId=`${test.output}`
      }
      selected:number|null=null;
      resultcase(i:number){
        this.selected=i;
        const result=this.codingQuestions.testcases[i];
        this.inputId=`${result.input1}`;
        this.outputId=`${result.output}`;
        this.useroutput=`${result.user_output}`;
      }
      currentSection: 'TestCase' | 'Result' = 'TestCase';
      sectionchange(section: 'TestCase' | 'Result') {
        this.currentSection = section;
  }
}

