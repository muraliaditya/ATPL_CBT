import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule,FormGroup,FormBuilder, Validators } from '@angular/forms';
@Component({
  selector: 'app-admin-login',
  imports: [CommonModule,ReactiveFormsModule,FormsModule],
  templateUrl: './admin-login.html',
  styleUrl: './admin-login.css'
})
export class AdminLogin {
Login!: FormGroup;
  constructor(private fb: FormBuilder) {}
  ngOnInit(): void {
    this.Login = this.fb.group({
      name: ['', Validators.required],
      password: ['', Validators.required]
    });
  }
  submit(): void {
    if(this.Login.valid){
      alert("Admin logged In");
      this.Login.reset()
    }
}
}
