import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthService {
    constructor(private http: HttpClient) { }

    register(user) {
        return this.http.post(`${environment.apiUrl}/authenticated-users`, user);
    }

    login() {

    }

    isLoggedIn() {
        //ovo izmeniti da proverava postojanje JWT tokena
        return false;
    }
}