import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule,FormGroup,FormBuilder, Validators } from '@angular/forms';
import { FloatLabel } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
@Component({
  selector: 'app-admin-login',
  imports: [CommonModule,ReactiveFormsModule,FormsModule,FloatLabel,InputTextModule],
  templateUrl: './admin-login.html',
  styleUrl: './admin-login.css'
})
export class AdminLogin {
Login!: FormGroup;
  constructor(private fb: FormBuilder) {}
  ngOnInit(): void {
    this.Login = this.fb.group({
      name: ['',[Validators.required,Validators.minLength(5),Validators.pattern(/^[a-zA-Z0-9_]+$/)]],
      password: ['', [Validators.required,Validators.minLength(8)]],
    });
  }
  submit(): void {
    if(this.Login.valid){
      alert("Logged In");
      this.Login.reset()
    }
}
}
