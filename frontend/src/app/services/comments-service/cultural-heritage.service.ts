import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
//import { CulturalHeritage } from '../../models/cultural-heritage.model';

@Injectable({ providedIn: 'root' })
export class CommentService {
    constructor(private http: HttpClient) { }

    getComments(chID) {
        return this.http.get(`${environment.apiUrl}/comments/${chID}`);
    }

    getCommentImage(imageUri) {
        return this.http.get(imageUri,  {responseType: 'blob'});
    }
}