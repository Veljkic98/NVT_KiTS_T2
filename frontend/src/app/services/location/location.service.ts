import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Location } from 'src/app/models/location.model';
import { environment } from '../../../environments/environment';

const REST_ENDPOINT = {
  getOne: '/locations/',
  getByPage: '/locations/by-page',
}

import { LOCATIONS } from '../../utils/constants';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private http: HttpClient) { }

  post(location: Location) {
    // const location = { latitude, longitude, country, city, street }
    // const formData = new FormData();
    // formData.append('locationRequestDTO', new Blob([JSON.stringify(location)], {
    //   type: 'application/json'
    // }));

    return this.http.post<any>(`${environment.apiUrl}/${LOCATIONS}`, location);
  }
}
