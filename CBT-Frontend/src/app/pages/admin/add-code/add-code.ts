import { Component } from '@angular/core';
import { FloatLabel } from 'primeng/floatlabel';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Select } from 'primeng/select';
import { InputText } from "primeng/inputtext";
@Component({
  selector: 'app-add-code',
  imports: [Select, FloatLabel, FormsModule, CommonModule, InputText],
  templateUrl: './add-code.html',
  styleUrl: './add-code.css'
})
export class AddCode {
  input1='';
  input2='';
  outputf='';
output='';
question='';
weightage='';
count='';
testtype=['Private','Public'];
types=['string','int','boolean'];
codingquestion: any[]=[];
inputs:any[]=[];
ongenerate(){
  this.inputs.push({
    type:null,
    parameter:'',
})
}
}
