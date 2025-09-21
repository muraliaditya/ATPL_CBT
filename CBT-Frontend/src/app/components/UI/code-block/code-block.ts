import {
  Component,
  Input,
  OnInit,
  OnChanges,
  SimpleChanges,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import * as Prism from 'prismjs';

// languages you need
import 'prismjs/components/prism-c';
import 'prismjs/components/prism-cpp';
import 'prismjs/components/prism-java';
import 'prismjs/components/prism-javascript';
import 'prismjs/components/prism-typescript';

@Component({
  selector: 'app-code-block',
  imports: [CommonModule],
  templateUrl: './code-block.html',
  styleUrl: './code-block.css',
})
export class CodeBlock implements OnInit, OnChanges {
  @Input() code = '';
  @Input() language: string = 'cpp';
  @Input() title?: string;

  highlighted: SafeHtml = '';

  constructor(private sanitizer: DomSanitizer) {}

  ngOnInit() {
    this.updateHighlight();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['code'] || changes['language']) {
      this.updateHighlight();
    }
  }

  private updateHighlight() {
    const grammar =
      Prism.languages[this.language] ||
      Prism.languages['cpp'] ||
      Prism.languages['javascript'];
    const html = Prism.highlight(this.code || '', grammar, this.language);
    this.highlighted = this.sanitizer.bypassSecurityTrustHtml(html);
  }

  copy() {
    if (!navigator?.clipboard) {
      const el = document.createElement('textarea');
      el.value = this.code || '';
      document.body.appendChild(el);
      el.select();
      try {
        document.execCommand('copy');
      } finally {
        document.body.removeChild(el);
      }
      return;
    }
    navigator.clipboard.writeText(this.code || '').catch(() => {});
  }
}
