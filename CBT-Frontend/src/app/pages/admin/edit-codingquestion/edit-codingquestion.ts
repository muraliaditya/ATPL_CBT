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
import { IntegerArrayValidate } from '../../../utils/custom-validators/integer-array-validator';
import { StringArrayValidate } from '../../../utils/custom-validators/string-array-validator';
import { NaNCheckValidate } from '../../../utils/custom-validators/nan-check';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-edit-codingquestion',
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
  templateUrl: './edit-codingquestion.html',
  styleUrl: './edit-codingquestion.css',
})
export class EditCodingquestion {
  testCaseTypesForm: FormGroup;
  tempTestCasesTypesForm: FormGroup;
  inputDataTypes: string[] = [
    'int',
    'string',
    'boolean',
    'character',
    'intArray',
    'strArray',
  ];
  testcaseTypesOptions: string[] = ['Public', 'Private'];
  inputTypesCount: number = 0;
  value3: string = '';
  showTestCaseForm = true;
  booleanOptions: string[] = ['true', 'false'];

  constructor(private fb: FormBuilder) {
    this.testCaseTypesForm = fb.group({
      question: ['', Validators.required],
      weightage: [0, [Validators.required, Validators.min(10)]],
      description: ['', Validators.required],
      methodSignature: ['', Validators.required],
      outputType: ['', Validators.required],
      testcasesTypes: fb.array([], [parameterDuplicateCheck]),
      testcases: fb.array([]),
    });
    this.tempTestCasesTypesForm = fb.group({
      testcasesTypes: fb.array([]),
      outputType: ['', Validators.required],
    });
  }

  isQuestionFormInvalid() {
    return this.testCaseTypesForm.invalid;
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

  eachTestCaseForm: {
    fieldName: string;
    value: number | string;
    validators: any[];
    inputType: string;
  }[] = [];

  getValidators(inputType: string) {
    if (inputType === 'intArray') {
      return [IntegerArrayValidate, Validators.required];
    } else if (inputType === 'strArray') {
      return [StringArrayValidate, Validators.required];
    } else if (inputType === 'character') {
      return [Validators.required, NaNCheckValidate];
    }
    return [Validators.required];
  }

  testcasesTypesSubmit() {
    let testcasesType = this.testCaseTypesForm.value.testcasesTypes;
    this.eachTestCaseForm = [];
    console.log(testcasesType);
    let newFields = [...testcasesType].map((testcase) => ({
      fieldName: testcase.parameterName,
      value: '',
      validators: this.getValidators(testcase.inputType),
      inputType: testcase.inputType,
    }));
    if (newFields.length) {
      for (let idx = newFields.length - 1; idx >= 0; idx--) {
        this.eachTestCaseForm.unshift(newFields[idx]);
      }
    }
    console.log(this.eachTestCaseForm);
    const testcasesTypes = this.testCaseTypesForm.value.testcasesTypes;
    const outputType = this.testCaseTypesForm.value.outputType;

    const tempTestCasesTypesArray = this.tempTestCasesTypesForm.get(
      'testcasesTypes'
    ) as FormArray;
    tempTestCasesTypesArray.clear();
    testcasesTypes.forEach((testcaseType: any) => {
      tempTestCasesTypesArray.push(
        this.fb.group({
          inputType: [testcaseType.inputType],
          parameterName: [testcaseType.parameterName],
        })
      );
    });

    this.tempTestCasesTypesForm.patchValue({
      outputType: outputType,
    });
    this.reconstructTestCases();
    console.log(this.testCaseTypesForm.value);
    this.showTestCaseForm = false;
  }

  setEdit() {
    this.showTestCaseForm = true;
  }

  testcasesCancel() {
    const tempTestcases = this.tempTestCasesTypesForm.value.testcasesTypes;
    const tempOutputType = this.tempTestCasesTypesForm.value.outputType;

    if (!tempTestcases.length) {
      this.showTestCaseForm = true;
      return;
    }

    const mainTestCasesTypes = this.testCasesTypes;
    mainTestCasesTypes.clear();

    tempTestcases.forEach((testcaseType: any) => {
      mainTestCasesTypes.push(
        this.fb.group({
          inputType: [testcaseType.inputType, Validators.required],
          parameterName: [testcaseType.parameterName, Validators.required],
        })
      );
    });

    this.testCaseTypesForm.patchValue({
      outputType: tempOutputType,
    });

    this.showTestCaseForm = false;
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
              value: [field.value, field.validators],
              type: field.inputType,
            })
          )
        ),
      }),
      ...this.basicFields.map((field) =>
        this.fb.group({
          fieldName: [field.fieldName],
          value: [
            field.value,
            field.fieldName !== 'output'
              ? Validators.required
              : this.getValidators(
                  this.tempTestCasesTypesForm.get('outputType')?.value
                ),
          ],
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

    if (this.eachTestCaseForm.length > 0) {
      testcasesArray.push(this.AddNewTestCase());
    }
  }

  get testCasesTypes(): FormArray {
    return this.testCaseTypesForm.get('testcasesTypes') as FormArray;
  }

  get tempTestcaseTypes(): FormArray {
    return this.tempTestCasesTypesForm.get('testcasesTypes') as FormArray;
  }

  getTestCaseSpecificTypeAtIdx(idx: number) {
    const type = this.tempTestcaseTypes.at(idx).get('inputType');
    return type ? type.value : '';
  }

  getTestCaseSpecificParameterNameAtIdx(idx: number) {
    const parameter = this.tempTestcaseTypes.at(idx).get('parameterName');
    return parameter ? parameter.value : '';
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
    const testCase = this.TestCases.at(testIdx);
    if (!testCase) return this.fb.array([]);
    console.log(testCase.get('testcaseInputs'));
    return testCase.get('testcaseInputs') as FormArray;
  }

  getInputsArray(testIdx: number): FormArray {
    const testcaseInputs = this.getTestCaseInputsArray(testIdx);
    if (!testcaseInputs || testcaseInputs.length === 0)
      return this.fb.array([]);
    const firstGroup = testcaseInputs.at(0);
    if (!firstGroup) return this.fb.array([]);
    return firstGroup.get('inputs') as FormArray;
  }

  getFieldName(testIdx: number, inputIdx: number): string {
    const inputsArray = this.getInputsArray(testIdx);
    if (!inputsArray || inputIdx >= inputsArray.length) return '';
    const fieldNameControl = inputsArray.at(inputIdx).get('fieldName');
    return fieldNameControl ? fieldNameControl.value : '';
  }

  getFieldNameAndType(testIdx: number, inputIdx: number): string {
    const inputsArray = this.getInputsArray(testIdx);
    if (!inputsArray || inputIdx >= inputsArray.length) return '';
    const fieldTypeControl = inputsArray.at(inputIdx).get('type');

    const fieldNameControl = inputsArray.at(inputIdx).get('fieldName');
    return fieldNameControl
      ? `${fieldNameControl.value} (${fieldTypeControl?.value})`
      : '';
  }

  getSlicedControls(testIdx: number, slicingNumber: number) {
    const inputs = this.getTestCaseInputsArray(testIdx);
    if (!inputs) return [];

    if (slicingNumber === 1) {
      console.log(inputs.controls.slice(1));
      return inputs.controls.slice(1, inputs.length);
    } else {
      return inputs.controls.slice(0, 1);
    }
  }

  getFieldType(testIdx: number, inputIdx: number): string {
    const inputsArray = this.getInputsArray(testIdx);
    if (!inputsArray || inputIdx >= inputsArray.length) return 'text';
    const fieldNameControl = inputsArray.at(inputIdx).get('type');
    if (fieldNameControl?.value === 'boolean') {
      return 'boolean';
    } else if (fieldNameControl?.value === 'int') {
      return 'number';
    } else {
      return 'text';
    }
  }

  getBasicFieldType(testIdx: number, basicIdx: number): string {
    console.log(testIdx, basicIdx, 'in');
    let fieldName = this.basicFields[basicIdx].fieldName;
    if (!fieldName) {
      return "'text";
    }

    if (basicIdx === 0 && fieldName === 'output') {
      let outputType = this.tempTestCasesTypesForm.get('outputType')?.value;
      if (outputType) {
        if (outputType === 'int') {
          return 'number';
        }
        if (outputType === 'boolean') {
          return 'advanced-dropdown-boolean';
        }
      }
    }
    if (fieldName === 'Description') {
      return 'advanced-textarea';
    }
    if (fieldName === 'TestCase Type') {
      return 'advanced-dropdown';
    }
    if (fieldName === 'weightage') {
      return 'number';
    }
    return 'text';
  }

  getBasicFieldName(testIdx: number, basicIdx: number): string {
    if (basicIdx >= this.basicFields.length) return '';
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
