import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { CHSubtypeService } from './ch-subtype.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, tick } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { CHSubtype } from 'src/app/models/ch-subtype.model';

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

  it('getAll() should return Array of CHSubtype[]', fakeAsync(() => {
    expect(true).toBe(true);
    let subtypes: CHSubtype[];

    let mockResponse: CHSubtype[] = [
      new CHSubtype({ name: 'festival 1', chTypeID: 1, id: 1 }),
      new CHSubtype({ name: 'festival 2', chTypeID: 1, id: 2 })
    ];

    chSubtypeService.getAll().subscribe(response => {
      subtypes = response
    });

    const req = httpMock.expectOne('http://localhost:8080/api/ch-subtypes');
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);

    tick();

    expect(subtypes.length).toEqual(2);
    expect(subtypes[0].id).toEqual(1);
    expect(subtypes[0].name).toEqual('festival 1');
    expect(subtypes[0].chTypeID).toEqual(1);
    expect(subtypes[1].id).toEqual(2);
    expect(subtypes[1].name).toEqual('festival 2');
    expect(subtypes[1].chTypeID).toEqual(1);
  }));

  it('deleteSubtype() should query url and delete subtype', fakeAsync(() => {
    chSubtypeService.deleteSubtype(2).subscribe(res => { });

    const req = httpMock.expectOne('http://localhost:8080/api/ch-subtypes/2');
    expect(req.request.method).toBe('DELETE');
    req.flush({});

  }));

  it('add() should add post new ch subtype', fakeAsync(() => {
    let subtype: CHSubtype = new CHSubtype({ name: 'festival 3', chTypeID: 1 });

    let mockType:CHSubtype = new CHSubtype({ name: 'festival 3', chTypeID: 1, id: 3 });

    chSubtypeService.add(subtype).subscribe( response => subtype = response);

    const req = httpMock.expectOne('http://localhost:8080/api/ch-subtypes');
    expect(req.request.method).toBe('POST');
    req.flush(mockType);

    expect(subtype).toBeDefined();
    expect(subtype.id).toEqual(3);
    expect(subtype.name).toEqual('festival 3');
  }))
});
