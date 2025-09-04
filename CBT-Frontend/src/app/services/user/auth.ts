import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../models/user/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private DB_LINK: string = 'http://localhost:8080';
  constructor(private _http: HttpClient) {}

  authenticateUser(userData: User) {
    return this._http.post<Observable<any>>(
      `${this.DB_LINK}/api/auth/login`,
      userData
    );
  }
}
