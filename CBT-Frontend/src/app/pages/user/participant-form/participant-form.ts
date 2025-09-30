import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { FloatLabel } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ProgressSpinner } from 'primeng/progressspinner';
import { finalize } from 'rxjs';
import { Toast } from 'primeng/toast';
import { CommonModule } from '@angular/common';
import { Select } from 'primeng/select';
import { InputNumberModule } from 'primeng/inputnumber';

@Component({
  selector: 'app-participant-form',
  imports: [
    ReactiveFormsModule,
    InputTextModule,
    FloatLabel,
    CommonModule,
    ReactiveFormsModule,
    ButtonModule,
    Select,
    ProgressSpinner,
    InputNumberModule,
    Toast,
  ],

  templateUrl: './participant-form.html',
  styleUrl: './participant-form.css',
  providers: [MessageService],
})
export class ParticipantForm {
  category: 'Student' | 'Experienced' = 'Student';
  participantForm: FormGroup;
  employeeForm: FormGroup;
  highestDegreeOptions: string[] = [
    'B.Tech',
    'M.Tech',
    'B.E',
    'B.SC',
    'M.Sc',
    'BCA',
    'MCA',
  ];

  constructor(private messageService: MessageService, private fb: FormBuilder) {
    this.participantForm = this.fb.group({
      name: ['', Validators.required],
      collegeRedgNo: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      collegeName: ['', Validators.required],
      highestDegree: ['', Validators.required],
      percentage: ['', [Validators.required, Validators.min(50)]],
    });
    this.employeeForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      designation: ['', Validators.required],
      currentCompanyName: ['', Validators.required],
      overallExperience: [
        null,
        [Validators.required, Validators.max(20), Validators.min(0.1)],
      ],
    });
  }
  onSubmit() {}
}
