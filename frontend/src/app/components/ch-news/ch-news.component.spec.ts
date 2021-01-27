import { Overlay } from '@angular/cdk/overlay';
import { HttpClient, HttpClientModule, HttpHandler } from '@angular/common/http';
import { ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import { of } from 'rxjs';
import { News } from 'src/app/models/news.model';
import { PageEnchanced } from 'src/app/models/page.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { NewsService } from 'src/app/services/news-service/news-service.service';

import { ChNewsComponent } from './ch-news.component';

describe('ChNewsComponent', () => {
  let component: ChNewsComponent;
  let fixture: ComponentFixture<ChNewsComponent>;
  let authService: AuthService;
  let newsService: NewsService

  beforeEach(async () => {

    let authServiceMock = {
      getRole: jasmine.createSpy('getRole')
        .and.returnValue(of("ADMIN")),
    }

    let n1 = new News();
    n1.id = 1;
    n1.heading = "h1";
    n1.content = "c1";
    n1.culturalHeritageID = 1;
    n1.adminID = 1;
    n1.imageUri = "http://localhost:8080/api/files/1";

    let n2 = new News();
    n2.id = 2;
    n2.heading = "h2";
    n2.content = "c2";
    n2.culturalHeritageID = 1;
    n2.adminID = 1;
    n2.imageUri = "http://localhost:8080/api/files/1";

    const mockNews = [n1, n2];

    let newsServiceMock = {
      getNews: jasmine.createSpy('getNews').and
        .returnValue(of(new PageEnchanced<News>(
          {
            content: [{
              adminID: 1,
              content: 'sadrzaj1',
              culturalHeritageID: 1,
              heading: 'naslov1',
              id: 5,
              imageUri: 'http://localhost:8080/api/files/2'
            },
            {
              adminID: 1,
              content: 'sadrzaj2',
              culturalHeritageID: 1,
              heading: 'naslov2',
              id: 6,
              imageUri: 'http://localhost:8080/api/files/2'
            },
            {
              adminID: 1,
              content: 'sadrzaj3',
              culturalHeritageID: 1,
              heading: 'naslov3',
              id: 7,
              imageUri: 'http://localhost:8080/api/files/2'
            }],
            id: 1,
            empty: false,
            number: 0,
            numberOfElements: 3,
            size: 3,
            totalElements: 12,
            totalPages: 6,
            last: false
          }))),
    }


    await TestBed.configureTestingModule({
      declarations: [ChNewsComponent],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: newsService, useValue: newsServiceMock },
        MatSnackBar, Overlay, NgbModal, MatPaginator,
      ],
      imports:
        [
          NgxPaginationModule,
          BrowserAnimationsModule,
          HttpClientModule
        ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChNewsComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    newsService = TestBed.inject(NewsService);
    expect(component.newsList).toEqual(undefined);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  fdescribe('ngOnInit()', () => {
    it('should fetch all new on init (with paggination)', fakeAsync(() => {

      component.chID = 1;
      component.ngOnInit();


    }));
  })
});
