import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MinLengthValidator, ReactiveFormsModule } from '@angular/forms';
import { FormsModule,FormGroup,FormBuilder, Validators } from '@angular/forms';
import { min } from 'rxjs';
@Component({
  selector: 'app-developer-login',
  imports: [CommonModule,ReactiveFormsModule,FormsModule],
  templateUrl: './developer-login.html',
  styleUrl: './developer-login.css'
})
export class DeveloperLogin {
  Login!: FormGroup;
  constructor(private fb: FormBuilder) {}
  ngOnInit(): void {
    this.Login = this.fb.group({
      name: ['', Validators.required],
      password: ['', Validators.required,Validators.minLength(8)]
    });
  }
  submit(): void {
    if(this.Login.valid){
      alert("Developer logged In");
      this.Login.reset()
    }
}
}
