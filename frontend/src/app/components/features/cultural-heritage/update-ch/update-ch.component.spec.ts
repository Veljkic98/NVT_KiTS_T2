import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';
import { CHSubtypeService } from 'src/app/services/ch-subtype-service/ch-subtype.service';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';
import { LocationService } from 'src/app/services/location/location.service';
import { Location } from 'src/app/models/location.model';

import { UpdateChComponent } from './update-ch.component';
import { CHSubtype } from 'src/app/models/ch-subtype.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Overlay } from '@angular/cdk/overlay';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { CulturalHeritagesComponent } from '../cultural-heritages/cultural-heritages.component';

describe('UpdateChComponent', () => {
  let component: UpdateChComponent;
  let fixture: ComponentFixture<UpdateChComponent>;
  let chService: CulturalHeritageService;
  let subtypeService: CHSubtypeService;
  let locationService: LocationService;
  let router: Router;
  let route: ActivatedRoute;

  beforeEach(async () => {

    const authServiceMock = {
      getRole: jasmine.createSpy('getRole')
        .and.returnValue(of('ADMIN')),

      // getId: jasmine.createSpy('getId')
      // .and.returnValue(of(3))
    };

    const mockCulturalHeritage =
      new CulturalHeritage({
        id: 1,
        avgRating: 2,
        chsubtypeID: 1,
        description: 'opis1',
        imageUri: 'http://localhost:8080/api/files/1',
        locationID: 1,
        name: 'naziv1',
        coordinates: ['12', '12'],
        totalRatings: 3,
        locationName: 'a a',
      });

    const l = new Location('12', '12', 'a', 'a', 'a');
    l.id = 1;

    const mockLocation = l;

    const mockSubtypes = [
      new CHSubtype({
        id: 1,
        name: 'naziv1',
        chTypeID: 1,
      }),
      new CHSubtype({
        id: 2,
        name: 'naziv2',
        chTypeID: 2,
      }),
    ];

    const subtypeServiceMock = {
      getAll: jasmine.createSpy('getAll')
        .and.returnValue(of(
          mockSubtypes
        )),

    };

    const chServiceMock = {
      getOne: jasmine.createSpy('getOne')
        .and.returnValue(of(mockCulturalHeritage)),
      put: jasmine.createSpy('put')
        .and.returnValue(of(mockCulturalHeritage)),
    };

    const locationServiceMock = {
      post: jasmine.createSpy('post')
        .and.returnValue(of(mockLocation)),
      getOne: jasmine.createSpy('getOne')
        .and.returnValue(of(mockLocation)),
    };

    await TestBed.configureTestingModule({
      declarations: [UpdateChComponent],
      providers: [
        { provide: CulturalHeritageService, useValue: chServiceMock },
        { provide: CHSubtypeService, useValue: subtypeServiceMock },
        { provide: LocationService, useValue: locationServiceMock },
        MatSnackBar, Overlay, NgbModal, HttpClient
      ],
      imports:
        [
          RouterTestingModule.withRoutes([{ path: 'cultural-heritages', component: CulturalHeritagesComponent }, ]),
          // RouterTestingModule
          // Router
          // HttpClient
          // NgxPaginationModule,
          // BrowserAnimationsModule
        ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateChComponent);
    component = fixture.componentInstance;
    chService = TestBed.inject(CulturalHeritageService);
    subtypeService = TestBed.inject(CHSubtypeService);
    locationService = TestBed.inject(LocationService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should fetch all subtypes on init', fakeAsync(() => {
      component.chid = 1;

      component.ngOnInit();

      expect(chService.getOne).toHaveBeenCalledWith(component.chid);
      expect(locationService.getOne).toHaveBeenCalledWith(1);
      expect(subtypeService.getAll).toHaveBeenCalled();
      expect(component.subtypes.length).toBe(2);
    }));
  });

  describe('updateCH()', () => {
    it('should update', fakeAsync(() => {
      spyOn(component, 'openSnackBar');
      const navigateSpy = spyOn(router, 'navigate');

      component.ngOnInit();

      console.log(component.culturalHeritage);

      component.subtype.id = 1;
      component.culturalHeritage.locationID = 1;
      component.culturalHeritage.chsubtypeID = 1;

      const file: File = new File([''], 'slika123.jpg');
      component.url = file;

      fixture.detectChanges();

      component.updateCH();

      tick();
      expect(locationService.post).toHaveBeenCalledWith(component.location);
      expect(chService.put).toHaveBeenCalledWith(component.culturalHeritage, file);
      expect(navigateSpy).toHaveBeenCalledWith(['/cultural-heritages']);
    }));
  });
});
