import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";

const REST_ENDPOINT = {
    GET: '/ch-subtypes/by-page',
    DELETE: '/ch-subtypes/',
};

@Injectable({providedIn: 'root'})
export class CHSubtypeService {
    constructor(private httpClient: HttpClient){}

    deleteSubtype(id: number): Observable<any> {
        return this.httpClient.delete<any>(`${environment.apiUrl}${REST_ENDPOINT.DELETE}${id}`);
    }

}
