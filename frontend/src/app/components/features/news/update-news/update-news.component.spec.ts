import { Overlay } from '@angular/cdk/overlay';
import { ɵComponentFactory } from '@angular/core';
import { ComponentFixture, fakeAsync, flush, TestBed, tick } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { News } from 'src/app/models/news.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { NewsService } from 'src/app/services/news-service/news-service.service';
import { ActivatedRouteStub } from 'src/app/testing/router-stubs';
import { NewsComponent } from '../news/news.component';

import { UpdateNewsComponent } from './update-news.component';

describe('UpdateNewsComponent', () => {
  let component: UpdateNewsComponent;
  let fixture: ComponentFixture<UpdateNewsComponent>;
  let router: Router;
  let authService: AuthService;
  let newsService: NewsService;

  beforeEach(async () => {

    const authServiceMock = {
      getRole: jasmine.createSpy('getRole')
        .and.returnValue(of('USER')),
      getId: jasmine.createSpy('getId')
        .and.returnValue(of(1))
    };


    const newsServiceMock = {

      getOne: jasmine.createSpy('getOne')
        .and.returnValue(of(new News(3, 'heading 3', 'content 3', 1, 1, 'slika.jpg'))),

      update: jasmine.createSpy('update')
        .and.returnValue(of(new News(3, 'HEADING 3', 'CONTENT 3', 1, 1, 'slika.jpg'))),
    };

    const activatedRouteStub: ActivatedRouteStub = new ActivatedRouteStub();
    activatedRouteStub.testParams = { index: 3 }; // we edit a news with id 3. Its id is in route url. It belongs to ch with id = 1.

    await TestBed.configureTestingModule({
      declarations: [UpdateNewsComponent],
      providers: [
        { provide: NewsService, useValue: newsServiceMock },
        { provide: ActivatedRoute, useValue: activatedRouteStub },
        { provide: AuthService, useValue: authServiceMock },
        MatSnackBar, Overlay,
      ],
      imports: [
        RouterTestingModule.withRoutes([{ path: 'manage/news/1', component: NewsComponent }, ]),
        BrowserAnimationsModule
      ],
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    authService = TestBed.inject(AuthService);
    newsService = TestBed.inject(NewsService);
    router = TestBed.inject(Router);


  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should get a news with id 3', fakeAsync(() => {
      fixture.detectChanges();
      component.ngOnInit();

      expect(newsService.getOne).toHaveBeenCalledWith(3);
      expect(component.news).toEqual(new News(3, 'heading 3', 'content 3', 1, 1, 'slika.jpg'));
    }));
  });

  describe('update()', () => {
    it('should update news with id 3', fakeAsync(() => {
      spyOn(component, 'openSnackBar');
      const navigateSpy = spyOn(router, 'navigate');

      fixture.detectChanges();
      component.ngOnInit();
      tick();

      component.news = new News(3, 'HEADING 3', 'CONTENT 3', 1, 1, null);
      const file: File = null;
      component.update();
      tick();
      expect(newsService.update).toHaveBeenCalledWith(component.news, file);

      expect(component.openSnackBar).toHaveBeenCalledWith(`Successfuly updated ${component.news.heading}.`);
      expect(navigateSpy).toHaveBeenCalledWith(['/manage/news/1']);
      flush();
    }));
  });
});
