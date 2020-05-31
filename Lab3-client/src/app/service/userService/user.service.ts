import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { AppAuthGuard } from '../AppAuthGuard';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private appAuthGuard: AppAuthGuard,
    private http: HttpClient) { }

  getCurrentUser(): User {
    return JSON.parse(localStorage.getItem('currentUser'))
  }

  setCurrentUsr(user: User) {
    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  logout() {
    if (this.appAuthGuard.isStillLoggedIn()) {
      this.appAuthGuard.doLogout();
      localStorage.clear();
    }
  }

  getUserByEmail(email: string): Observable<User[]> {
    return this.http.get<User[]>(environment.userService + email);
  }
}
