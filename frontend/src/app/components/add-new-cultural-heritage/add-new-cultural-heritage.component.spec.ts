import { Overlay } from '@angular/cdk/overlay';
import { HttpClient } from '@angular/common/http';
import { ComponentFixture, fakeAsync, flush, TestBed, tick } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { CHSubtype } from 'src/app/models/ch-subtype.model';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';
import { CHSubtypeService } from 'src/app/services/ch-subtype-service/ch-subtype.service';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';

import { AddNewCulturalHeritageComponent } from './add-new-cultural-heritage.component';

describe('AddNewCulturalHeritageComponent', () => {
  let component: AddNewCulturalHeritageComponent;
  let fixture: ComponentFixture<AddNewCulturalHeritageComponent>;
  let chService: CulturalHeritageService;
  let subtypeService: CHSubtypeService;

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
        .and.returnValue(of({
          content: mockSubtypes,
          // empty: false,
          // first: true,
          // last: true,
          // number: 0,
          // numberOfElements: 2,
          // pageable: { sort: { sorted: true, unsorted: false, empty: false }, offset: 0, pageNumber: 0, pageSize: 2 },
          // size: 2,
          // sort: { sorted: true, unsorted: false, empty: false },
          // totalElements: 2,
          // totalPages: 1
        }
        )),
    }

    let chServiceMock = {
      post: jasmine.createSpy('post')
        .and.returnValue(of(mockCulturalHeritage)),
    }

    await TestBed.configureTestingModule({
      declarations: [AddNewCulturalHeritageComponent],
      providers: [
        { provide: CulturalHeritageService, useValue: chServiceMock },
        { provide: CHSubtypeService, useValue: subtypeServiceMock },
        MatSnackBar, Overlay, NgbModal, HttpClient
      ],
      imports:
        [
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
    // expect(component.dataSource).toEqual([]);
    // expect(component.pageIndex).toBe(0);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  fdescribe('ngOnInit()', () => {
    it('should fetch all subtypes on init (with paggination)', fakeAsync(() => {
      // fixture.detectChanges();
      component.ngOnInit();
      expect(subtypeService.getAll).toHaveBeenCalledWith(); // page index is 0 
      // tick();

      expect(component.subtypes.length).toBe(2);
    }));
  })

  // fdescribe('addCH()', () => {
  //   it('should add', fakeAsync(() => {

  //     // component.ngOnInit();

  //     tick();

  //     // fixture.detectChanges();

  //     component.name = "naziv1";
  //     component.description = "opis1";
  //     component.isSubtypeChosen = true;
  //     component.isFileChosen = true;
  //     // component.location = new Location({});
  //     component.url = "asdasd"
  //     // fixture.detectChanges();
      
      
  //     // component.addCH();
  //     // flush();

  //     // expect(chService.delete).toHaveBeenCalledWith(2);

  //     // fixture.detectChanges();

  //     // let comments: DebugElement[] = fixture.debugElement.queryAll(By.css('.comment-root'));
  //     // expect(comments.length).toBe(1);

  //   }));
  // })
});
