import { Component } from '@angular/core';
import { DynamicLayout } from '../../../components/UI/dynamic-layout/dynamic-layout';
import { codingQuestions } from '../../../models/admin/admin';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-code-listing',
  imports: [
    DynamicLayout,
    InputTextModule,
    FloatLabelModule,
    FormsModule,
    CommonModule,
  ],
  templateUrl: './code-listing.html',
  styleUrl: './code-listing.css',
})
export class CodeListing {
  sort = '';
  editingId = '';
  onSearch() {
    console.log('Search');
  }
  onEdit(q: codingQuestions) {
    console.log('edit');
  }
  onDelete(q: codingQuestions) {
    this.Codes = this.Codes.filter((c) => c.questionId !== q.questionId);
  }
  'pageNo': 1;
  Codes: codingQuestions[] = [
    {
      questionId: 'code_001',
      questionName: 'Add Two Numbers',
      difficulty: 'Easy',
    },
    {
      questionId: 'code_002',
      questionName: 'Integer Conversion',
      difficulty: 'Medium',
    },
    {
      questionId: 'code_003',
      questionName: 'Binary Tree Traversal',
      difficulty: 'Hard',
    },
    {
      questionId: 'code_004',
      questionName: 'Array Sum',
      difficulty: 'Easy',
    },
    {
      questionId: 'code_005',
      questionName: 'String Palindrome',
      difficulty: 'Medium',
    },
  ];
}
