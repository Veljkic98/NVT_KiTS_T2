import { Overlay } from '@angular/cdk/overlay';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import { of } from 'rxjs';
import { News } from 'src/app/models/news.model';
import { Page } from 'src/app/models/page.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { NewsService } from 'src/app/services/news-service/news-service.service';

import { ChNewsComponent } from './ch-news.component';

describe('ChNewsComponent', () => {
  let component: ChNewsComponent;
  let fixture: ComponentFixture<ChNewsComponent>;
  let newsService: NewsService;
  let authService: AuthService;

  beforeEach(async () => {
    const authServiceMock = {
      getRole: jasmine.createSpy('getRole')
      .and.returnValue(of('USER')),

      getId: jasmine.createSpy('getId')
      .and.returnValue(of(3))
    };

    const newsServiceMock = {
      getNews: jasmine.createSpy('getNews').and
        .returnValue(of(new Page<News>(
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
    };


    await TestBed.configureTestingModule({
      declarations: [ChNewsComponent],
      providers: [
        { provide: NewsService, useValue: newsServiceMock },
        {provide: AuthService, useValue: authServiceMock },
      ],
      imports:
      [
        NgxPaginationModule,
        BrowserAnimationsModule,
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    newsService = TestBed.inject(NewsService);
    authService = TestBed.inject(AuthService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should fetch all new on init (with paggination)', fakeAsync(() => {
      component.chID = 1;
      component.ngOnInit();
      tick();
      expect(newsService.getNews).toHaveBeenCalledWith(component.chID, 0);
    }));
  });
  describe('getNews()', () => {
    it('should get news', fakeAsync(() => {
      component.chID = 1;
      component.ngOnInit();
      tick();
      expect(newsService.getNews).toHaveBeenCalledWith(component.chID, 0);
      expect(component.lastPage).toEqual(false);
      expect(component.newsList).toEqual([{
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
      }]);

      expect(component.total).toEqual(12);
      expect(component.page).toEqual(1);
    }));
  });
});
