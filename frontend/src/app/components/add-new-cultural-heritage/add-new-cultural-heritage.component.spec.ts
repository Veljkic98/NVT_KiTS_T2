import { Overlay } from '@angular/cdk/overlay';
import { HttpClient } from '@angular/common/http';
import { ComponentFixture, fakeAsync, flush, TestBed, tick } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { CHSubtype, CHSubtype2, CHSubtype3 } from 'src/app/models/ch-subtype.model';
import { Location } from 'src/app/models/location.model';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';
import { CHSubtypeService } from 'src/app/services/ch-subtype-service/ch-subtype.service';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';
import { LocationService } from 'src/app/services/location/location.service';

import { AddNewCulturalHeritageComponent } from './add-new-cultural-heritage.component';
import { CulturalHeritagesComponent } from '../cultural-heritages/cultural-heritages.component';

describe('AddNewCulturalHeritageComponent', () => {
  let component: AddNewCulturalHeritageComponent;
  let fixture: ComponentFixture<AddNewCulturalHeritageComponent>;
  let chService: CulturalHeritageService;
  let subtypeService: CHSubtypeService;
  let locationService: LocationService;
  let router: Router;
  let route: ActivatedRoute;

  beforeEach(async () => {

    let authServiceMock = {
      getRole: jasmine.createSpy('getRole')
        .and.returnValue(of("ADMIN")),

      // getId: jasmine.createSpy('getId')
      // .and.returnValue(of(3))
    }

    const mockCulturalHeritage =
      new CulturalHeritage({
        id: 1,
        avgRating: 2,
        chsubtypeID: 1,
        description: "opis1",
        imageUri: "http://localhost:8080/api/files/1",
        locationID: 1,
        name: "naziv1",
        coordinates: ["12", "12"],
        totalRatings: 3,
        locationName: "lokacija1",
      })

    const l = new Location("12", "12", "asd", "asd", "asd");
    l.id = 1;

    const mockLocation = l;

    const mockSubtypes = [
      new CHSubtype({
        id: 1,
        name: "naziv1",
        parentId: 1,
      }),
      new CHSubtype({
        id: 2,
        name: "naziv2",
        parentId: 2,
      }),
    ]

    let subtypeServiceMock = {
      getAll: jasmine.createSpy('getAll')
        .and.returnValue(of(
          mockSubtypes
        )),
    }

    let chServiceMock = {
      post: jasmine.createSpy('post')
        .and.returnValue(of(mockCulturalHeritage)),
    }

    let locationServiceMock = {
      post: jasmine.createSpy('post')
        .and.returnValue(of(mockLocation)),
    }

    await TestBed.configureTestingModule({
      declarations: [AddNewCulturalHeritageComponent],
      providers: [
        { provide: CulturalHeritageService, useValue: chServiceMock },
        { provide: CHSubtypeService, useValue: subtypeServiceMock },
        { provide: LocationService, useValue: locationServiceMock },
        MatSnackBar, Overlay, NgbModal, HttpClient
      ],
      imports:
        [
          RouterTestingModule.withRoutes([{ path: 'cultural-heritages', component: CulturalHeritagesComponent },]),
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
    fixture = TestBed.createComponent(AddNewCulturalHeritageComponent);
    component = fixture.componentInstance;
    chService = TestBed.inject(CulturalHeritageService);
    subtypeService = TestBed.inject(CHSubtypeService);
    locationService = TestBed.inject(LocationService);
    router = TestBed.inject(Router);
    // expect(component.dataSource).toEqual([]);
    // expect(component.pageIndex).toBe(0);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  fdescribe('ngOnInit()', () => {
    it('should fetch all subtypes on init', fakeAsync(() => {
      // fixture.detectChanges();
      component.ngOnInit();
      expect(subtypeService.getAll).toHaveBeenCalled();
      // tick();

      expect(component.subtypes.length).toBe(2);
    }));
  })

  fdescribe('addCH()', () => {
    it('should add', fakeAsync(() => {

      // spyOn(component, '');
      const navigateSpy = spyOn(router, 'navigate');

      component.location = new Location("12", "12", "asd", "asd", "asd");
      component.location.id = 1;

      component.name = "naziv1";
      component.description = "opis1";
      component.url = "asdasdasdad";
      component.subtype = new CHSubtype2();
      component.subtype.id = 1;

      component.addCH();
      expect(locationService.post).toHaveBeenCalledWith(component.location);

      expect(navigateSpy).toHaveBeenCalledWith(['/cultural-heritages'])
    }));
  })
});
