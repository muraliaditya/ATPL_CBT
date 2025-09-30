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
  selector: 'app-demo',
  imports: [FloatLabel,Select,Editor, CommonModule, FormsModule, Dialog,ButtonModule,InputTextModule,ReactiveFormsModule,DynamicLayout],
  templateUrl: './demo.html',
  styleUrl: './demo.css'
})
export class Demo {
  // types = ['string', 'int', 'boolean'];
  // showForm = false;

  // TestCaseForm: FormGroup;
  // TestValuesForm: FormGroup;
  // count = 0;

  // constructor(private fb: FormBuilder) {
  //   this.TestCaseForm = this.fb.group({
  //     question: ['', Validators.required],
  //     weightage: ['', Validators.required],
  //     description: ['', Validators.required],
  //     count:['',Validators.required],
  //     inputs: this.fb.array([]),
  //     output: this.fb.group({
  //       type: ['', Validators.required],
  //       method: ['', Validators.required],
  //     }),
  //   });

  //   this.TestValuesForm = this.fb.group({
  //     testInputs: this.fb.array([]),
  //     expectedOutput: ['', Validators.required]
  //   });
  // }

  // get inputsArray(): FormArray {
  //   return this.TestCaseForm.get('inputs') as FormArray;
  // }

  // get testInputsArray(): FormArray {
  //   return this.TestValuesForm.get('testInputs') as FormArray;
  // }

  // onGenerate() {
  //   this.inputsArray.clear();
  //   this.testInputsArray.clear();

  //   for (let i = 0; i < this.count; i++) {
  //     const inputGroup = this.fb.group({
  //       type: ['', Validators.required],
  //       parameter: ['', Validators.required],
  //     });
  //     this.inputsArray.push(inputGroup);

  //     this.testInputsArray.push(this.fb.control('', Validators.required));
  //   }

  //   this.showForm = true;
  // }
  // onTypeChange(index: number) {
  //   const type = this.inputsArray.at(index).get('type')?.value;
  //   const ctrl = this.testInputsArray.at(index);

  //   if (type === 'int') {
  //     ctrl.setValidators([Validators.required, Validators.pattern(/^\d+$/)]);
  //   } else if (type === 'string') {
  //     ctrl.setValidators([
  //       Validators.required,
  //       Validators.pattern(/^[a-zA-Z ]+$/),
  //     ]);
  //   } else if (type === 'boolean') {
  //     ctrl.setValidators([
  //       Validators.required,
  //       Validators.pattern(/^(true|false)$/i),
  //     ]);
  //   }

  //   ctrl.updateValueAndValidity();
  // }

  // onSave() {
  //   if (this.TestCaseForm.valid && this.TestValuesForm.valid) {
  //     console.log('Final Data:', {
  //       config: this.TestCaseForm.value,
  //       testValues: this.TestValuesForm.value,
  //     });
  //   } else {
  //     this.TestCaseForm.markAllAsTouched();
  //     this.TestValuesForm.markAllAsTouched();
  //   }
  // }
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
  Main:FormGroup;
  inputCount: number = 0;
  constructor(private fb: FormBuilder) {
    this.TestCase = this.fb.group({
      inputs: this.fb.array([]),
      output: ['', Validators.required],
      type:['',Validators.required],
      weightage: ['', Validators.required],
    });
    this.Main =this.fb.group({
      question:['', Validators.required],
      weightage:['', Validators.required],
      description:['',Validators.required],
      count:['',Validators.required],
    })

  }
  get inputsArray(): FormArray {
  return this.TestCase.get('inputs') as FormArray;
}
get formArray():FormArray{
   return this.Main.get('inputs') as FormArray
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
    reset(){
      this.TestCase.reset();
    }
      currentSection: 'TestCase' | 'Result' = 'TestCase';
      sectionchange(section: 'TestCase' | 'Result') {
        this.currentSection = section;
  }
selectedTestCase: any = null;

onSubmit() {
  if (this.TestCase.valid) {
    const testCase = this.TestCase.value;
    this.submittedTestCases.push(testCase);
    this.TestCase.reset();
    this.visible = false;
    this.save = true;
  } else {
    this.TestCase.markAllAsTouched();
  }
}

viewTestCase(index: number) {
  this.selectedTestCase = this.submittedTestCases[index];
}
onvalid(){
  if(this.Main.valid){
  console.log('validated');
  }
}
}