import { Component } from '@angular/core';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { FormsModule } from '@angular/forms';
import { Select } from 'primeng/select';
import { CommonModule } from '@angular/common';
import { mcqSections } from '../../../models/admin/admin';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-mcq-listing',
  imports: [CommonModule, Select, InputTextModule, FloatLabelModule, FormsModule, RouterLink],
  templateUrl: './mcq-listing.html',
  styleUrl: './mcq-listing.css'
})
export class MCQListing {
  Region: string = '';
  editingId: string | null = null;
  editCache: any = {};
  filteredMcqs: mcqSections[]=[];
  onEdit(q: mcqSections) {
    this.editingId = q.mcqQuestionId;
  }
  onSave(q: mcqSections) {
    console.log('Saved:', q);
    this.editingId = null;
  }
  onCancel() {
    this.editingId = null;
  }
  onDelete(q:mcqSections){
    this.mcqs = this.mcqs.filter(c => c.mcqQuestionId !== q.mcqQuestionId);
  }
  Section: string[] = ['Geography','Literature','Science'];
  mcqs: mcqSections[]= [
      {
      mcqQuestionId: 'mcq001',
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
      mcqQuestionId: 'mcq004',
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
      mcqQuestionId: 'mcq005',
      question: "Who wrote 'Romeo and Juliet'?",
      option1: 'Charles Dickens',
      option2: 'William Shakespeare',
      option3: 'Jane Austen',
      option4: 'Mark Twain',
      answerKey: 'William Shakespeare',
      weightage: 1,
      section: 'Literature',
    },
    ]
    ngOnInit() {
    this.filteredMcqs = [...this.mcqs];
  }
  onSearch() {
    const r = (this.Region || '').trim();
    if (!r) {
      this.filteredMcqs = [...this.mcqs];
    } else {
      this.filteredMcqs = this.mcqs.filter(q => q.section === r);
    }
  }
}
