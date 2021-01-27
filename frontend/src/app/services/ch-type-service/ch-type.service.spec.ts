import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { CHTypeService } from './ch-type.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { CHType } from 'src/app/models/ch-type.model';
import { Page } from 'src/app/models/page.model';

describe('CHTypeService', () => {
  let injector;
  let chTypeService: CHTypeService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

	 beforeEach(() => {

    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [CHTypeService]
    });

    injector = getTestBed();
    chTypeService = TestBed.inject(CHTypeService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });


 	it('should pass simple test', () => {
	    expect(true).toBe(true);
    });



  it('getTypes() should return Page object with CH types', fakeAsync(() => {
    let types: CHType[];
    let last: boolean;
    let totalElements: number;
    let totalPages: number;
    let numberOfElements: number;
    let size: number;

    const mockResponse: Page<CHType> = new Page<CHType>(
      {
        content: [{
          id: 1,
          name: 'type1',
          subtypes: []
        },
        {
          id: 2,
          name: 'type2',
          subtypes: []
        },
        {
          id: 3,
          name: 'type3',
          subtypes: []
        }],
        id: 1,
        empty: false,
        number: 0,
        numberOfElements: 3,
        size: 3,
        totalElements: 12,
        totalPages: 6,
        last: false
      }
    );

    chTypeService.getTypes(0).subscribe(data => {
            types = data.content;
            numberOfElements = data.numberOfElements;
            size = data.size;
            totalElements = data.totalElements;
            totalPages = data.totalPages;
            last = data.last;
    });

    const req = httpMock.expectOne('http://localhost:8080/api/ch-types/by-page?page=0&size=10&sort=id,ASC');
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);

    tick();

    expect(types.length).toEqual(3);
    expect(types[0].id).toEqual(1);
    expect(types[0].name).toEqual('type1');
    expect(types[0].subtypes.length).toEqual(0);

    expect(types[1].id).toEqual(2);
    expect(types[1].name).toEqual('type2');
    expect(types[1].subtypes.length).toEqual(0);

    expect(types[2].id).toEqual(3);
    expect(types[2].name).toEqual('type3');
    expect(types[2].subtypes.length).toEqual(0);


  }));




  it('deleteType() should query url and delete type', fakeAsync(() => {
        chTypeService.deleteType(2).subscribe(res => { });

        const req = httpMock.expectOne('http://localhost:8080/api/ch-types/2');
        expect(req.request.method).toBe('DELETE');
        req.flush({});

    }));

  it('editType() should query url and change existing type', fakeAsync(() => {
        let type: CHType = new CHType({
            id: 1,
           name: 'manifestation',
           subtypes : [
               {id: 9, name: 'CAN BE DELETED', chTypeID: 1},
               {id: 2, name: 'fair', chTypeID: 1},
               {id: 1, name: 'festival', chTypeID: 1}
           ]
        });

        const mockType: CHType = {
            id: 2,
            name: 'institution2',
            subtypes: []
        };

        chTypeService.editType(type).subscribe(res => type = res);

        const req = httpMock.expectOne('http://localhost:8080/api/ch-types/1');
        expect(req.request.method).toBe('PUT');
        req.flush(mockType);

        tick();

        expect(type).toBeDefined();
        expect(type.id).toEqual(2);
        expect(type.name).toEqual('institution2');
      }));

});
