import {
  Component,
  ElementRef,
  OnInit,
  ViewChild,
  viewChild,
} from '@angular/core';
import { DynamicLayout } from '../../../components/UI/dynamic-layout/dynamic-layout';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { InfiniteScroller } from '../../../components/UI/infinite-scroller/infinite-scroller';
import { CodingService } from '../../../services/coding/coding-service';
import { codingQuestions } from '../../../models/admin/admin';
import { delay, finalize } from 'rxjs';
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
export class CodeListing implements OnInit {
  pageNo: number = 0;
  maxPages: number | null = null;
  pageSize: number = 5;
  isLoading: boolean = false;
  loadingFailed: boolean = false;
  sort = '';
  editingId = '';
  Codes: codingQuestions[] = [];

  capitalizeFirst(str: string): string {
    if (!str) return '';
    return str.charAt(0).toUpperCase() + str.slice(1);
  }

  getCodingquestions() {
    this.isLoading = true;

    this.codingService
      .searchQuestions(this.sort, this.pageNo + 1, this.pageSize)
      .pipe(
        delay(3000),
        finalize(() => (this.isLoading = false))
      )
      .subscribe({
        next: (data) => {
          if (Array.isArray(data.codingQuestions)) {
            this.Codes = [...this.Codes, ...data.codingQuestions];
          }
          if (typeof data.totalPages === 'number') {
            this.maxPages = data.totalPages;
            console.log(this.maxPages);
          }
          this.pageNo = data.pageNo;
          console.log(data);
        },
        error: (error) => {
          console.log(error);
          this.loadingFailed = true;
        },
      });
  }
  loadMore() {
    if (this.isLoading) return;

    this.getCodingquestions();
  }
  constructor(private codingService: CodingService) {}

  onSearch() {
    console.log('Search');

    this.Codes = [];
    this.pageNo = 0;
    this.maxPages = null;
    this.getCodingquestions();
  }
  onEdit(q: codingQuestions) {
    console.log('edit');
  }
  onDelete(q: codingQuestions) {
    this.Codes = this.Codes.filter((c) => c.questionId !== q.questionId);
  }
  ngOnInit(): void {
    this.getCodingquestions();
  }
}
