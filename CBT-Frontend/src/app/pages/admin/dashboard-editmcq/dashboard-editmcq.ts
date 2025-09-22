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
import { DynamicLayout } from '../../../components/UI/dynamic-layout/dynamic-layout';
@Component({
  selector: 'app-dashboard-editmcq',
  imports: [DynamicLayout,InputText,AdminHeader,FloatLabel,Select,FormsModule,CommonModule],
  templateUrl: './dashboard-editmcq.html',
  styleUrl: './dashboard-editmcq.css'
})
export class DashboardEditmcq {
  editingId:string|null=null;
  Section: string[] = ['Geography','Literature','Science'];
  constructor(private route: ActivatedRoute) {}
    onSave(mcqs: data) {
      console.log('Saved:', mcqs);
      this.editingId = null;
    }
    onCancel() {
      this.editingId = null;
    }
    Id!: string;
    mcqs: any;
    quote:'' | undefined;
    ngOnInit(): void {
    this.Id = this.route.snapshot.paramMap.get('id')!;
    this.mcqs = this.Mcq.find(m => m.mcqQuestionId === this.Id);
  }
 Mcq:data[]= [{
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
]
}
