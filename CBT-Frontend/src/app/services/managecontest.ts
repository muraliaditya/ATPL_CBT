import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { Contest } from '../models/admin/admin';
@Injectable({
  providedIn: 'root'
})
export class Managecontest {
  private apiUrl = environment.apiUrl;
  private ManageContest = 'api/v1/admin/mcqs';
  private token =
    'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTdXBlckFkbWluNDIiLCJpYXQiOjE3NTkyODI0MjgsImV4cCI6MTc1OTI4NjAyOH0.sUqjJthkl33W8ZtemstCYzVKSPQWj2wr4zPyh9FQQJ4';
  constructor(private _http: HttpClient) {}
 buildUrlWithParams(
  baseUrl: string,
  values: any[],
  paramNames: string[]
): string {
  if (!Array.isArray(values) || !Array.isArray(paramNames) || values.length !== paramNames.length) {
    console.log("Values and paramNames must be arrays of the same length");
  }

  const params: string[] = [];

  for (let i = 0; i < values.length; i++) {
    const value = values[i];
    const param = paramNames[i];
    if (value !== undefined && value !== null && value !== "") {
      params.push(`${encodeURIComponent(param)}=${encodeURIComponent(value)}`);
    }
  }

  return params.length === 0
    ? baseUrl
    : `${baseUrl}?${params.join("&")}`;
}
  searchcontest(ContestName: string, ContestId: string,choice:string) {
    console.log('d')
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.token}`,
    });
    let baseurl=`${this.apiUrl}/${this.ManageContest}/search`;
    let values=[ContestName,ContestId,choice]
    let paramNames=['ContestName','ContestId','choice']
    console.log(this.buildUrlWithParams(baseurl,values,paramNames))
    return this._http.get<Contest>(
      `${this.apiUrl}/${this.ManageContest}/search?ContestName=${ContestName}&ContestId=${ContestId}&choice=${choice}`,
      { headers }
    );
  }
}
