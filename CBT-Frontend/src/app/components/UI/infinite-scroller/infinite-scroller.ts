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
  @Input() currentPage = 1;
  @Input() isLoading = false;
  @Output() loadNextPage = new EventEmitter<void>();
  @ViewChild('codingQuesScroll') questionScroll!: ElementRef<HTMLDivElement>;
  onScroll(event: Event) {
    console.log(event);
    if (this.questionScroll) {
      const questionsScrollElement = this.questionScroll.nativeElement;
      if (
        questionsScrollElement.clientHeight +
          questionsScrollElement.scrollTop >=
        questionsScrollElement.scrollHeight - 50
      ) {
        console.log('bottom reached');
        if (!this.isLoading) this.loadNextPage.emit();
      }
    }
  }
}
