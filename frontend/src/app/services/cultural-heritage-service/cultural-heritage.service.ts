import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { CulturalHeritage } from '../../models/cultural-heritage.model';
import { Page } from '../../models/page.model';
import { Observable } from 'rxjs';

const REST_ENDPOINT = {
  getOne: '/cultural-heritages/',
  getByPage: '/cultural-heritages/by-page',
  filter: '/cultural-heritages/filtered',
};

import { CULTURAL_HERITAGES } from '../../utils/constants';
import { CHFilter } from 'src/app/models/ch-filter.model';
import { CulturalHeritageToAdd } from 'src/app/models/cultural-heritage-to-add.model';


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

  addNew(ch: CulturalHeritageToAdd) {
    return this.http.post<CulturalHeritage>(`${environment.apiUrl}/${CULTURAL_HERITAGES}`, ch);
  }

  post(name: string, description: string, locationID: number, chsubtypeID: number, image: string): Observable<any> {
    const ch = { name, description, locationID, chsubtypeID };
    const formData = new FormData();
    formData.append('culturalHeritageRequestDTO', new Blob([JSON.stringify(ch)], {
      type: 'application/json'
    }));
    console.log(image);
    if (image) {
      formData.append('file', image);
    }

    return this.http.post<any>(`${environment.apiUrl}/${CULTURAL_HERITAGES}`, formData);
  }


  filterCulturalHeritages(payload: CHFilter, page: number): Observable<Page>{
    return this.http.post<Page>(`${environment.apiUrl}${REST_ENDPOINT.filter}/?page=${page}&size=10`, payload);
  }


  subscribe(chID: number) {
    return this.http.post<any>(`${environment.apiUrl}/${CULTURAL_HERITAGES}/subscribe/${chID}`, null, { observe: 'response' });
  }

  unsubscribe(chID: number) {
    return this.http.delete<any>(`${environment.apiUrl}/${CULTURAL_HERITAGES}/unsubscribe/${chID}`, { observe: 'response' });
  }

  put(ch: CulturalHeritage, image: any): Observable<any> {

    const formData = new FormData();
    formData.append('culturalHeritageRequestDTO', new Blob([JSON.stringify(ch)], {
      type: 'application/json'
    }));
    formData.append('file', image);

    return this.http.put<CulturalHeritage>(`${environment.apiUrl}/${CULTURAL_HERITAGES}/${ch.id}`, formData);
  }

  delete(id: number): Observable<Object> {
    return this.http.delete(`${environment.apiUrl}/${CULTURAL_HERITAGES}/${id}`);
  }
}
