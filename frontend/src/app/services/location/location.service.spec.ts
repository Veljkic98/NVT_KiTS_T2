import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, tick } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { Rating } from 'src/app/models/rating.model';
import { LocationService } from './location.service';
import { Location } from 'src/app/models/location.model';

describe('LocationService', () => {
    let injector;
    let locationService: LocationService;
    let httpMock: HttpTestingController;
    let httpClient: HttpClient;

    beforeEach(() => {

        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [LocationService]
        });

        injector = getTestBed();
        locationService = TestBed.inject(LocationService);
        httpClient = TestBed.inject(HttpClient);
        httpMock = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should pass simple test', () => {
        expect(true).toBe(true);
    });

    it(' getLocation(id) should return location', fakeAsync(() => {

        let location: Location;
        const mockLocation: Location = {
            id: 1,
            latitude: '12',
            longitude: '12',
            country: 'Srbija',
            city: 'NS',
            street: 'ulica1',
        };


        locationService.getOne(1).subscribe(res => location = res);

        const req = httpMock.expectOne('http://localhost:8080/api/locations/1');
        expect(req.request.method).toBe('GET');

        req.flush(mockLocation);

        tick();

        expect(location).toBeDefined();
        expect(location.id).toEqual(1);
        expect(location.latitude).toEqual('12');
        expect(location.longitude).toEqual('12');
        expect(location.city).toEqual('NS');
        expect(location.country).toEqual('Srbija');
        expect(location.street).toEqual('ulica1');
    }));

    it('post() should save a new location', fakeAsync(() => {
        let location: Location = {
            latitude: '12',
            longitude: '12',
            country: 'Srbija',
            city: 'NS',
            street: 'ulica1',
        };

        const mockLocation: Location = {
            id: 1,
            latitude: '12',
            longitude: '12',
            country: 'Srbija',
            city: 'NS',
            street: 'ulica1',
        };


        locationService.post(location).subscribe(res => location = res);

        const req = httpMock.expectOne('http://localhost:8080/api/locations');
        expect(req.request.method).toBe('POST');

        req.flush(mockLocation);

        tick();

        expect(location).toBeDefined();
        expect(location.id).toEqual(1);
        expect(location.latitude).toEqual('12');
        expect(location.longitude).toEqual('12');
        expect(location.city).toEqual('NS');
        expect(location.country).toEqual('Srbija');
        expect(location.street).toEqual('ulica1');
    }));

});
