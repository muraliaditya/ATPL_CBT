import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class Loginportal {
  private apiUrl = environment.apiUrl;
  private login_url = environment.LOGIN_URL;
  constructor(private _http: HttpClient) {}
  submit(credentials: Object): Observable<any> {
    return this._http.post(`${this.apiUrl}/${this.login_url}`, credentials);
  }
}