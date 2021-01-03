import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Rating } from '../../models/rating.model';

@Injectable({ providedIn: 'root' })
export class RatingService {
    constructor(private http: HttpClient) { }

    getUserRating(chID: number) {
        return this.http.get<Rating>(`${environment.apiUrl}/ratings/?chID=${chID}`);
    }

    postRating(chID: number, grade: number) {
        return this.http.post(`${environment.apiUrl}/ratings`, { 
            grade: grade, 
            culturalHeritageId : chID
        });
    }

    updateRating(ratingID: number, chID: number, grade: number) {
        return this.http.put(`${environment.apiUrl}/ratings/${ratingID}`, { 
            grade: grade, 
            culturalHeritageId : chID
        });
    }
}