import { Component } from '@angular/core';
import { MonacoEditorModule } from 'ngx-monaco-editor-v2';
@Component({
  selector: 'app-editor',
  imports: [MonacoEditorModule],
  templateUrl: './editor.html',
  styleUrl: './editor.css'
})
export class Editor {
  code: string = `function hello() {
  console.log("Hello World!");
}`;

  editorOptions = {
    theme: 'vs-dark',
    language: 'javascript',
    automaticLayout: true,
    fontSize: 14,
    minimap: { enabled: false }
  };

  onCodeChanged(value: string) {
    console.log('Code changed:', value);
  }
}
