import { Component, ElementRef, ViewChild, viewChild } from '@angular/core';
import { DynamicLayout } from '../../../components/UI/dynamic-layout/dynamic-layout';
import { codingQuestions } from '../../../models/admin/admin';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { InfiniteScroller } from '../../../components/UI/infinite-scroller/infinite-scroller';
@Component({
  selector: 'app-code-listing',
  imports: [
    DynamicLayout,
    InputTextModule,
    FloatLabelModule,
    FormsModule,
    CommonModule,
    InfiniteScroller,
  ],
  templateUrl: './code-listing.html',
  styleUrl: './code-listing.css',
})
export class CodeListing {
  pageNo: number = 1;
  isLoading: boolean = false;
  sort = '';
  editingId = '';
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
  loadMore() {
    if (this.isLoading) return;
    this.isLoading = true;

    setTimeout(() => {
      this.Codes = [...this.Codes, ...this.Codes.slice(0, 10)];
      this.isLoading = false;
    }, 2000);
  }

  onSearch() {
    console.log('Search');
  }
  onEdit(q: codingQuestions) {
    console.log('edit');
  }
  onDelete(q: codingQuestions) {
    this.Codes = this.Codes.filter((c) => c.questionId !== q.questionId);
  }
}
