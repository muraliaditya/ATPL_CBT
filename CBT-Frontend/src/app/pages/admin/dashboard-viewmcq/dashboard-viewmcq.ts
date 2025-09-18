import { Component } from '@angular/core';
import { FloatLabel } from 'primeng/floatlabel';
import { Select } from 'primeng/select';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { data } from '../../../models/admin/admin';
import { InputText } from 'primeng/inputtext';
import { AdminHeader } from '../../../components/UI/admin-header/admin-header';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-dashboard-viewmcq',
  imports: [InputText,AdminHeader,FloatLabel,Select,FormsModule,CommonModule],
  templateUrl: './dashboard-viewmcq.html',
  styleUrl: './dashboard-viewmcq.css'
})
export class DashboardViewmcq {
  editingId:string|null=null;
  Section: string[] = ['Geography','Literature','Science'];
  onEdit(mcq: data) {
      this.editingId = mcq.mcqQuestionId;
    }
    onSave(q: data) {
      console.log('Saved:', q);
      this.editingId = null;
    }
    onCancel() {
      this.editingId = null;
    }
    onDelete(q:data){
      this.mcqs = this.mcqs.filter(c => c.mcqQuestionId !== q.mcqQuestionId);
    }
    mcqId!: string;
  mcq: any;

  mcqs = [
    {
      mcqQuestionId: 'mcq_12345',
      question: 'What is the capital of France?',
      option1: 'Berlin',
      option2: 'Madrid',
      option3: 'Paris',
      option4: 'Rome',
      answerKey: 'Paris',
      weightage: 2,
      section: 'Geography',
    },
    {
      mcqQuestionId: 'mcq1111',
      question: 'What is capital of France?',
      option1: 'Berlin',
      option2: 'Madrid',
      option3: 'Paris',
      option4: 'Rome',
      answerKey: 'Paris',
      weightage: 2,
      section: 'Geography',
    }
  ];

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.mcqId = this.route.snapshot.paramMap.get('id')!;
    this.mcq = this.mcqs.find(m => m.mcqQuestionId === this.mcqId);
  }
//  Mcq:data[]= [{
//     "mcqQuestionId": "mcq_12345",
//     "question": "What is the chemical symbol for gold?",
//     "option1": "AU",
//     "option2": "CU",
//     "option3": "AG", 
//     "option4": "FE",
//     "answerKey": "AU",
//     "weightage": 2,
//     "section":"Mcq",
//   }]
}
