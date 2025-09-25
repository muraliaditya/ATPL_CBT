import { Component } from '@angular/core';
import { MonacoEditorModule } from 'ngx-monaco-editor-v2';
import { FormsModule } from '@angular/forms';
import { languages } from 'prismjs';
@Component({
  selector: 'app-editor',
  imports: [FormsModule,MonacoEditorModule],
  templateUrl: './editor.html',
  styleUrl: './editor.css'
})
export class Editor {
  boiler:Record<string,string>={
    python:`print(hello)`,
    java:`class main{
    public static void main(String args[])
    {
    //code here
    }
    }`,
    c:`#include <stdio.h>
    int main()
    {
    //code here
    },`,
  }
  code: string =this.boiler['c'];
  editorOptions = {
    theme: 'vs-dark',
    language: 'c',
    automaticLayout: true,
    fontSize: 14,
    minimap: { enabled: false }
  };
  changeLanguage(language:string){
    this.editorOptions={...this.editorOptions,language:language};
    this.code=this.boiler[language];
  }
  onCodeChanged(value: string) {
    console.log('Code changed:', value);
  }
}
