import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { CulturalHeritage } from '../../models/cultural-heritage.model';
import { Page } from '../../models/page.model'
import { Observable } from 'rxjs';

const REST_ENDPOINT = {
  getOne: '/cultural-heritages/',
  getByPage: '/cultural-heritages/by-page',
}

import { CULTURAL_HERITAGES } from '../../utils/constants';


@Injectable({ providedIn: 'root' })
export class CulturalHeritageService {
  constructor(private http: HttpClient) { }



  getCulturalHeritages(page: number): Observable<Page> {
    return this.http.get<Page>(`${environment.apiUrl}${REST_ENDPOINT.getByPage}/?page=${page}&size=10`);
  }

  getCulturalHeritagesWithSize(pageIndex: number, pageSize: number): Observable<Page> {
    return this.http.get<Page>(`${environment.apiUrl}${REST_ENDPOINT.getByPage}/?page=${pageIndex}&size=${pageSize}`);
  }

  getOne(id: number): Observable<CulturalHeritage> {
    return this.http.get<CulturalHeritage>(`${environment.apiUrl}/${CULTURAL_HERITAGES}/${id}`);
  }
}
