import { Overlay } from '@angular/cdk/overlay';
import { ComponentFixture, fakeAsync, flush, TestBed, tick } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { By } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import { of } from 'rxjs';
import { CHFilter } from 'src/app/models/ch-filter.model';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';
import { Page } from 'src/app/models/page.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';

import { DashboardComponent } from './dashboard.component';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let authService: AuthService;
  let chService: CulturalHeritageService;

  beforeEach(async () => {
    const authServiceMock = {
        getRole: jasmine.createSpy('getRole')
        .and.returnValue('ROLE_USER'),

        getSubscriptions: jasmine.createSpy('getSubscriptions')
        .and.returnValue(of([]))
      };
    const chServiceMock = {
        getCulturalHeritages: jasmine.createSpy('getCulturalHeritages').and
                                     .returnValue(of(new Page<CulturalHeritage>({
                    id: 1,
                    empty: false,
                    number: 0,
                    numberOfElements: 3,
                    size: 3,
                    totalElements: 12,
                    totalPages: 6,
                    last: false,
                    content: [{
                        id: 1,
                        avgRating: 0,
                        chsubtypeID: 1,
                        coordinates:  ['12.327145', '45.438759'],
                        description: 'The Carnival of Venice (Italian: Carnevale di Venezia) is an annual festival, held in Venice, Italy. The Carnival starts forty days before Easter and ends on Shrove Tuesday (Fat Tuesday or MartedÃ¬ Grasso), the day before Ash Wednesday. Dove il gabinetto! In other words, At a carnival, every joke is disgraced!',
                        imageUri: 'http://localhost:8080/api/files/1',
                        locationID: 1,
                        locationName: 'Italy Venice',
                        name: 'Venice Carnival',
                        totalRatings: 0
                        },
                        {
                        id: 2,
                        avgRating: 3,
                        chsubtypeID: 1,
                        coordinates:  ['2.327145', '6.3259'],
                        description: 'some description',
                        imageUri: 'http://localhost:8080/api/files/1',
                        locationID: 1,
                        locationName: 'Germany Swechen',
                        name: 'Venikarman Museum',
                        totalRatings: 11
                        }], }))),

        filterCulturalHeritages: jasmine.createSpy('filterCulturalHeritages').and
                                        .returnValue(of(new Page<CulturalHeritage>({
                                            id: 1,
                                            empty: false,
                                            number: 0,
                                            numberOfElements: 3,
                                            size: 3,
                                            totalElements: 12,
                                            totalPages: 6,
                                            last: false,
                                            content: [
                                                {
                                                id: 2,
                                                avgRating: 3,
                                                chsubtypeID: 1,
                                                coordinates:  ['2.327145', '6.3259'],
                                                description: 'some description',
                                                imageUri: 'http://localhost:8080/api/files/1',
                                                locationID: 1,
                                                locationName: 'Germany Swechen',
                                                name: 'Venikarman Museum',
                                                totalRatings: 11
                                            }]
                                        })
                                )),

    getOne: jasmine.createSpy('getOne')
    .and.returnValue(of(new CulturalHeritage({
      id: 1,
      avgRating: 0,
      chsubtypeID: 1,
      coordinates:  ['12.327145', '45.438759'],
      description: 'The Carnival of Venice (Italian: Carnevale di Venezia) is an annual festival, held in Venice, Italy. The Carnival starts forty days before Easter and ends on Shrove Tuesday (Fat Tuesday or MartedÃ¬ Grasso), the day before Ash Wednesday. Dove il gabinetto! In other words, At a carnival, every joke is disgraced!',
      imageUri: 'http://localhost:8080/api/files/1',
      locationID: 1,
      locationName: 'Italy Venice',
      name: 'Venice Carnival',
      totalRatings: 0
    })))
    };

    await TestBed.configureTestingModule({
      declarations: [ DashboardComponent ],
      imports: [NgxPaginationModule, BrowserAnimationsModule],
      providers:    [
        {provide: AuthService, useValue: authServiceMock },
        {provide: CulturalHeritageService, useValue: chServiceMock },
        MatSnackBar, Overlay, NgbModal
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    chService = TestBed.inject(CulturalHeritageService);
    fixture.detectChanges();

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch cultural heritages on initialization ', fakeAsync(() => {
    component.ngOnInit();
    expect(chService.getCulturalHeritages).toHaveBeenCalled();
    tick();

    expect(component.culturalHeritages[0].id).toBe(1);
    expect(component.culturalHeritages[0].name).toEqual('Venice Carnival');
    expect(component.culturalHeritages[0].locationName).toEqual('Italy Venice');
    expect(component.culturalHeritages[0].imageUri).toEqual('http://localhost:8080/api/files/1');
    expect(component.culturalHeritages[0].totalRatings).toEqual(0);
    expect(component.culturalHeritages[0].coordinates).toEqual(['12.327145', '45.438759']);

    expect(component.culturalHeritages[1].id).toBe(2);
    expect(component.culturalHeritages[1].name).toEqual('Venikarman Museum');
    expect(component.culturalHeritages[1].locationName).toEqual('Germany Swechen');
    expect(component.culturalHeritages[1].imageUri).toEqual('http://localhost:8080/api/files/1');
    expect(component.culturalHeritages[1].totalRatings).toEqual(11);
    expect(component.culturalHeritages[1].coordinates).toEqual(['2.327145', '6.3259']);


  }));

  it('filterData() should call filterCulturalHeritages on service when called', fakeAsync(() => {
    const page = 1;
    const filter: CHFilter = {
        type: component.filterType,
        value: component.filterValue
    };
    component.filterType = filter.type;
    component.filterValue = filter.value;

    component.filterData(page);
    flush();

    expect(chService.filterCulturalHeritages).toHaveBeenCalledWith(filter, page - 1);

  }));

  it('resetData() should call getCulturalHeritages with page = 0 and reset filterValue and filterType', fakeAsync(() => {
    const page = 0;
    const filter: CHFilter = {
        type: component.filterType,
        value: component.filterValue
    };

    component.resetData();
    expect(component.filterValue).toEqual('');
    expect(component.filterType).toEqual('');

    expect(chService.getCulturalHeritages).toHaveBeenCalledWith(0);


  }));



});
