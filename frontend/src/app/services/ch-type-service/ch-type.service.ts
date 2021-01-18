import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CHType } from 'src/app/models/ch-type.model';
import { Page } from 'src/app/models/page.model';
import { environment } from 'src/environments/environment';
import { CH_TYPES } from 'src/app/utils/constants';

const REST_ENDPOINT = {
    GET: '/ch-types/by-page',
};

@Injectable({providedIn: 'root'})
export class CHTypeService {
    constructor(private httpClient: HttpClient){}

    getTypes(page: number): Observable<Page> {
        return this.httpClient.get<Page>(`${environment.apiUrl}${REST_ENDPOINT.GET}/?page=${page}`);
    }

    deleteType(typeID: number): Observable<object> {
        return this.httpClient.delete(`${environment.apiUrl}/${CH_TYPES}/${typeID}`);
    }

    editType(type: CHType): Observable<any> {
        return this.httpClient.put<any>(`${environment.apiUrl}/${CH_TYPES}/${type.id}`, type);
    }

}
