import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CHSubtype } from 'src/app/models/ch-subtype.model';
import { environment } from 'src/environments/environment';
import { CH_SUBTYPES } from '../../utils/constants';

const REST_ENDPOINT = {
    GET: '/ch-subtypes/by-page',
    DELETE: '/ch-subtypes/',
};

@Injectable({providedIn: 'root'})
export class CHSubtypeService {
    constructor(private httpClient: HttpClient){}

    deleteSubtype(id: number): Observable<object> {
        return this.httpClient.delete<object>(`${environment.apiUrl}${REST_ENDPOINT.DELETE}${id}`);
    }

    editSubtype(subtype: CHSubtype): Observable<any> {
        return this.httpClient.put<any>(`${environment.apiUrl}/${CH_SUBTYPES}/${subtype.id}`, subtype);
    }

    getAll(): Observable<CHSubtype[]> {
        return this.httpClient.get<CHSubtype[]>(`${environment.apiUrl}/${CH_SUBTYPES}`);
    }

    add(subtype: CHSubtype): Observable<CHSubtype> {
        return this.httpClient.post<CHSubtype>(`${environment.apiUrl}/${CH_SUBTYPES}`, subtype);
    }
}
