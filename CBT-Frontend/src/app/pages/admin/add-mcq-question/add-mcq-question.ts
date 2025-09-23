import { Component } from '@angular/core';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { Select } from 'primeng/select';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DynamicLayout } from "../../../components/UI/dynamic-layout/dynamic-layout";
import { AdminHeader } from '../../../components/UI/admin-header/admin-header';
@Component({
  selector: 'app-add-mcq-question',
  imports: [AdminHeader,FormsModule, Select, InputTextModule, FloatLabelModule, CommonModule, DynamicLayout],
  templateUrl: './add-mcq-question.html',
  styleUrl: './add-mcq-question.css'
})
export class AddMcqQuestion {
 choice: string = '';
  category: string[] = ['Aptitude','Reasoning','Quantitative'];
  answerkey:string[]=['A','B','C','D']
  Question='';
  ContestName='';
  value1='';
  value2='';
  value3='';
  value4='';
  Weightage='';
  answer='';
  questions: any[] = [
    {
      question: '',
      category: null,
      options: ['', '', '', ''],
      weightage: '',
      answer:'',
    }
  ];
  idx=0;
  addQuestion() {
    this.questions.push({
      question: '',
      category: null,
      options: ['', '', '', ''],
      weightage: '',
      answer:'',
    });
  }
  removeQuestion(index: number) {
    alert(`Delete Question ${index+1}`)
    this.questions.splice(index, 1);
  }
  submit(){
    alert('Submitted Added Questions');
    console.log('Submitted', this.questions);
  }
  }
