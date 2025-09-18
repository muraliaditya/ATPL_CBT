import { Component } from '@angular/core';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { Select } from 'primeng/select';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-mcq-question',
  imports: [FormsModule,Select ,InputTextModule,FloatLabelModule,CommonModule ],
  templateUrl: './add-mcq-question.html',
  styleUrl: './add-mcq-question.css'
})
export class AddMcqQuestion {
 choice: string = '';
  category: string[] = ['Aptitude','Reasoning','Quantitative'];
  Question='';
  ContestName='';
  value1='';
  value2='';
  value3='';
  value4='';
  Weightage='';
  questions: any[] = [];
  idx=0;
  addQuestion() {
    this.questions.push({
      question: '',
      category: null,
      options: ['', '', '', ''],
      weightage: '',
    });
  }
  removeQuestion(index: number) {
    alert('Delete Question {{index}}')
    this.questions.splice(index, 1);
  }
  submit(){
    alert('Submitted Added Questions');
    console.log('Submitted', this.questions);
  }
  }
