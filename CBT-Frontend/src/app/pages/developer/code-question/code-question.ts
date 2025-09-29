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
import { DynamicLayout } from '../../../components/UI/dynamic-layout/dynamic-layout';
import { parameterDuplicateCheck } from '../../../utils/custom-validators/parameter-duplicate';
import { StringArrayValidate } from '../../../utils/custom-validators/string-array-validator';
import { IntegerArrayValidate } from '../../../utils/custom-validators/integer-array-validator';
import { NaNCheckValidate } from '../../../utils/custom-validators/nan-check';
@Component({
  selector: 'app-code-question',
  imports: [FloatLabel,Select,Editor, CommonModule, FormsModule, Dialog,ButtonModule,InputTextModule,ReactiveFormsModule,DynamicLayout],
  templateUrl: './code-question.html',
  styleUrl: './code-question.css'
})
export class CodeQuestion {
test:any[]=[];
saved: boolean = false; 
save:boolean=false;
show:boolean=false;
showForm: boolean = false;
question='';
method='';
description:'' | undefined;
output='';
weightage='';
count:number=0;
testtype=['Private','Public'];
types=['string','int','boolean','strArray','Char','intArray'];
onsave() {
  this.saved = true;
}
oncancel() {
  this.showForm = false;
  this.inputs = [];
  this.count = 0;
}
onEdit(){
  this.saved = false;
}
inputs:any[]=[];
ongenerate(){
  this.inputsArray.clear();
  this.inputs=[];
  for(let i = 0; i < this.count; i++)
  this.inputs.push({
    type:null,
    parameter:'',
})
this.saved = false;
this.save=false;
this.showForm = true;
}

inputval:any[]=[];
Inputform:any[]=[];
  testType=['Public','Private'];
  type:string ='';
  Question:string='';
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
  visible: boolean = false;
  showDialog() {
  this.inputsArray.clear();
  this.inputs.forEach(input => {
    let validator = Validators.required;
    if (input.type === 'int') {
      validator = Validators.pattern(/^\d+$/);
    } else if (input.type === 'string') {
      validator = Validators.pattern(/^[a-zA-Z ]+$/);
    } else if (input.type=== 'boolean') {
      validator=Validators.pattern(/^(true|false)$/i);
    } else if (input.type==='strArray') {
      validator= StringArrayValidate, Validators.required;
    } else if(input.type==='intArray'){
      validator= IntegerArrayValidate, Validators.required;
    } else if(input.type==='Char'){
      validator= NaNCheckValidate, Validators.required;
    }
    this.inputsArray.push(this.fb.control('', [Validators.required, validator]));
  });
  let outputValidator = Validators.required;
  if (this.output === 'int') {
    outputValidator = Validators.pattern(/^\d+$/);
  } else if (this.output === 'string') {
    outputValidator = Validators.pattern(/^[a-zA-Z ]+$/);
  } else if (this.output === 'boolean') {
    outputValidator = Validators.pattern(/^(true|false)$/i);
  } else if (this.output==='strArray') {
      outputValidator= StringArrayValidate, Validators.required;
  } else if(this.output==='intArray'){
    outputValidator= IntegerArrayValidate, Validators.required;
  } else if(this.output==='Char'){
    outputValidator= NaNCheckValidate, Validators.required;
  }
  this.TestCase.get('output')?.setValidators(outputValidator);
  this.TestCase.get('output')?.updateValueAndValidity();
  this.visible = true;
}
    submittedTestCases:any[]=[];
    onSubmit(){
      if(this.TestCase.valid){
      console.log(this.TestCase.value);
      }
      this.test.push({
        inputs:'',
        output:'',
      });
      this.save=true;
      this.TestCase.reset();
      this.visible = false;
    }
    reset(){
      this.TestCase.reset();
    }
      currentSection: 'TestCase' | 'Result' = 'TestCase';
      sectionchange(section: 'TestCase' | 'Result') {
        this.currentSection = section;
  }
}
