import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { environment } from '../../../../environments/environment.development';
import { contestResultsResponse } from '../../../models/admin/participant';

@Injectable({
  providedIn: 'root',
})
export class ResultService {
  private apiUrl = environment.apiUrl;
  private createContestURL = 'api/v1/admin/contests';
  private token =
    'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTdXBlckFkbWluNDIiLCJpYXQiOjE3NTkzMTYwMzQsImV4cCI6MTc1OTMxOTYzNH0.q-6tfzbZAZ6YZNwzQzL7nu5_icJ6qH0MmfjqjX4g5q0';
  constructor(private _http: HttpClient) {}

  getContestResults(contestId: string) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    return this._http.get<contestResultsResponse>(
      `${this.apiUrl}/${this.createContestURL}/results/${contestId}`,
      { headers }
    );
  }
}
