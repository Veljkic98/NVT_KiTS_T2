import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { AuthService } from './auth.service';
import { User } from '../../models/user.model';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';
import { JWT } from 'src/app/models/jwt.model';

describe('AuthService', () => {
  let injector;
  let authService: AuthService;
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
        email: "some1667@gmail.com",
        approved: false
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
    expect(newUser.approved).toBe(false);
  }));

  it('verify() should query url and return approved user profile', fakeAsync(() => {
    let newUser: User;

    const mockUser: User = 
    {
        id: 1, 
        firstName: 'Petar', 
        lastName: 'Petrovic', 
        email: "some1667@gmail.com",
        approved: true
    }

    authService.verify(1).subscribe(res => newUser = res);
    
    const req = httpMock.expectOne('http://localhost:8080/api/authenticated-users/verify/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);

    tick();
    expect(newUser).toBeDefined();
    expect(newUser.id).toEqual(1);
    expect(newUser.firstName).toEqual('Petar');
    expect(newUser.lastName).toEqual('Petrovic');
    expect(newUser.email).toEqual('some1667@gmail.com');
    expect(newUser.approved).toBe(true);
  }));
  it('login() should return JWT', fakeAsync(() => {
    let mockResponse: JWT = {
      accessToken: 'some serious hashed stuff',
      expiresIn: 18000000
    };
    let jwt;

    authService.login('helen@gmail.com','123').subscribe(res => jwt = res);
    
    
    const req = httpMock.expectOne('http://localhost:8080/auth/login');
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);

    tick();

    expect(jwt.accessToken).toBe('some serious hashed stuff');
    expect(jwt.expiresIn).toEqual(18000000);

  }));

  it('getSubscriptions() should query url and return cultural heritages that user is subscribed to', fakeAsync(() => {
    let subscriptions: CulturalHeritage[];
    
    const mockResponse: Array<CulturalHeritage> = [{
        id: 1,
        avgRating: 1.5,
        chsubtypeID: 3,
        description: 'bla',
        imageUri: 'image',
        locationID: 1,
        name: 'ch1',
        coordinates: ['1', '1'],
        totalRatings: 23,
        locationName: 'Zanzibar',
      },
      {
        id: 2,
        avgRating: 4,
        chsubtypeID: 2,
        description: 'blabla',
        imageUri: 'image',
        locationID: 1,
        name: 'ch2',
        coordinates: ['1', '1'],
        totalRatings: 23,
        locationName: 'Tanzania',
      }];
    
      
    authService.getSubscriptions().subscribe(res => subscriptions = res);
    const req = httpMock.expectOne('http://localhost:8080/api/authenticated-users/me/subscriptions');
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);

    tick();

    expect(subscriptions[0].id).toEqual(1);
    expect(subscriptions[0].name).toEqual('ch1');
    expect(subscriptions[0].chsubtypeID).toEqual(3);
  
    expect(subscriptions[1].id).toEqual(2);
    expect(subscriptions[1].name).toEqual('ch2');
    expect(subscriptions[1].chsubtypeID).toEqual(2);  }));



  it('getProfile() should query url and return user profile data', fakeAsync(() => {
    let newUser: User;

    const mockUser: User = 
    {
        id: 1, 
        firstName: 'Petar', 
        lastName: 'Petrovic', 
        email: "some1667@gmail.com",
        approved: true
    }

    authService.getProfile().subscribe(res => newUser = res);
    
    const req = httpMock.expectOne('http://localhost:8080/api/authenticated-users/me');
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);

    tick();
    expect(newUser).toBeDefined();
    expect(newUser.id).toEqual(1);
    expect(newUser.firstName).toEqual('Petar');
    expect(newUser.lastName).toEqual('Petrovic');
    expect(newUser.email).toEqual('some1667@gmail.com');
    expect(newUser.approved).toBe(true);
  }));

  
});