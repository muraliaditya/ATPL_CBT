import { Component } from '@angular/core';
import { FloatLabel } from 'primeng/floatlabel';
import { Select } from 'primeng/select';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { data } from '../../../models/admin/admin';
import { InputText } from 'primeng/inputtext';
import { AdminHeader } from '../../../components/UI/admin-header/admin-header';
@Component({
  selector: 'app-dashboard-viewmcq',
  imports: [InputText,AdminHeader,FloatLabel,Select,FormsModule,CommonModule],
  templateUrl: './dashboard-viewmcq.html',
  styleUrl: './dashboard-viewmcq.css'
})
export class DashboardViewmcq {
  editingId:string|null=null;
  Section: string[] = ['Geography','Literature','Science'];
  onEdit(q: data) {
      this.editingId = q.mcqQuestionId;
    }
    onSave(q: data) {
      console.log('Saved:', q);
      this.editingId = null;
    }
    onCancel() {
      this.editingId = null;
    }
    onDelete(q:data){
      this.Mcq = this.Mcq.filter(c => c.mcqQuestionId !== q.mcqQuestionId);
    }
 Mcq:data[]= [{
    "mcqQuestionId": "mcq_12345",
    "question": "What is the chemical symbol for gold?",
    "option1": "AU",
    "option2": "CU",
    "option3": "AG", 
    "option4": "FE",
    "answerKey": "AU",
    "weightage": 2,
    "section":"Mcq",
  }]
}
