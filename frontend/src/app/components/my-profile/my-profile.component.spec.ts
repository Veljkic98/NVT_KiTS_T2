import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { AuthService } from 'src/app/services/auth-service/auth.service';

import { MyProfileComponent } from './my-profile.component';
import { ActivatedRoute } from '@angular/router';
import { ActivatedRouteStub } from 'src/app/testing/router-stubs';
import { User } from 'src/app/models/user.model';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';
import { DebugElement } from '@angular/core';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Overlay } from '@angular/cdk/overlay';

describe('MyProfileComponent', () => {
  let component: MyProfileComponent;
  let fixture: ComponentFixture<MyProfileComponent>;
  let authService: AuthService;
  let route: ActivatedRoute;
  let chService: CulturalHeritageService;

  beforeEach(async () => {
    const authServiceMock = {
        getRole: jasmine.createSpy('getRole')
        .and.returnValue('ROLE_USER'),

        getId: jasmine.createSpy('getId')
        .and.returnValue(of(3)),

        getProfile: jasmine.createSpy('getProfile')
        .and.returnValue(of(new User({
          id: 3,
          firstName: 'Sima',
          lastName: 'Matas',
          email: 'sima12@hotmail.com',
          approved: true
        }))),

        getSubscriptions: jasmine.createSpy('getSubscriptions').and
                                 .returnValue(of(
                                   [{
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
                                   },
                                  ]
                                 ))



      };
    const fakeActivatedRoute =  new ActivatedRouteStub();

    const chServiceMock = {};
    fakeActivatedRoute.testParams = { index: 1 };

    await TestBed.configureTestingModule({
      declarations: [ MyProfileComponent ],
      providers:    [
        {provide: AuthService, useValue: authServiceMock },
        {provide: ActivatedRoute, useValue: fakeActivatedRoute},
        {provide: CulturalHeritageService, useValue: chServiceMock },
        MatSnackBar, Overlay
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyProfileComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    route = TestBed.inject(ActivatedRoute);
    chService = TestBed.inject(CulturalHeritageService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch and display subscriptions on ngOnInit', fakeAsync(() => {
    component.ngOnInit();
    expect(authService.getRole).toHaveBeenCalled();
    tick();

    expect(authService.getSubscriptions).toHaveBeenCalled();
    tick();

    expect(component.subscriptions[0].id).toEqual(1);
    expect(component.subscriptions[0].name).toEqual('ch1');
    expect(component.subscriptions[0].chsubtypeID).toEqual(3);

    expect(component.subscriptions[1].id).toEqual(2);
    expect(component.subscriptions[1].name).toEqual('ch2');
    expect(component.subscriptions[1].chsubtypeID).toEqual(2);

    fixture.detectChanges();
    tick();
    fixture.detectChanges();
    const subsElements: DebugElement[] = fixture.debugElement.queryAll(By.css('.subs-label'));
    expect(subsElements.length).toBe(2);
    expect(subsElements[0].nativeElement.textContent).toEqual('ch1');
    expect(subsElements[1].nativeElement.textContent).toEqual('ch2');

  }));

  describe('ngOnInit()', () => {
  it('should fetch user profile details and subscriptions if user is not admin', fakeAsync(() => {
    component.ngOnInit();
    expect(authService.getProfile).toHaveBeenCalled();
    tick();

    expect(component.user.id).toBe(3);
    expect(component.user.firstName).toBe('Sima');
    expect(component.user.lastName).toEqual('Matas');
    expect(component.user.email).toEqual('sima12@hotmail.com');
    expect(component.user.approved).toEqual(true);


    // should display fetched profile
    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const profile = fixture.debugElement.query(By.css('.profile-head')).nativeElement;
    expect(profile.textContent).toContain('Sima Matas');
    expect(profile.textContent).toContain('sima12@hotmail.com');
  }));
});
});
