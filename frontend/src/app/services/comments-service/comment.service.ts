import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { COMMENTS_PER_PAGE } from '../../utils/constants';
import { Page } from '../../models/page.model';
import { COMMENTS } from '../../utils/constants';

@Injectable({ providedIn: 'root' })
export class CommentService {
    constructor(private http: HttpClient) { }

    getComments(chID: number, page: number) {
        return this.http.get<Page>(`${environment.apiUrl}/${COMMENTS}/by-page/${chID}/?page=${page}&size=${COMMENTS_PER_PAGE}&sort=id,ASC`);
    }

    postComment(chID: number, content: string, image: string) {
        const comment = { content: content, culturalHeritageID: chID };
        let formData = new FormData();
        formData.append('comment', new Blob([JSON.stringify(comment)], {
            type: "application/json"
        }));
        if (image) {
            formData.append("file", image);
        }

        return this.http.post<any>(`${environment.apiUrl}/${COMMENTS}`, formData);
    }

    deleteComment(chID: number) {
        return this.http.delete(`${environment.apiUrl}/${COMMENTS}/${chID}`);
    }
}