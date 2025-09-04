import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FloatLabel } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';

import {
  ReactiveFormsModule,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ProgressSpinner } from 'primeng/progressspinner';

@Component({
  selector: 'app-login-page',
  imports: [
    FormsModule,
    InputTextModule,
    FloatLabel,
    CommonModule,
    ReactiveFormsModule,
    ButtonModule,
    ProgressSpinner,
  ],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage {
  userForm = new FormGroup({
    collegeUid: new FormControl('', [Validators.required]),
    collegeRegNo: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
  });
  isSubmitting: boolean = false;
  onSubmit() {
    console.log(this.userForm.value);
    this.isSubmitting = true;
    setTimeout(() => {
      this.isSubmitting = false;
    }, 3000);
  }
}
