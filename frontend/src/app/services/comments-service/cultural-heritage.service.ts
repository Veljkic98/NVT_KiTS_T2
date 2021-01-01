import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { COMMENTS_PER_PAGE } from '../../utils/constants';
import { Page } from '../../models/page.model';

@Injectable({ providedIn: 'root' })
export class CommentService {
    constructor(private http: HttpClient) { }

    getComments(chID, page) {
        return this.http.get<Page>(`${environment.apiUrl}/comments/by-page/${chID}/?page=${page}&size=${COMMENTS_PER_PAGE}&sort=id,ASC`);
    }

    getCommentImage(imageUri) {
        return this.http.get(imageUri,  {responseType: 'blob'});
    }
}