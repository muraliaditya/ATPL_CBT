import { Component } from '@angular/core';
import { SplitterModule } from 'primeng/splitter';
import { CardModule } from 'primeng/card';
import { PanelModule } from 'primeng/panel';
import { MonacoEditor } from '../../UI/monoco-editor/monoco-editor';
@Component({
  selector: 'app-code-section',
  imports: [MonacoEditor, PanelModule, CardModule, SplitterModule],
  templateUrl: './code-section.html',
  styleUrl: './code-section.css',
})
export class CodeSection {}
