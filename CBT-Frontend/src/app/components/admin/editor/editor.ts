import { Component } from '@angular/core';
import { MonacoEditorModule } from 'ngx-monaco-editor-v2';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-editor',
  imports: [FormsModule,MonacoEditorModule],
  templateUrl: './editor.html',
  styleUrl: './editor.css'
})
export class Editor {
  code: string = `function hello() {
  print("Hello World!");
}`;

  editorOptions = {
    theme: 'vs-dark',
    language: 'python',
    automaticLayout: true,
    fontSize: 14,
    minimap: { enabled: false }
  };

  onCodeChanged(value: string) {
    console.log('Code changed:', value);
  }
}
