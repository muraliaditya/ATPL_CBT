import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { DynamicLayout } from "../../../components/UI/dynamic-layout/dynamic-layout";
import { AdminHeader } from '../../../components/UI/admin-header/admin-header';
import { FormGroup,FormBuilder,FormArray,Validators } from '@angular/forms';
import { Editor } from '../../../components/admin/editor/editor';
@Component({
  selector: 'app-add-mcq-question',
  imports: [Editor,ReactiveFormsModule,AdminHeader,FormsModule, CommonModule, DynamicLayout],
  templateUrl: './add-mcq-question.html',
  styleUrl: './add-mcq-question.css'
})
export class AddMcqQuestion {
 mcqForm: FormGroup;

  category = ['Aptitude','Quantitative'];
  answerKey = ['Option 1', 'Option 2', 'Option 3', 'Option 4'];

  constructor(private fb: FormBuilder) {
    this.mcqForm = this.fb.group({
      questions: this.fb.array([this.createQuestion()])
    });
  }
  get questions(): FormArray {
    return this.mcqForm.get('questions') as FormArray;
  }
  private createQuestion(): FormGroup {
    return this.fb.group({
      question: ['', Validators.required],
      category: [''],
      options: this.fb.array([
        this.fb.control('', Validators.required),
        this.fb.control('', Validators.required),
        this.fb.control('', Validators.required),
        this.fb.control('', Validators.required)
      ]),
      weightage: ['', Validators.required],
      answer: ['']
    });
  }

  addQuestion(): void {
    this.questions.push(this.createQuestion());
  }

  removeQuestion(index: number): void {
    this.questions.removeAt(index);
  }

  getOptions(i: number): FormArray {
    return this.questions.at(i).get('options') as FormArray;
  }

  submit(): void {
    if (this.mcqForm.valid) {
      console.log('Submitted:', this.mcqForm.value);
    } else {
      this.mcqForm.markAllAsTouched();
    }
  }
}