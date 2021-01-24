import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { CHTypeService } from './ch-type.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { CHType } from 'src/app/models/ch-type.model';

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
               {id: 9, name: "CAN BE DELETED", parentId: 1},
               {id: 2, name: "fair", parentId: 1},
               {id: 1, name: "festival", parentId: 1}
           ]
        });
    
        const mockType: CHType = {
            id: 2,
            name: "institution2",
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