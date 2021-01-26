import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { RatingService } from './rating.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { Rating } from 'src/app/models/rating.model';

describe('RatingService', () => {
  let injector;
  let ratingService: RatingService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

	 beforeEach(() => {

    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers:    [RatingService]
    });

    injector = getTestBed();
    ratingService = TestBed.inject(RatingService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

 	it('should pass simple test', () => {
	    expect(true).toBe(true);
	});

  it(' getUserRating() should return user rating for ch with chID', fakeAsync(() => {
    let userRating: Rating;
    const mockRating: Rating =
    {
        id: 11,
        grade: 3,
        chID: 9,
        userID: 3
    };

    ratingService.getUserRating(1).subscribe(res => userRating = res);

    const req = httpMock.expectOne('http://localhost:8080/api/ratings/?chID=1');
    expect(req.request.method).toBe('GET');
    req.flush(mockRating);

    tick();
    expect(userRating).toBeDefined();
    expect(userRating.id).toEqual(11);
    expect(userRating.userID).toEqual(3);
    expect(userRating.chID).toEqual(9);
    expect(userRating.grade).toEqual(3);
  }));

  it('postRating() should save a new rating', fakeAsync(() => {
    let newRating: Rating = new Rating({
        grade: 3,
        chID: 1
    });

    const mockRating: Rating = {
        id: 12,
        grade: 3,
        chID: 1,
        userID: 3
    };

    ratingService.postRating(newRating.chID, newRating.grade).subscribe(res => newRating = res);

    const req = httpMock.expectOne('http://localhost:8080/api/ratings');
    expect(req.request.method).toBe('POST');
    req.flush(mockRating);

    tick();
    expect(newRating).toBeDefined();
    expect(newRating.id).toEqual(12);
    expect(newRating.chID).toEqual(1);
    expect(newRating.grade).toEqual(3);
    expect(newRating.userID).toEqual(3);
  }));

  it('updateRating() should query url and change existing rating', fakeAsync(() => {
    let rating: Rating = new Rating({
        id: 12,
        grade: 3,
        chID: 1,
        userID: 3
    });

    const mockRating: Rating = {
        id: 12,
        grade: 3,
        chID: 1,
        userID: 3
    };

    ratingService.updateRating(rating.id, rating.chID, rating.grade).subscribe(res => rating = res);

    const req = httpMock.expectOne('http://localhost:8080/api/ratings/12');
    expect(req.request.method).toBe('PUT');
    req.flush(mockRating);

    tick();

    expect(rating).toBeDefined();
    expect(rating.id).toEqual(12);
    expect(rating.grade).toEqual(3);
    expect(rating.chID).toEqual(1);
    expect(rating.userID).toEqual(3);
  }));

});
