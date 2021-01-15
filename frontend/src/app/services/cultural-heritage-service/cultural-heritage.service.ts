import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { CulturalHeritage } from '../../models/cultural-heritage.model';
import { CULTURAL_HERITAGES } from '../../utils/constants';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CulturalHeritageService {
    constructor(private http: HttpClient) { }

    getOne(id): Observable<CulturalHeritage> {
        return this.http.get<CulturalHeritage>(`${environment.apiUrl}/${CULTURAL_HERITAGES}/${id}`);
    }
}
