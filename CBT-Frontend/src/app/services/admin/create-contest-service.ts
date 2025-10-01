import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { ContestMCQQuestion } from '../../models/admin/contest';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class CreateContestService {
  private apiUrl = environment.apiUrl;
  private createContestURL = 'api/v1/admin/mcqs';
  private token =
    'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTdXBlckFkbWluNDIiLCJpYXQiOjE3NTkzMTYwMzQsImV4cCI6MTc1OTMxOTYzNH0.q-6tfzbZAZ6YZNwzQzL7nu5_icJ6qH0MmfjqjX4g5q0';
  constructor(private _http: HttpClient) {}

  regenerateMcqQuestion(sectionName: string, currentQuestionId: string) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this._http.get<ContestMCQQuestion>(
      `${this.apiUrl}/${this.createContestURL}/regenerate?sectionName=${sectionName}&currentQuestionId=${currentQuestionId}`,
      { headers }
    );
  }
  regenerateNMcqQuestions(sectionName: string, count: number) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this._http.get<ContestMCQQuestion[]>(
      `${this.apiUrl}/${this.createContestURL}/random?sectionName=${sectionName}&count=${count}`,
      { headers }
    );
  }
}
