import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { Select } from 'primeng/select';

@Component({
  selector: 'app-pagination',
  imports: [
    CommonModule,
    Select,
    FloatLabelModule,
    FormsModule,
    InputTextModule,
  ],
  templateUrl: './pagination.html',
  styleUrl: './pagination.css',
})
export class Pagination {
  totalPages = 15;
  @Output() pageChange: EventEmitter<number> = new EventEmitter();
  @Output() limitChange: EventEmitter<number> = new EventEmitter();
  paginatedArray: string[] = [];
  currentPage = 1;
  paginatedLimit: number = 10;
  changeLimit(value: number) {
    this.limitChange.emit(value);
  }
  paginationOptions = [
    { label: '10', value: 10 },
    { label: '20', value: 20 },
    { label: '30', value: 30 },
  ];
  goToPrevPage() {
    this.currentPage = this.currentPage - 1;
    this.pageChange.emit(this.currentPage);
  }
  goToNextPage() {
    this.currentPage = this.currentPage + 1;
    this.pageChange.emit(this.currentPage);
  }
  goToPage(val: string) {
    let page = parseInt(val);
    if (typeof page !== 'number' || page === this.currentPage) {
      return;
    }
    this.currentPage = page;
    this.pageChange.emit(page);
  }

  get constructPaginatedArray() {
    let tempPaginatedArray = [];
    if (this.totalPages <= 3) {
      for (let i = 1; i <= this.totalPages; i++) {
        tempPaginatedArray.push(i.toString());
      }
    } else {
      tempPaginatedArray.push('1');
      if (this.currentPage > 3) {
        tempPaginatedArray.push('...');
      }
      for (
        let i = Math.max(2, this.currentPage - 1);
        i <= Math.min(this.totalPages - 1, this.currentPage + 1);
        i++
      ) {
        tempPaginatedArray.push(i.toString());
      }

      if (this.currentPage < this.totalPages - 2) {
        tempPaginatedArray.push('...');
      }
      tempPaginatedArray.push(this.totalPages.toString());
    }
    this.paginatedArray = tempPaginatedArray;
    return this.paginatedArray;
  }

  @Input()
  set pagesCount(value: number) {
    this.totalPages = value;
  }
}
