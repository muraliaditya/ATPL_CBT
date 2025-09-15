import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FloatLabel } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { Auth } from '../../../services/user/auth';

import {
  ReactiveFormsModule,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MessageService } from 'primeng/api';

import { ProgressSpinner } from 'primeng/progressspinner';
import { User } from '../../../models/user/user';
import { finalize } from 'rxjs';
import { Toast } from 'primeng/toast';

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
    Toast,
  ],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
  providers: [MessageService],
})
export class LoginPage {
  userForm = new FormGroup({
    collegeUid: new FormControl('', [Validators.required]),
    collegeRollNo: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
  });

  constructor(
    private authService: Auth,
    private messageService: MessageService
  ) {}
  isSubmitting: boolean = false;
  onSubmit() {
    const formData: User = {
      collegeUid: this.userForm.value.collegeUid!,
      collegeRollNo: this.userForm.value.collegeRollNo!,
      email: this.userForm.value.email!,
    };
 

    this.isSubmitting = true;
    this.authService
      .authenticateUser(formData)
      .pipe(
        finalize(() => {
          this.isSubmitting = false;
        })
      )
      .subscribe({
        next: (data) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Success',
            detail: 'successfully registered! ',
          });
        },
        error: (error) => {
          console.log(this.messageService);
          console.log(error);
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Not eligible to take test',
          });
        },
      });
  }
}
