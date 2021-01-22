import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Page } from "src/app/models/page.model";
import { environment } from "src/environments/environment";
import { NEWS_PER_PAGE } from '../../utils/constants';


const REST_ENDPOINT = {
    GET: '/news/by-page/',
    DELETE: '/news/',
}

@Injectable({providedIn: 'root'})
export class NewsService {
    constructor(private httpClient: HttpClient){}

    getNews(chID: number, page: number): Observable<Page> {
        // return this.httpClient.get<Page>(`${environment.apiUrl}${REST_ENDPOINT.GET}${chID}/?page=${page}&size=${NEWS_PER_PAGE}`);
        return this.httpClient.get<Page>(`${environment.apiUrl}${REST_ENDPOINT.GET}${chID}/?page=${page}&size=${NEWS_PER_PAGE}&sort=id,ASC`);
    }

    deleteNews(id: number): Observable<Object> {
        return this.httpClient.delete(`${environment.apiUrl}${REST_ENDPOINT.DELETE}${id}`);
    }

}