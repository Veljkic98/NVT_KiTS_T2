import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { News, NewsRequest } from "src/app/models/news.model";
import { Page, PageEnchanced } from "src/app/models/page.model";
import { environment } from "src/environments/environment";
import { NEWS_PER_PAGE } from '../../utils/constants';


const REST_ENDPOINT = {
    GET: '/news/by-page/',
    GET_ONE: '/news/',
    ADD_ONE: '/news',
    DELETE: '/news/',
}

@Injectable({ providedIn: 'root' })
export class NewsService {
    constructor(private httpClient: HttpClient) { }

    getNews(chID: number, page: number): Observable<PageEnchanced<News>> {
        // return this.httpClient.get<Page>(`${environment.apiUrl}${REST_ENDPOINT.GET}${chID}/?page=${page}&size=${NEWS_PER_PAGE}`);
        return this.httpClient.get<PageEnchanced<News>>(`${environment.apiUrl}${REST_ENDPOINT.GET}${chID}/?page=${page}&size=${NEWS_PER_PAGE}&sort=id,ASC`);
    }

    getOne(chID: number) {
        return this.httpClient.get<any>(`${environment.apiUrl}${REST_ENDPOINT.GET_ONE}${chID}`);
    }

    deleteNews(id: number): Observable<Object> {
        return this.httpClient.delete(`${environment.apiUrl}${REST_ENDPOINT.DELETE}${id}`);
    }

    update(news: News) {

        // return this.httpClient.put(`${environment.apiUrl}${REST_ENDPOINT.GET_ONE}${id}`, news);
        var id = news.id;
        var imageUri = news.imageUri;
        var heading = news.heading;
        var content = news.content;
        var culturalHeritageID = news.culturalHeritageID;
        var adminID = news.adminID;

        const ch = { heading, content, culturalHeritageID, adminID }
        const formData = new FormData();
        formData.append('news', new Blob([JSON.stringify(ch)], {
            type: 'application/json'
        }));
        if (imageUri) {
            formData.append('file', imageUri);
        }

        return this.httpClient.put(`${environment.apiUrl}${REST_ENDPOINT.GET_ONE}${id}`, formData);
    }

    add(news: News2) {
        var imageUri = news.imageUri;
        var heading = news.heading;
        var content = news.content;
        var culturalHeritageID = news.culturalHeritageID;
        var adminID = news.adminID;

        const ch = { heading, content, culturalHeritageID, adminID }
        const formData = new FormData();
        formData.append('news', new Blob([JSON.stringify(ch)], {
            type: 'application/json'
        }));
        if (imageUri) {
            formData.append('file', imageUri);
        }

        return this.httpClient.post(`${environment.apiUrl}${REST_ENDPOINT.ADD_ONE}`, formData);
    }

}