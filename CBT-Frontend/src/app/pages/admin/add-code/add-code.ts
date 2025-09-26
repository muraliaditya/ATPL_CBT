import { Component } from '@angular/core';
import { FloatLabel } from 'primeng/floatlabel';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Select } from 'primeng/select';
import { InputText } from "primeng/inputtext";
@Component({
  selector: 'app-add-code',
  imports: [Select, FloatLabel, FormsModule, CommonModule, InputText, ReactiveFormsModule],
  templateUrl: './add-code.html',
  styleUrl: './add-code.css'
})
export class AddCode {
saved: boolean = false; 
show:boolean=false;
showForm: boolean = false;
question='';
method='';
description:'' | undefined;
output='';
weightage='';
count:number=0;
testtype=['Private','Public'];
types=['string','int','boolean'];
onsave() {
  this.saved = true;
}
oncancel() {
  this.showForm = false;
  this.inputs = [];
  this.count = 0;
}
onEdit(){
  this.show=true;
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
inputval:any[]=[];
Inputform:any[]=[];
add(){
  this.Inputform.push({
    inputval:[],
    input3:'',
    output:'',
    testtype:null,
    description:'',
})
}
del(index:number){
  this.Inputform.splice(index, 1);
  console.log('ok');
}

}
