import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule,FormGroup,FormBuilder, Validators } from '@angular/forms';
import { FloatLabel } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { Loginportal } from '../../../services/admin/loginportal';
@Component({
  selector: 'app-admin-login',
  imports: [CommonModule,ReactiveFormsModule,FormsModule,FloatLabel,InputTextModule],
  templateUrl: './admin-login.html',
  styleUrl: './admin-login.css'
})
export class AdminLogin {
message : string ='';
Login!: FormGroup;
Username='';
Password='';
  constructor(private fb: FormBuilder,
    private login:Loginportal) {}
  ngOnInit(): void {
    this.Login = this.fb.group({
      username: ['',[Validators.required,Validators.minLength(5),Validators.pattern(/^[a-zA-Z0-9_]+$/)]],
      password: ['', [Validators.required,Validators.minLength(8)]],
    });
  }
  submit(): void {
    console.log(this.Login.value)
    this.login.submit(this.Login.value).subscribe({
      next:(res)=>{
        console.log('login',res);
        this.message = res.message;
      },
      error:(err)=>{
        console.log('failed',err)
      }
    })
  }
    
}
