import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CHType } from "src/app/models/ch-type.model";
import { Page } from "src/app/models/page.model";
import { environment } from "src/environments/environment";

const REST_ENDPOINT = {
    GET: '/ch-types/by-page',
};

@Injectable({providedIn: 'root'})
export class CHTypeService {
    constructor(private httpClient: HttpClient){}

    getTypes(page: number): Observable<Page> {
        return this.httpClient.get<Page>(`${environment.apiUrl}${REST_ENDPOINT.GET}/?page=${page}`);
    }

}
