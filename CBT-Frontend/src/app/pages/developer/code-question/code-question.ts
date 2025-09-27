import { Component, ViewChild } from '@angular/core';
import { AddCode } from '../../admin/add-code/add-code';
import { FormArray, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Editor } from '../../../components/admin/editor/editor';
import { FloatLabel } from 'primeng/floatlabel';
import { Select } from 'primeng/select';
import { Dialog } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormGroup,FormBuilder,Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
@Component({
  selector: 'app-code-question',
  imports: [AddCode,FloatLabel,Select,Editor, CommonModule, FormsModule, Dialog,ButtonModule,InputTextModule,ReactiveFormsModule],
  templateUrl: './code-question.html',
  styleUrl: './code-question.css'
})
export class CodeQuestion {
  testType=['Public','Private'];
  type:string ='';
  Question:string='';
  description='';
    inputId:string='';
    outputId:string='';
    language=['c','python','java','c++'];
    selectedlanguage:string='c';
    @ViewChild(Editor) Editorref!: Editor;
    languageChange(val:Event){
      this.Editorref.changeLanguage(val.toString());
    }
  TestCase: FormGroup;
  inputCount: number = 0;
  constructor(private fb: FormBuilder) {
    this.TestCase = this.fb.group({
      inputs: this.fb.array([]),
      output: ['', Validators.required],
      type:['',Validators.required],
      weightage: ['', Validators.required],
    });
  }
  get inputsArray(): FormArray {
  return this.TestCase.get('inputs') as FormArray;
}
onCountChange(newCount: number) {
  this.inputCount = newCount;
  this.inputsArray.clear();
  for (let i = 0; i < this.inputCount; i++) {
    this.inputsArray.push(this.fb.control('', Validators.required));
  }
}
    visible: boolean = false;
    showDialog() {
        this.visible = true;
    }
    submittedTestCases:any[]=[];
    onSubmit(){
      if(this.TestCase.valid){
      this.submittedTestCases.push(this.TestCase.value);
      }
      this.TestCase.reset();
      this.visible = false
    }
    reset(){
      this.TestCase.reset();
    }
      currentSection: 'TestCase' | 'Result' = 'TestCase';
      sectionchange(section: 'TestCase' | 'Result') {
        this.currentSection = section;
  }
}
