import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  FormsModule,
  NgForm,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { DynamicLayout } from '../../UI/dynamic-layout/dynamic-layout';
import { AdminHeader } from '../../UI/admin-header/admin-header';
import { FloatLabel } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { Select } from 'primeng/select';

@Component({
  selector: 'app-add-coding-question',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    DynamicLayout,
    AdminHeader,
    FormsModule,
    FloatLabel,
    InputTextModule,
    TextareaModule,
    Select,
  ],
  templateUrl: './add-coding-question.html',
  styleUrl: './add-coding-question.css',
})
export class AddCodingQuestion {
  testCaseTypesForm: FormGroup;
  inputDataTypes: string[] = ['int', 'string', 'intArray', 'strArray'];
  value3: string = '';
  inputTypesCount: number = 0;
  constructor(private fb: FormBuilder) {
    this.testCaseTypesForm = fb.group({
      testcasesTypes: fb.array([]),
    });
  }

  get testCasesTypes(): FormArray {
    return this.testCaseTypesForm.get('testcasesTypes') as FormArray;
  }

  AddTestCaseTypes() {
    return this.fb.group({
      inputType: ['', Validators.required],
      parameterName: ['', Validators.required],
    });
  }

  AddTestCaseTypesCount(num: number) {
    this.testCasesTypes.clear();

    for (let i = 0; i < num; i++) {
      this.testCasesTypes.push(this.AddTestCaseTypes());
    }
  }
  removeAtIndex(idx: number) {
    this.testCasesTypes.removeAt(idx);
  }
  submitForm(value: NgForm) {
    console.log('Submitted value:', value.value.myNumber);
    this.AddTestCaseTypesCount(value.value.myNumber);
  }
}
