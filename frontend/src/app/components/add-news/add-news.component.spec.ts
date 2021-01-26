import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { of } from 'rxjs';
import { News } from 'src/app/models/news.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { AddNewsComponent } from './add-news.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NewsService } from 'src/app/services/news-service/news-service.service';
import { ActivatedRouteStub } from 'src/app/testing/router-stubs';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { Overlay } from '@angular/cdk/overlay';

describe('AddNewsComponent', () => {
  let component: AddNewsComponent;
  let fixture: ComponentFixture<AddNewsComponent>;
  let authService: AuthService;
  let route: ActivatedRoute;
  let newsService: NewsService;
  let _snackBar: MatSnackBar;

  beforeEach(async () => {
    const authServiceMock = {
      getRole: jasmine.createSpy('getRole')
        .and.returnValue(of('USER')),
      getId: jasmine.createSpy('getId')
        .and.returnValue(of(1))
    };

    const mockNews = [
      new News(1, "heading 1", "content 1", 1, 1, "slika.jpg"),
      new News(2, "heading 2", "content 2", 1, 1, "slika.jpg"),
    ];

    const newsServiceMock = {
      getComments: jasmine.createSpy('getComments')
        .and.returnValue(of({
          content: mockNews,
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

      postComment: jasmine.createSpy('postComment')
        .and.returnValue(of(new News(3, "heading 3", "content 3", 1, 1, "slika.jpg"))),
    };

    const activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { index: 1 }; // we edit a student with id 1. Its id is in route url

    await TestBed.configureTestingModule({
      declarations: [AddNewsComponent],
      providers: [
        { provide: NewsService, useValue: newsServiceMock },
        { provide: ActivatedRoute, useValue: activatedRouteStub },
        { provide: AuthService, useValue: authServiceMock },
        MatSnackBar, Overlay,
      ],
      imports: [RouterTestingModule.withRoutes([])],
    })
      .compileComponents();
  });


  beforeEach(() => {
    fixture = TestBed.createComponent(AddNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    authService = TestBed.inject(AuthService);
    newsService = TestBed.inject(NewsService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should start id', fakeAsync(() => {
      fixture.detectChanges();
      component.ngOnInit();
      tick();
    }))
  })

  fdescribe('add()', () => {

  })
});
