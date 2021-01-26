import { Overlay } from '@angular/cdk/overlay';
import { HttpClientModule } from '@angular/common/http';
import { DebugElement } from '@angular/core';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { By } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import { of } from 'rxjs';
import { News } from 'src/app/models/news.model';
import { Page, PageEnchanced } from 'src/app/models/page.model';
import { NewsService } from 'src/app/services/news-service/news-service.service';
import { ActivatedRouteStub } from 'src/app/testing/router-stubs';

import { NewsComponent } from './news.component';

describe('NewsComponent', () => {
  let component: NewsComponent;
  let fixture: ComponentFixture<NewsComponent>;
  let service: any;
  let snackBar: any;
  let modalService: any;
  let route: any;


  beforeEach(() => {
    const newsServiceMock = {
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
          deleteNews: jasmine.createSpy('deleteNews').and
                              .returnValue(of({body: ''}))
    };

    const activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { index: 1 }; // we edit a student with id 1. Its id is in route url

    TestBed.configureTestingModule({
      declarations: [ NewsComponent ],
      imports: [NgxPaginationModule],
      providers: [
        {provide: NewsService, useValue: newsServiceMock},
        {provide: ActivatedRoute, useValue: activatedRouteStub},
        MatSnackBar, Overlay, NgbModal
      ]
    });

    fixture = TestBed.createComponent(NewsComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(NewsService);
    route = TestBed.inject(ActivatedRoute);

  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch news on load', fakeAsync(() => {
    component.ngOnInit();
    expect(service.getNews).toHaveBeenCalledWith(1, 0);
    tick();

    expect(component.news.length).toBe(3);
    expect(component.news[0].id).toEqual(5);
    expect(component.news[0].adminID).toEqual(1);
    expect(component.news[0].heading).toEqual('naslov1');
    expect(component.news[0].content).toEqual('sadrzaj1');
    expect(component.news[0].culturalHeritageID).toEqual(1);
    expect(component.news[0].imageUri).toEqual('http://localhost:8080/api/files/2');

    expect(component.news[1].heading).toEqual('naslov2');
    expect(component.news[1].content).toEqual('sadrzaj2');

    fixture.detectChanges();
    tick();
    fixture.detectChanges();


    const newsElements: DebugElement[] = fixture.debugElement.queryAll(By.css('mat-card'));
    expect(newsElements.length).toBe(3);

    const cardTitle1 = fixture.debugElement.query(By.css('#heading1')).nativeElement;
    expect(cardTitle1.textContent).toEqual('naslov1');
    const content1 = fixture.debugElement.query(By.css('#content1')).nativeElement;
    expect(content1.textContent).toEqual('sadrzaj1');

    const cardTitle2 = fixture.debugElement.query(By.css('#heading2')).nativeElement;
    expect(cardTitle2.textContent).toEqual('naslov2');
    const content2 = fixture.debugElement.query(By.css('#content2')).nativeElement;
    expect(content2.textContent).toEqual('sadrzaj2');

    const cardTitle3 = fixture.debugElement.query(By.css('#heading3')).nativeElement;
    expect(cardTitle3.textContent).toEqual('naslov3');
    const content3 = fixture.debugElement.query(By.css('#content3')).nativeElement;
    expect(content3.textContent).toEqual('sadrzaj3');

  }));

  it('should call delete news', () => {
    component.deleteNews(1);
    expect(service.deleteNews).toHaveBeenCalledWith(1);

  });
});
