import { Component } from '@angular/core';
import { FloatLabel } from 'primeng/floatlabel';
import { FormsModule } from '@angular/forms';
import { submissions } from '../../../models/admin/admin';
import { CommonModule } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { mcqSections } from '../../../models/admin/admin';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { data } from '../../../models/admin/admin';
@Component({
  selector: 'app-request-dashboard',
  imports: [InputTextModule, FloatLabel, FormsModule, CommonModule, RouterLink],
  templateUrl: './request-dashboard.html',
  styleUrl: './request-dashboard.css'
})
export class RequestDashboard {
  openRow:string|null=null;
  UserName='';
  constructor(private router: Router) { }
  p: any;
    
  
    onApproval(p: submissions) {
       alert(`Approved ${p.devId}`);
       console.log('Saved:', p.devId,p.questionId,p.questionType,p.userName);
          }
  
    onDelete(p:submissions) {
      if (confirm(`Delete ${p.devId}?`)) {
        this.submit = this.submit.filter((c) => c.devId !== p.devId);
      }
    }    
  submit:submissions[]= [
    {
      "devId": "#DEV-0441",
      "userName": "john deo",
      "questionType": "Mcq",
      "questionId": "mcq_12345"
    },
    {
      "devId": "#DEV-0443",
      "userName": "john deo", 
      "questionType": "Coding",
      "questionId": "code_56789"
    },
    {
      "devId": "#DEV-0442",
      "userName": "john deo",
      "questionType": "Mcq", 
      "questionId": "mcq001"
    },
    {
      "devId": "#DEV-0439",
      "userName": "john deo",
      "questionType": "Coding",
      "questionId": "code_11111"
    },
    {
      "devId": "#DEV-0442",
      "userName": "john deo",
      "questionType": "Mcq", 
      "questionId": "mcq002"
    },
    {
      "devId": "#DEV-0442",
      "userName": "john deo",
      "questionType": "Mcq", 
      "questionId": "mcq003"
    },
  ]
  Mcq:data[]= [{
      "mcqQuestionId": "mcq_12345",
      "question": "What is the chemical symbol for gold?",
      "option1": "AU",
      "option2": "CU",
      "option3": "AG", 
      "option4": "FE",
      "answerKey": "AU",
      "weightage": 2,
      "section":"Mcq",
    }]
}
