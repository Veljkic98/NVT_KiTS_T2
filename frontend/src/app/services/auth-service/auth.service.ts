import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { User } from '../../models/user.model';
import { AUTHENTICATED_USERS } from '../../utils/constants';

@Injectable({ providedIn: 'root' })
export class AuthService {
    constructor(private http: HttpClient) { }

    register(user) {
        return this.http.post(`${environment.apiUrl}/${AUTHENTICATED_USERS}`, user);
    }

    login(email: string, password: string): Observable<Object> {
        return this.http.post(`${environment.hostUrl}auth/login`, { username: email, password });        
    }

    logout(): void {
      localStorage.removeItem("user");
    }
  
    getRole(): string {    
      return localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user"))['roles'] : "INVALID";
    }

    isLoggedIn(): boolean {
        return localStorage.getItem("user") !== null;
    }

    verify(id) {
        return this.http.get(`${environment.apiUrl}/${AUTHENTICATED_USERS}/verify/${id}`)
    }

    getProfile() {
        return this.http.get<User>(`${environment.apiUrl}/${AUTHENTICATED_USERS}/me`)
    }
}