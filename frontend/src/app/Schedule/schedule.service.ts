import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { ScheduleTo } from './schedule.model';
import { HttpParams } from '@angular/common/http';
import { HttpResponse } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})

export class ScheduleService {

    apiUrl: string = 'http://localhost:8080/api';
    headers = new HttpHeaders().set('Content-Type', 'application/json');
  
    constructor(private http: HttpClient) { }
  
    // Create
    createSchedules(data): Observable<any> {
      let API_URL = `${this.apiUrl}/create/schedules`;
      return this.http.post(API_URL, data)
        .pipe(
          catchError(this.error)
        )
    }
  
    // Read
    showSchedules(): Observable<any> {
    //   let params = new HttpParams();
    //   params = params.append('user', user);
      return this.http.get<ScheduleTo[]>(`${this.apiUrl}/get/schedules`)
        .pipe(
          catchError(this.error)
        )
    }

    // Export data to Excel file
    exportExcel(data: ScheduleTo[]): Observable<any>{
      let API_URL = `${this.apiUrl}/schedules/download`;
      let headers = new HttpHeaders();
      headers = headers.append('Accept', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8');
      return this.http.post(API_URL, data,{
        headers: headers,
        observe: 'response',
        responseType: 'blob'
      })
        .pipe(
          catchError(this.error)
        )
    }
  
    // // Update
    // updateTask(id, data): Observable<any> {
    //   let API_URL = `${this.apiUrl}/update-task/${id}`;
    //   return this.http.put(API_URL, data, { headers: this.headers }).pipe(
    //     catchError(this.error)
    //   )
    // }
  
    // // Delete
    // deleteTask(id): Observable<any> {
    //   var API_URL = `${this.apiUrl}/delete-task/${id}`;
    //   return this.http.delete(API_URL).pipe(
    //     catchError(this.error)
    //   )
    // }
  
    // Handle Errors 
    error(error: HttpErrorResponse) {
      let errorMessage = '';
      if (error.error instanceof ErrorEvent) {
        errorMessage = error.error.message;
      } else {
        errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
      }
      console.log(errorMessage);
      return throwError(errorMessage);
    }
  
}