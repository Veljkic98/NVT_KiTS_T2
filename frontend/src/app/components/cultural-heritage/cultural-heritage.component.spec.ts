import { Overlay } from '@angular/cdk/overlay';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';

import { CulturalHeritageComponent } from './cultural-heritage.component';

describe('CulturalHeritageComponent', () => {
  let component: CulturalHeritageComponent;
  let fixture: ComponentFixture<CulturalHeritageComponent>;
  let authService: AuthService;
  let chService: CulturalHeritageService;

  beforeEach(async () => {
    const authServiceMock = {
      getRole: jasmine.createSpy('getRole')
        .and.returnValue(of('USER')),

      getSubscriptions: jasmine.createSpy('getSubscriptions')
        .and.returnValue(of([]))
    };
    const chServiceMock = {
      getOne: jasmine.createSpy('getOne')
        .and.returnValue(of(new CulturalHeritage({
          id: 1,
          avgRating: 0,
          chsubtypeID: 1,
          coordinates: ['12.327145', '45.438759'],
          description: 'The Carnival of Venice (Italian: Carnevale di Venezia) is an annual festival, held in Venice, Italy. The Carnival starts forty days before Easter and ends on Shrove Tuesday (Fat Tuesday or MartedÃ¬ Grasso), the day before Ash Wednesday. Dove il gabinetto! In other words, At a carnival, every joke is disgraced!',
          imageUri: 'http://localhost:8080/api/files/1',
          locationID: 1,
          locationName: 'Italy Venice',
          name: 'Venice Carnival',
          totalRatings: 0
        }))),
        subscribe: jasmine.createSpy('subscribe')
        .and.returnValue
    };
    const commentServiceMock = {

    };
    await TestBed.configureTestingModule({
      declarations: [CulturalHeritageComponent],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: CulturalHeritageService, useValue: chServiceMock },
        MatSnackBar, Overlay
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalHeritageComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    chService = TestBed.inject(CulturalHeritageService);
    expect(component.ch).toEqual(undefined);
    expect(component.error).toBe(undefined);
    expect(component.isSubscribed).toBe(false);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should fetch cultural heritage, user rating for it and subsctiption on init ', fakeAsync(() => {
      component.ngOnInit();
      expect(chService.getOne).toHaveBeenCalled();
      tick();


      expect(component.ch.id).toBe(1);
      expect(component.ch.name).toEqual('Venice Carnival');
      expect(component.ch.locationName).toEqual('Italy Venice');
      expect(component.ch.imageUri).toEqual('http://localhost:8080/api/files/1');
      expect(component.ch.totalRatings).toEqual(0);
      expect(component.ch.coordinates).toEqual(['12.327145', '45.438759']);

      expect(component.isSubscribed).toBe(false);
      expect(component.error).toBe(null);

      // //should display fetched cultural heritage
      fixture.detectChanges();
      tick();
      fixture.detectChanges();

      const cardTitle = fixture.debugElement.query(By.css('mat-card-title')).nativeElement;
      expect(cardTitle.textContent).toEqual('Venice Carnival');
      const cardSubTitle = fixture.debugElement.query(By.css('mat-card-subtitle')).nativeElement;
      expect(cardSubTitle.textContent).toEqual('Italy Venice');
      const rating = fixture.debugElement.query(By.css('.rate')).nativeElement;
      expect(rating.textContent).toEqual(' 0 ');
    }));
  });

  describe('subscribe()', () => {
    it('should subscribe user to CH.', fakeAsync(() => {
      component.chID = 1;
      // component.ngOnInit();
      tick();
      component.subscribe();
      tick();

    }));
  });
});
