import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { CulturalHeritage } from '../../models/cultural-heritage.model';

@Injectable({ providedIn: 'root' })
export class CulturalHeritageService {
    constructor(private http: HttpClient) { }

    getOne(id) {
        return this.http.get<CulturalHeritage>(`${environment.apiUrl}/cultural-heritages/${id}`)
    }
}