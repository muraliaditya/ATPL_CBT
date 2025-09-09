import { Component, Input } from '@angular/core';
import { SplitterModule } from 'primeng/splitter';
import { CardModule } from 'primeng/card';
import { PanelModule } from 'primeng/panel';
import { MonacoEditor } from '../../UI/monoco-editor/monoco-editor';
import { CodingQuestions } from '../../../models/test/questions';
@Component({
  selector: 'app-code-section',
  imports: [MonacoEditor, PanelModule, CardModule, SplitterModule],
  templateUrl: './code-section.html',
  styleUrl: './code-section.css',
})
export class CodeSection {
  @Input() questions: CodingQuestions[] = [];
}
