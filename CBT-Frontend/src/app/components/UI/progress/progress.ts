import { Component, Input } from '@angular/core';
@Component({
  selector: 'app-progress',
  imports: [],
  templateUrl: './progress.html',
  styleUrl: './progress.css',
})
export class Progress {
  @Input() solved: number = 0;
  @Input() total: number = 1;
  radius = 12;
  circumference = 2 * 3.14 * this.radius;
  get percentage(): number {
    return Math.round((this.solved / this.total) * 100);
  }
  get dashOffset(): number {
    return this.circumference * (1 - this.percentage / 100);
  }
}
