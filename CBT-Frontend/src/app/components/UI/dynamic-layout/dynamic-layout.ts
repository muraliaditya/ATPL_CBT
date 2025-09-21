import {
  AfterViewInit,
  Component,
  ElementRef,
  OnDestroy,
  ViewChild,
} from '@angular/core';

@Component({
  selector: 'app-dynamic-layout',
  imports: [],
  templateUrl: './dynamic-layout.html',
  styleUrl: './dynamic-layout.css',
})
export class DynamicLayout {
  @ViewChild('headerRef') headerRef!: ElementRef;
  @ViewChild('footerRef') footerRef!: ElementRef;
  @ViewChild('mainRef') mainRef!: ElementRef;

  private headerObserver!: ResizeObserver;
  private footerObserver!: ResizeObserver;

  ngAfterViewInit() {
    this.updateMainHeight();

    // Watch header changes
    this.headerObserver = new ResizeObserver(() => this.updateMainHeight());
    this.headerObserver.observe(this.headerRef.nativeElement);

    // Watch footer changes
    this.footerObserver = new ResizeObserver(() => this.updateMainHeight());
    this.footerObserver.observe(this.footerRef.nativeElement);

    // Also handle window resize
    window.addEventListener('resize', this.updateMainHeight);
  }

  ngOnDestroy() {
    this.headerObserver?.disconnect();
    this.footerObserver?.disconnect();
    window.removeEventListener('resize', this.updateMainHeight);
  }

  private updateMainHeight = () => {
    const headerHeight = this.headerRef?.nativeElement?.offsetHeight || 0;
    const footerHeight = this.footerRef?.nativeElement?.offsetHeight || 0;
    this.mainRef.nativeElement.style.height = `calc(100vh - ${headerHeight}px - ${footerHeight}px)`;
  };
}
