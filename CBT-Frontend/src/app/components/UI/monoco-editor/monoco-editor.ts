import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MonacoEditorModule } from 'ngx-monaco-editor-v2';
import { CodeEditorThemeService } from '../../../services/code-editor-theme-service';
interface monacoEditorType {
  language: string;
  automaticLayout: boolean;
}
@Component({
  selector: 'app-monaco-editor',
  imports: [MonacoEditorModule, FormsModule],
  templateUrl: './monoco-editor.html',
  styleUrl: './monoco-editor.css',
})
export class MonacoEditor {
  editorOptions: monacoEditorType = {
    language: 'cpp',
    automaticLayout: true,
  };

  constructor(private applyTheme: CodeEditorThemeService) {}

  toggletheme() {
    this.applyTheme.setTheme();
  }

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
}
