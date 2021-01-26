import { ComponentFixture, fakeAsync, flush, TestBed, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { NgxPaginationModule } from 'ngx-pagination';
import { AuthService } from 'src/app/services/auth-service/auth.service';

import { CulturalHeritagesComponent } from './cultural-heritages.component';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';
import { DebugElement } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Overlay } from '@angular/cdk/overlay';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MatPaginator } from '@angular/material/paginator';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('CulturalHeritagesComponent', () => {
  let component: CulturalHeritagesComponent;
  let fixture: ComponentFixture<CulturalHeritagesComponent>;
  let authService: AuthService;
  let chService: CulturalHeritageService;

  beforeEach(async () => {

    let authServiceMock = {
      getRole: jasmine.createSpy('getRole')
        .and.returnValue(of("ADMIN")),

      // getId: jasmine.createSpy('getId')
      // .and.returnValue(of(3))
    }

    const mockCulturalHeritages = [
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
      }),
      new CulturalHeritage({
        id: 2,
        avgRating: undefined,
        chsubtypeID: 1,
        description: "opis2",
        imageUri: undefined,
        locationID: 2,
        name: "naziv2",
        coordinates: ["15", "15"],
        totalRatings: undefined,
        locationName: "lokacija2",
      })
    ];

    let chServiceMock = {
      getCulturalHeritagesWithSize: jasmine.createSpy('getCulturalHeritagesWithSize')
        .and.returnValue(of({
          content: mockCulturalHeritages,
          empty: false,
          first: true,
          last: true,
          number: 0,
          numberOfElements: 2,
          pageable: { sort: { sorted: true, unsorted: false, empty: false }, offset: 0, pageNumber: 0, pageSize: 2 },
          size: 2,
          sort: { sorted: true, unsorted: false, empty: false },
          totalElements: 2,
          totalPages: 1
        }
        )),

      delete: jasmine.createSpy('delete')
        .and.returnValue(of({})),

      // postComment: jasmine.createSpy('postComment')
      // .and.returnValue(of(new Comment({
      //   id: 5,
      //   content: "This is awesome, we will pass 2 exams with one project!!",
      //   authenticatedUserID: 3,
      //   culturaHeritageID: 1,
      //   imageUri: null,
      //   userName: "Sima Matas"
      // })))
    }



    await TestBed.configureTestingModule({
      declarations: [CulturalHeritagesComponent],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: CulturalHeritageService, useValue: chServiceMock },
        MatSnackBar, Overlay, NgbModal, MatPaginator, 
      ],
      imports:
        [
          NgxPaginationModule,
          BrowserAnimationsModule
        ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalHeritagesComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    chService = TestBed.inject(CulturalHeritageService);
    expect(component.dataSource).toEqual([]);
    expect(component.pageIndex).toBe(0);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // testing methods (functionalities)

  fdescribe('ngOnInit()', () => {
    it('should fetch all cultural heritages on init (with paggination)', fakeAsync(() => {
      fixture.detectChanges();
      component.ngOnInit();
      expect(chService.getCulturalHeritagesWithSize).toHaveBeenCalledWith(0, 10); // page index is 0 
      tick();

      expect(component.totalPages).toEqual(1);
      expect(component.pageIndex).toEqual(0);
      expect(component.error).toEqual(undefined);

      expect(component.dataSource.length).toBe(2);
      expect(component.error).toBe(undefined);

      //should display fetched cultural heritages
      fixture.detectChanges();
      tick();
      fixture.detectChanges();


      tick();
      fixture.detectChanges();

      // let chs: DebugElement[] = fixture.debugElement.queryAll(By.css('ng-container'));
      // expect(chs.length).toBe(0); 
      // expect(chs[0].nativeElement.textContent).toContain("opis1");
      // console.log("----------------------------") 
      // console.log(chs[0].nativeElement)
      // expect(chs[0].nativeElement.textContent).toContain("naziv1");

      // expect(chs[1].nativeElement.textContent).toContain("opis2");
      // expect(chs[1].nativeElement.textContent).toContain("naziv2");
    }));
  })

  fdescribe('deleteCH()', () => {
    it('should delete', fakeAsync(() => {
      
      component.ngOnInit();

      tick();

      fixture.detectChanges();

      component.selectedCH = component.dataSource[1];
      fixture.detectChanges();
      component.deleteCH(2);
      flush();

      expect(chService.delete).toHaveBeenCalledWith(2);

      fixture.detectChanges();

      // let comments: DebugElement[] = fixture.debugElement.queryAll(By.css('.comment-root'));
      // expect(comments.length).toBe(1);

    }));
  })


});
