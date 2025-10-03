import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { codingQuestions } from '../../models/admin/admin';
import { codeListingResponse } from '../../models/admin/admin';
@Injectable({
  providedIn: 'root',
})
export class CodingService {
  private apiUrl = environment.apiUrl;
  private codingQuestionUrl = 'api/v1/admin/coding-questions';
  private token =
    'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTdXBlckFkbWluNDIiLCJpYXQiOjE3NTk0ODUyMzUsImV4cCI6MTc1OTQ4ODgzNX0.Rxaz56tI1dSpuYwuLcR4by9CFWI1hnUp95DFPzAMfq0';

  constructor(private _http: HttpClient) {}

  buildUrlWithParams(
    baseUrl: string,
    values: any[],
    paramNames: string[]
  ): string {
    if (
      !Array.isArray(values) ||
      !Array.isArray(paramNames) ||
      values.length !== paramNames.length
    ) {
      throw new Error(
        'Values and paramNames must be arrays of the same length'
      );
    }

    const params: string[] = [];

    for (let i = 0; i < values.length; i++) {
      const value = values[i];
      const param = paramNames[i];
      if (value !== undefined && value !== null && value !== '') {
        params.push(
          `${encodeURIComponent(param)}=${encodeURIComponent(value)}`
        );
      }
    }

    return params.length === 0 ? baseUrl : `${baseUrl}?${params.join('&')}`;
  }

  searchQuestions(questionSearch: string, page: number, pageSize: number) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    let questionsFetchUrl = `${this.apiUrl}/${this.codingQuestionUrl}/search`;
    if (questionSearch) {
      questionsFetchUrl = questionsFetchUrl + `?question=${questionSearch}`;
    }
    if (page) {
      questionsFetchUrl = questionsFetchUrl + `?page=${page}`;
    }
    let base = `${this.apiUrl}/${this.codingQuestionUrl}/search`;
    let values = [questionSearch, page, pageSize];
    let paramValues = ['question', 'page', 'size'];
    return this._http.get<codeListingResponse>(
      `${this.buildUrlWithParams(base, values, paramValues)}`,
      {
        headers,
      }
    );
  }
}
