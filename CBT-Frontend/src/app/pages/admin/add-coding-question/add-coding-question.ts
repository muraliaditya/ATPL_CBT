import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  FormsModule,
  NgForm,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { DynamicLayout } from '../../../components/UI/dynamic-layout/dynamic-layout';
import { AdminHeader } from '../../../components/UI/admin-header/admin-header';
import { FloatLabel } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { Select } from 'primeng/select';
import { environment } from '../../../../environments/environment';
import { parameterDuplicateCheck } from '../../../utils/custom-validators/parameter-duplicate';

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
      outputType: ['', Validators.required],
      testcasesTypes: fb.array([], [parameterDuplicateCheck]),
      testcases: fb.array([]),
    });
  }

  basicFields = [
    {
      fieldName: 'output',
      value: 0,
    },
    {
      fieldName: 'weightage',
      value: 0,
    },
    {
      fieldName: 'TestCase Type',
      value: '',
    },
    {
      fieldName: 'Description',
      value: '',
    },
  ];

  eachTestCaseForm: { fieldName: string; value: number | string }[] = [];

  testcasesTypesSubmit() {
    let testcasesType = this.testCaseTypesForm.value.testcasesTypes;
    this.eachTestCaseForm = [];
    console.log(testcasesType);
    let newFields = [...testcasesType].map((testcase) => ({
      fieldName: testcase.parameterName,
      value: '',
    }));
    if (newFields.length) {
      for (let idx = newFields.length - 1; idx >= 0; idx--) {
        this.eachTestCaseForm.unshift(newFields[idx]);
      }
    }
    console.log(this.eachTestCaseForm);

    console.log(this.testCaseTypesForm.value.testcasesTypes);
    this.reconstructTestCases();

    console.log(this.testCaseTypesForm.value);
  }

  get TestCases() {
    return this.testCaseTypesForm.get('testcases') as FormArray;
  }

  AddNewTestCase(): FormGroup {
    const allFieldsArray = this.fb.array([
      this.fb.group({
        inputs: this.fb.array(
          this.eachTestCaseForm.map((field) =>
            this.fb.group({
              fieldName: [field.fieldName],
              value: [field.value],
            })
          )
        ),
      }),
      ...this.basicFields.map((field) =>
        this.fb.group({
          fieldName: [field.fieldName],
          value: [field.value],
        })
      ),
    ]);

    return this.fb.group({
      testcaseInputs: allFieldsArray,
    });
  }

  reconstructTestCases() {
    const testcasesArray = this.testCaseTypesForm.get('testcases') as FormArray;
    testcasesArray.clear();

    testcasesArray.push(this.AddNewTestCase());
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
    this.testCaseTypesForm.patchValue({
      outputType: '',
    });

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

  getTestCaseInputsArray(testIdx: number): FormArray {
    return this.TestCases.at(testIdx).get('testcaseInputs') as FormArray;
  }

  getInputsArray(testIdx: number): FormArray {
    const testcaseInputs = this.getTestCaseInputsArray(testIdx);
    return testcaseInputs.at(0).get('inputs') as FormArray;
  }

  getFieldName(testIdx: number, inputIdx: number): string {
    const inputsArray = this.getInputsArray(testIdx);
    const fieldNameControl = inputsArray.at(inputIdx).get('fieldName');
    return fieldNameControl ? fieldNameControl.value : '';
  }

  getBasicFieldName(testIdx: number, basicIdx: number): string {
    return this.basicFields[basicIdx].fieldName;
  }

  addTestCase() {
    if (this.eachTestCaseForm.length > 0) {
      this.TestCases.push(this.AddNewTestCase());
    }
  }

  deleteTestCase(testIdx: number) {
    if (this.TestCases.length > 1) {
      this.TestCases.removeAt(testIdx);
    }
  }
}
