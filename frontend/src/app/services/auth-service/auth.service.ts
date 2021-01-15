import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { User } from '../../models/user.model';
import { AUTHENTICATED_USERS } from '../../utils/constants';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
    constructor(private http: HttpClient) { }

    register(user): Observable<object> {
        return this.http.post(`${environment.apiUrl}/${AUTHENTICATED_USERS}`, user);
    }

    login(email: string, password: string): Observable<object> {
        return this.http.post(`${environment.hostUrl}auth/login`, { username: email, password });
    }

    logOut(): void {
      localStorage.removeItem('user');
    }

    getRole(): string {
      return localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')).role : 'INVALID';
    }

    getId(): number {
        return localStorage.getItem("user") ? JSON.parse(localStorage.getItem('user'))['id'] : 'INVALID';
    }

    isLoggedIn(): boolean {
        return localStorage.getItem('user') !== null;
    }

    verify(id): Observable<object> {
        return this.http.get(`${environment.apiUrl}/${AUTHENTICATED_USERS}/verify/${id}`);
    }

    getProfile(): Observable<User> {
        return this.http.get<User>(`${environment.apiUrl}/${AUTHENTICATED_USERS}/me`);
    }
    getSubscriptions(): Observable<Array<CulturalHeritage>> {
        return this.http.get<Array<CulturalHeritage>>(`${environment.apiUrl}/${AUTHENTICATED_USERS}/me/subscriptions`);
    }

}
