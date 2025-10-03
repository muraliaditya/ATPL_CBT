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
import { IntegerArrayValidate } from '../../../utils/custom-validators/integer-array-validator';
import { StringArrayValidate } from '../../../utils/custom-validators/string-array-validator';
import { NaNCheckValidate } from '../../../utils/custom-validators/nan-check';

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
export class AddCodingQuestion implements OnInit {
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
  difficultyOptions: string[] = ['Easy', 'Medium', 'Hard'];

  inputTypesCount: number = 0;
  value3: string = '';
  showTestCaseForm = true;
  booleanOptions: string[] = ['true', 'false'];

  constructor(private fb: FormBuilder) {
    this.testCaseTypesForm = fb.group({
      question: ['', Validators.required],
      weightage: [0, [Validators.required, Validators.min(10)]],
      description: ['', Validators.required],
      difficulty: ['', Validators.required],
      methodSignature: ['', Validators.required],
      outputType: ['', Validators.required],
      javaBoilerPlateCode: ['', Validators.required],
      pythonBoilerPlateCode: ['', Validators.required],
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
    value: string | number;
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
      value: testcase.inputType === 'int' ? 0 : '',
      validators: this.getValidators(testcase.inputType),
      inputType: testcase.inputType,
    }));
    if (newFields.length) {
      for (let idx = newFields.length - 1; idx >= 0; idx--) {
        this.eachTestCaseForm.unshift(newFields[idx]);
      }
    }
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
    this.AddTestCaseTypesCount(value.value.myNumber);
  }

  getTestCaseInputsArray(testIdx: number): FormArray {
    const testCase = this.TestCases.at(testIdx);
    if (!testCase) return this.fb.array([]);
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
  submitQuestion() {
    console.log(this.testCaseTypesForm.value);

    const testcasesArray = this.testCaseTypesForm.get('testcases') as FormArray;
    for (let testIdx = 0; testIdx < testcasesArray.length; testIdx++) {
      const testcaseGroup = testcasesArray.at(testIdx) as FormGroup;
      const testcaseInputsArray = testcaseGroup.get(
        'testcaseInputs'
      ) as FormArray;

      const inputGroup = testcaseInputsArray.at(0) as FormGroup;
      const inputsArr = inputGroup.get('inputs') as FormArray;

      console.log(`  testcaseInputs group #:`);

      for (let inputIdx = 0; inputIdx < inputsArr.length; inputIdx++) {
        const inputControl = inputsArr.at(inputIdx) as FormGroup;
        const fieldName = inputControl.get('fieldName')?.value;
        const value = inputControl.get('value')?.value;
        const type = inputControl.get('type')?.value;

        console.log(
          `    Input #${inputIdx} - fieldName: ${fieldName}, value: ${value}, type: ${type}`
        );
      }
    }
  }

  getBasicFieldType(testIdx: number, basicIdx: number): string {
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

  ngOnInit() {
    const initialData = {
      question: 'Sample question?',
      weightage: 20,
      description: 'This is a description',
      methodSignature: 'void sampleMethod()',
      outputType: 'int',
      javaBoilerPlateCode: '// Java code here',
      pythonBoilerPlateCode: '# Python code here',
      testcasesTypes: [
        { inputType: 'int', parameterName: 'param1' },
        { inputType: 'string', parameterName: 'param2' },
      ],
      testcases: [],
    };

    this.testCaseTypesForm.patchValue({
      question: initialData.question,
      weightage: initialData.weightage,
      description: initialData.description,
      methodSignature: initialData.methodSignature,
      outputType: initialData.outputType,
      javaBoilerPlateCode: initialData.javaBoilerPlateCode,
      pythonBoilerPlateCode: initialData.pythonBoilerPlateCode,
    });

    const testcasesTypesArray = this.testCaseTypesForm.get(
      'testcasesTypes'
    ) as FormArray;
    testcasesTypesArray.clear();
    initialData.testcasesTypes.forEach((tc) => {
      testcasesTypesArray.push(
        this.fb.group({
          inputType: [tc.inputType, Validators.required],
          parameterName: [tc.parameterName, Validators.required],
        })
      );
    });

    const testcasesArray = this.testCaseTypesForm.get('testcases') as FormArray;
    // testcasesArray.clear();
  }
}
