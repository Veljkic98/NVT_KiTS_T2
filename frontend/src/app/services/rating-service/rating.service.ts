import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Rating } from '../../models/rating.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class RatingService {
    constructor(private http: HttpClient) { }

    getUserRating(chID: number): Observable<Rating> {
        return this.http.get<Rating>(`${environment.apiUrl}/ratings/?chID=${chID}`);
    }

    postRating(chID: number, grade: number): Observable<any> {
        return this.http.post(`${environment.apiUrl}/ratings`, {
            grade,
            culturalHeritageId : chID
        });
    }

    updateRating(ratingID: number, chID: number, grade: number): Observable<any> {
        return this.http.put(`${environment.apiUrl}/ratings/${ratingID}`, {
            grade,
            culturalHeritageId : chID
        });
    }
}
