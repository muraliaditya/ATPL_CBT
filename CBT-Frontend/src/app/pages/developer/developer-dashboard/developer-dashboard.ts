import { Component } from '@angular/core';
import { data } from '../../../models/developer/developer';
import { mcqSections } from '../../../models/admin/admin';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-developer-dashboard',
  imports: [FormsModule],
  templateUrl: './developer-dashboard.html',
  styleUrl: './developer-dashboard.css'
})
export class DeveloperDashboard {
  quote="d";
  mcqs: mcqSections[]= [
        {
        mcqQuestionId: 'mcq001',
        question: 'What is the capital of France?',
        option1: 'Berlin',
        option2: 'Madrid',
        option3: 'Paris',
        option4: 'Rome',
        answerKey: 'Paris',
        weightage: 2,
        section: 'Geography',
      },
  ]
}
