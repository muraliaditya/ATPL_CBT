import { Component } from '@angular/core';
import { FloatLabel } from 'primeng/floatlabel';
import { Select } from 'primeng/select';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { data } from '../../../models/admin/admin';
import { InputText } from 'primeng/inputtext';
import { AdminHeader } from '../../../components/UI/admin-header/admin-header';
import { ActivatedRoute } from '@angular/router';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-dashboard-viewmcq',
  imports: [InputText, FloatLabel, Select, FormsModule, CommonModule, RouterLink, AdminHeader],
  templateUrl: './dashboard-viewmcq.html',
  styleUrl: './dashboard-viewmcq.css'
})
export class DashboardViewmcq {
  i=0;
  editingId:string|null=null;
  Section: string[] = ['Geography','Literature','Science'];
  router: any;
  onEdit(mcq:data) {
      this.editingId = mcq.mcqQuestionId;
    }
    onApprove(mcq:data) {
      console.log('Saved:', this.mcq);
      this.editingId = null;
    }
    onDelete(mcq:data){
      this.mcq= this.mcqs.filter(c => c.mcqQuestionId !== mcq.mcqQuestionId);
      this.editingId=null;
    }
    onSave(mcq:data)
    {
      console.log('save',this.mcq);
      this.editingId=null;
    }
    onCancel() {
      this.editingId = null;
    }
    mcqId!: string;
    mcq: any;
    constructor(private route: ActivatedRoute) {}
    ngOnInit(): void {
    this.mcqId = this.route.snapshot.paramMap.get('id')!;
    this.mcq = this.mcqs.find(m => m.mcqQuestionId === this.mcqId);
  }
  mcqs :data[]= [
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
      mcqQuestionId: 'mcq003',
      question: "Who wrote 'Romeo and Juliet'?",
      option1: 'Charles Dickens',
      option2: 'William Shakespeare',
      option3: 'Jane Austen',
      option4: 'Mark Twain',
      answerKey: 'William Shakespeare',
      weightage: 1,
      section: 'Literature',
    },
    {
      mcqQuestionId: 'mcq002',
      question: 'Which planet is known as the Red Planet?',
      option1: 'Earth',
      option2: 'Mars',
      option3: 'Jupiter',
      option4: 'Venus',
      answerKey: 'Mars',
      weightage: 3,
      section: 'Science',
    },
    {
    mcqQuestionId: 'mcq001',
      question: "Who wrote 'Romeo and Juliet'?",
      option1: 'Charles Dickens',
      option2: 'William Shakespeare',
      option3: 'Jane Austen',
      option4: 'Mark Twain',
      answerKey: 'William Shakespeare',
      weightage: 1,
      section: 'Literature',
    }
  ];
}
