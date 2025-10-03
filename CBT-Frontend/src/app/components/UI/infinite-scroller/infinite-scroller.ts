import { CommonModule } from '@angular/common';
import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  Output,
  ViewChild,
} from '@angular/core';

@Component({
  selector: 'app-infinite-scroller',
  imports: [CommonModule],
  templateUrl: './infinite-scroller.html',
  styleUrl: './infinite-scroller.css',
})
export class InfiniteScroller {
  @Input() loadingFailed: boolean = false;
  @Input() currentPage: number = 1;
  @Input() isLoading: boolean = false;
  @Input() totalPages: number | null = null;
  @Output() loadNextPage = new EventEmitter<void>();
  @ViewChild('codingQuesScroll') questionScroll!: ElementRef<HTMLDivElement>;
  onScroll(event: Event) {
    if (this.questionScroll) {
      const questionsScrollElement = this.questionScroll.nativeElement;
      if (
        questionsScrollElement.clientHeight +
          questionsScrollElement.scrollTop >=
        questionsScrollElement.scrollHeight - 50
      ) {
        console.log(this.totalPages);
        if (
          typeof this.totalPages === 'number' &&
          this.currentPage >= this.totalPages
        )
          return;
        console.log('bottom reached');

        if (!this.isLoading || this.loadingFailed) this.loadNextPage.emit();
      }
    }
  }
}
