import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { CHSubtypeService } from './ch-subtype.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';

describe('CHSubtypeService', () => {
  let injector;
  let chSubtypeService: CHSubtypeService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

	 beforeEach(() => {

    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [CHSubtypeService]
    });

    injector = getTestBed();
    chSubtypeService = TestBed.inject(CHSubtypeService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

 	it('should pass simple test', () => {
	    expect(true).toBe(true);
    });

  it('deleteSubtype() should query url and delete subtype', fakeAsync(() => {
        chSubtypeService.deleteSubtype(2).subscribe(res => { });

        const req = httpMock.expectOne('http://localhost:8080/api/ch-subtypes/2');
        expect(req.request.method).toBe('DELETE');
        req.flush({});

    }));
});
