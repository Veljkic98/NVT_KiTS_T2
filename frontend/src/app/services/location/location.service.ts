import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Location } from 'src/app/models/location.model';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

const REST_ENDPOINT = {
  getOne: '/locations/',
  getByPage: '/locations/by-page',
};

import { LOCATIONS } from '../../utils/constants';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private http: HttpClient) { }

  post(location: Location): Observable<Location> {
    // const location = { latitude, longitude, country, city, street }
    // const formData = new FormData();
    // formData.append('locationRequestDTO', new Blob([JSON.stringify(location)], {
    //   type: 'application/json'
    // }));

    return this.http.post<Location>(`${environment.apiUrl}/${LOCATIONS}`, location);
  }

  getOne(id: number): Observable<Location> {
    return this.http.get<Location>(`${environment.apiUrl}/${LOCATIONS}/${id}`);
  }
}
