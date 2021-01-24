import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { of } from 'rxjs';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { RatingComponent } from './rating.component';
import { RatingService } from 'src/app/services/rating-service/rating.service';
import { Rating } from 'src/app/models/rating.model';

describe('RatingComponent', () => {
  let component: RatingComponent;
  let fixture: ComponentFixture<RatingComponent>;
  let authService: AuthService;
  let ratingService: RatingService;

  beforeEach(async () => {
    let authServiceMock = {
        getRole: jasmine.createSpy('getRole')
        .and.returnValue(of("USER")),

        getId: jasmine.createSpy('getId')
        .and.returnValue(of(3))
      }

    let ratingServiceMock = {
      getUserRating: jasmine.createSpy('getUserRating')
        .and.returnValue(of(new Rating({
            id: 1,
            userID: 3,
            grade: 4,
            chID: 1
        }))),
             
        postRating: jasmine.createSpy('postRating')
        .and.returnValue(of(new Rating({
            id: 1,
            userID: 3,
            grade: 4,
            chID: 1
        }))),

        updateRating: jasmine.createSpy('updateRating')
        .and.returnValue(of(new Rating({
            id: 1,
            userID: 3,
            grade: 5,
            chID: 1
        }))),
      }
    await TestBed.configureTestingModule({
      declarations: [ RatingComponent ],
      providers:    [ 
        {provide: AuthService, useValue: authServiceMock },
        {provide: RatingService, useValue: ratingServiceMock } ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RatingComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    ratingService = TestBed.inject(RatingService);
    expect(component.userRated).toBe(false);
    expect(component.userRating).toBe(0);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
  it('should fetch user rating if exist', fakeAsync(() => {
    component.chID = 1;
    fixture.detectChanges();
    component.ngOnInit();
    expect(ratingService.getUserRating).toHaveBeenCalledWith(1); 
    tick();
    
    expect(component.chID).toBe(1);
    expect(component.userRated).toBe(true);
    expect(component.userRating).toEqual(4);
    expect(component.ratingID).toBe(1);
    expect(component.oldRating).toBe(4);
  }));
  })

  describe('newRate()', () => {
    it('should add rating for chosen ch', fakeAsync(() => {
        component.chID = 1;
        component.userRating = 5;
        spyOn(component.calcAvgAddedRating, 'emit');
        fixture.detectChanges();
        component.newRate();
        expect(ratingService.postRating).toHaveBeenCalledWith(1, 5); 
        tick();

        expect(component.calcAvgAddedRating.emit).toHaveBeenCalledWith(5); 
        expect(component.chID).toBe(1);
        expect(component.userRated).toBe(true);
        expect(component.userRating).toEqual(5);
        expect(component.ratingID).toBe(1);
        expect(component.oldRating).toBe(5);
    }));
  })

  describe('changeRate()', () => {
    it('should change rating for chosen ch', fakeAsync(() => {
        component.chID = 1;
        component.userRating = 5;
        spyOn(component.calcAvgChangedRating, 'emit');
        fixture.detectChanges();
        component.changeRate();
        expect(ratingService.updateRating).toHaveBeenCalledWith(1, 1, 5); 
        tick();
        
        expect(component.calcAvgChangedRating.emit).toHaveBeenCalledWith({
            rating : 5,
            oldRating: 4
        }); 
        expect(component.chID).toBe(1);
        expect(component.userRated).toBe(true);
        expect(component.userRating).toEqual(5);
        expect(component.ratingID).toBe(1);
        expect(component.oldRating).toBe(5);
    }));
  })
});
