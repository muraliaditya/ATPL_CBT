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
testForm!: FormGroup;
  types = ['string', 'int', 'boolean','strArray','intArray','Char'];
  showForm:boolean = false;
  saved = false;
  visible: boolean = false;
  testCaseForm!: FormGroup;
  showTestCaseForm = false;
  testType=['Private','Public'];
  booleanType=['True','False'];

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.testForm = this.fb.group({
      question: ['', Validators.required],
      weightage: ['', Validators.required],
      description: ['', Validators.required],
      count: ['', [Validators.required, Validators.pattern(/^[1-9][0-9]*$/)]],
      inputs: this.fb.array([]),
      output: ['', Validators.required],
      method: ['', Validators.required]
    });
  }

  get inputs(): FormArray {
    return this.testForm.get('inputs') as FormArray;
  }

  onGenerate(){
    const count = this.testForm.get('count')?.value;
    if (!count || isNaN(count)) return;
    this.inputs.clear();
    for (let i = 0; i < count; i++) {
      this.inputs.push(
        this.fb.group({
          type: ['', Validators.required],
          parameter: ['', [Validators.required]]
        })
      );
    }
    this.showForm = true;
  }

  typesFromMainForm: string[] = [];
  openTestCaseForm() {
    console.log('ok')
    const count = this.testForm.get('count')?.value;
    if (!count) return;
    this.testCaseForm = this.fb.group({
      inputs: this.fb.array([]),
      output: ['', Validators.required],
      weightage:['',Validators.required],
      type:['',Validators.required],
    });
    const inputsArray = this.testCaseForm.get('inputs') as FormArray;
    const typesFromMainForm = this.inputs.value.map((i: any) => i.type);
      this.typesFromMainForm = typesFromMainForm;
    for (let i = 0; i < count; i++) {
      const validatorFn = this.validatorMap[typesFromMainForm[i]] || Validators.required;
      inputsArray.push(
        this.fb.group({
          value: ['', [Validators.required,validatorFn]]
        })
      );
    }
    const outputType = this.testForm.get('output')?.value || 'string';
    const outputValidator = this.validatorMap[outputType] || Validators.required;
    this.testCaseForm.get('output')?.setValidators([Validators.required, outputValidator]);
    this.testCaseForm.get('output')?.updateValueAndValidity();
    this.showTestCaseForm = true;
    this.visible=true;
  }
  
  onTestCaseSubmit() {
    if (this.testCaseForm.valid) {
      console.log('Test Case Added:', this.testCaseForm.value);
    } else {
      this.testCaseForm.markAllAsTouched();
    }
  }

  onCancel(): void {
    this.testForm.reset();
    this.showForm = false;
    this.inputs.clear();
  }

  onSave(): void {
    if (this.testForm.valid) {
      console.log('Form Submitted:', this.testForm.value);
      this.saved = true;
    } else {
      this.testForm.markAllAsTouched();
    }
  }

  onEdit(): void {
    this.saved = false;
  }

  language=['c','python','java','c++'];
    selectedlanguage:string='c';
    @ViewChild(Editor) Editorref!: Editor;
    languageChange(val:Event){
      this.Editorref.changeLanguage(val.toString());
    }

    currentSection: 'TestCase' | 'Result' = 'TestCase';
      sectionchange(section: 'TestCase' | 'Result') {
        this.currentSection = section;
  }
  validatorMap: { [key: string]: any } = {
  int: Validators.pattern(/^\d+$/),
  string: Validators.pattern(/^[a-zA-Z ]+$/),
  boolean: Validators.pattern(/^(true|false)$/i),
  strArray: StringArrayValidate,
  intArray: IntegerArrayValidate,
  Char: NaNCheckValidate,
};
get testCaseInputs(): FormArray {
  return this.testCaseForm.get('inputs') as FormArray;
}
save=false;
reset(){
      this.testCaseForm.reset();
    }
    onsave() {
  this.saved = true;
}
submittedTestCases:any[]=[];
onSubmit() {
  if (this.testCaseForm.valid) {
    const testCase = this.testCaseForm.value;
    this.submittedTestCases.push(testCase);
    this.testCaseForm.reset();
    this.visible = false;
    this.save = true;
  } else {
    this.testCaseForm.markAllAsTouched();
  }
}
selectedTestCase: any = null;
viewTestCase(index: number) {
  this.selectedTestCase = this.submittedTestCases[index];
  this.selectedTestCase.inputValues = this.selectedTestCase.inputs.map((i: any) => i.value);
}
}
