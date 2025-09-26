import { Component, ElementRef, ViewChild, viewChild } from '@angular/core';
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
  @ViewChild('codingQuesScroll') questionScroll!: ElementRef<HTMLDivElement>;
  pageNo: number = 1;
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
  timeout: null | number = null;

  onScroll(event: Event) {
    if (this.questionScroll) {
      const questionsScrollElement = this.questionScroll.nativeElement;
      if (
        questionsScrollElement.clientHeight +
          questionsScrollElement.scrollTop >=
        questionsScrollElement.scrollHeight - 50
      ) {
        if (this.timeout) {
          return;
        }
        console.log('bottom reached');

        this.timeout = setTimeout(() => {
          this.Codes = [...this.Codes, ...this.Codes.slice(0, 10)];
          if (this.timeout) {
            this.timeout = null;
          }
        }, 2000);
      }
    }
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
