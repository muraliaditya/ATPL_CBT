import { Component } from '@angular/core';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { Select } from 'primeng/select';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-edit-mcq',
  imports: [FormsModule,Select ,InputTextModule,FloatLabelModule,CommonModule],
  templateUrl: './edit-mcq.html',
  styleUrl: './edit-mcq.css'
})
export class EditMcq {
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

  addQuestion() {
    this.questions.push({
      question: '',
      category: null,
      options: ['', '', '', ''],
      weightage: ''
    });
  }
  removeQuestion(index: number) {
    this.questions.splice(index, 1);
  }
}
