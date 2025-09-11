import { Component, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MonacoEditorModule } from 'ngx-monaco-editor-v2';
import { CodeEditorThemeService } from '../../../services/code-editor-theme-service';
interface monacoEditorType {
  language: string;
  automaticLayout: boolean;
  minimap: any;
}
@Component({
  selector: 'app-monaco-editor',
  imports: [MonacoEditorModule, FormsModule],
  templateUrl: './monoco-editor.html',
  styleUrl: './monoco-editor.css',
})
export class MonacoEditor implements OnInit {
  @Input() currentLanguage: string = '';
  @Input() code: string = '';
  editorOptions: monacoEditorType = {
    language: 'cpp',
    automaticLayout: true,
    minimap: {
      enabled: false,
    },
  };

  constructor(private applyTheme: CodeEditorThemeService) {}

  onEditorInit(editor: any) {
    console.log(this.applyTheme.getDefaultTheme());
    (window as any).monaco.editor.setTheme(this.applyTheme.getDefaultTheme());
  }

  changeEditorOptions(currentLanguage: string) {
    if (this.editorOptions.language !== currentLanguage) {
      this.editorOptions = { ...this.editorOptions, language: currentLanguage };
      this.code = '';
    }
  }
  changedCode(value: String) {
    console.log(value);
  }

  ngOnInit(): void {
    console.log(this.currentLanguage);
    console.log(this.code);
    this.editorOptions = {
      ...this.editorOptions,
      language: this.currentLanguage,
    };
  }
}
