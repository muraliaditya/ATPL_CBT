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

@Component({
  selector: 'app-participant-form',
  imports: [
    ReactiveFormsModule,
    InputTextModule,
    FloatLabel,
    CommonModule,
    ReactiveFormsModule,
    ButtonModule,
    ProgressSpinner,
    Toast,
  ],

  templateUrl: './participant-form.html',
  styleUrl: './participant-form.css',
  providers: [MessageService],
})
export class ParticipantForm {
  participantForm: FormGroup;

  constructor(private messageService: MessageService, private fb: FormBuilder) {
    this.participantForm = this.fb.group({
      name: ['', Validators.required],
      collegeRedgNo: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      collegeName: ['', Validators.required],
      highestDegree: ['', Validators.required],
      percentage: ['', Validators.required, Validators.min(50)],
    });
  }
  onSubmit() {}
}
