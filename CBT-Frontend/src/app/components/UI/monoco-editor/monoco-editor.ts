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

  code = `
  // C++ program to check if the number is even
// or odd using modulo operator
#include <bits/stdc++.h>
using namespace std;

int main() {
    int n = 11;

    // If n is completely divisible by 2
    if (n % 2 == 0)
        cout << "Even";

    // If n is NOT completely divisible by 2
    else
        cout << "Odd";
    return 0;
}
   `;
  ngOnInit(): void {
    console.log(this.currentLanguage);
    this.editorOptions = {
      ...this.editorOptions,
      language: this.currentLanguage,
    };
  }
}
