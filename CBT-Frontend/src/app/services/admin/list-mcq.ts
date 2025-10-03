import { Injectable } from '@angular/core';
import { HttpClient,HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class ListMcq {
  private baseUrl = 'http://localhost:8080/api/v1/admin/mcqs'; 

  constructor(private http: HttpClient) {}
  getMcqsbySectionName(sectionName:string):Observable<any[]>{
    const params=new HttpParams().set('sectionName',sectionName)
    return this.http.get<any>('this.baseUrl',{params})
  }
}
