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
saved: boolean = false; 
showForm: boolean = false;
question='';
output='';
weightage='';
count:number=0;
testtype=['Private','Public'];
types=['string','int','boolean'];
codingquestion: any[]=[];
onsave() {
  this.saved = true;
}
oncancel() {
  this.showForm = false;
  this.inputs = [];
  this.count = 0;
}
inputs:any[]=[];
ongenerate(){
  this.inputs=[];
  for(let i = 0; i < this.count; i++)
  this.inputs.push({
    type:null,
    parameter:'',
})
this.saved = false;
  this.showForm = true;
}
Inputform:any[]=[{
  input1:'',
    input2:'',
    input3:'',
    testtype:null,
    description:'',
}];
add(){
  this.Inputform.push({
    input1:'',
    input2:'',
    input3:'',
    testtype:null,
    description:'',
})
}
del(index:number){
  this.Inputform.splice(index, 1);
  console.log('ok');
}
}
