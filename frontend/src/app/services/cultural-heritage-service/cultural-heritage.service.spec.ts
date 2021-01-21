import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { CulturalHeritageService } from './cultural-heritage.service';
import { User } from '../../models/user.model';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';

describe('CulturalHeritageService', () => {
  let injector;
  let chService: CulturalHeritageService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

	beforeEach(() => {

    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers:    [AuthService]
    });

    injector = getTestBed();
    authService = TestBed.inject(AuthService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });
  
  afterEach(() => {
    httpMock.verify();
  });
 	
 	it('should pass simple test', () => {
	    expect(true).toBe(true);
	}); 

  it('register() should return new created user', fakeAsync(() => {
    let newUser: User = new User({
        firstName: 'Petar', 
        lastName: 'Petrovic', 
        email: "some1667@gmail.com"
      }
    )

    const mockUser: User = 
    {
        id: 1, 
        firstName: 'Petar', 
        lastName: 'Petrovic', 
        email: "some1667@gmail.com"
    }

    authService.register(newUser).subscribe(res => newUser = res);
    
    const req = httpMock.expectOne('http://localhost:8080/api/authenticated-users');
    expect(req.request.method).toBe('POST');
    req.flush(mockUser);

    tick();
    expect(newUser).toBeDefined();
    expect(newUser.id).toEqual(1);
    expect(newUser.firstName).toEqual('Petar');
    expect(newUser.lastName).toEqual('Petrovic');
    expect(newUser.email).toEqual('some1667@gmail.com');
  }));
});