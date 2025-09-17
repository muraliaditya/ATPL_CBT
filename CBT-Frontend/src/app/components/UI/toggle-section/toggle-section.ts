import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-toggle-section',
  imports: [CommonModule],
  templateUrl: './toggle-section.html',
  styleUrl: './toggle-section.css',
})
export class ToggleSection {
  @Input() headingText: string = '';
  @Input() showSection: boolean = true;
  changeShowState() {
    this.showSection = !this.showSection;
  }
}
