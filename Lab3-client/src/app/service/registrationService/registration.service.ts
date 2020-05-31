import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http: HttpClient) { }

  registerUser(user: User): Observable<User> {
    console.log("registration");
    return this.http.post<User>(environment.registerService, user);
  }

  logout(user:User){
    this.http.post(environment.logoutService,user);
  }
}
