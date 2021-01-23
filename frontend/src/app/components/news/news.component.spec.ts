import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { NewsService } from 'src/app/services/news-service/news-service.service';

import { NewsComponent } from './news.component';

describe('NewsComponent', () => {
  let component: NewsComponent;
  let fixture: ComponentFixture<NewsComponent>;
  let newsService: any;

  beforeEach(async () => {
    let newsServiceMock = {
        getNews: jasmine.createSpy('getNews').and
                        .returnValue(of({body: {}}))
    }

    await TestBed.configureTestingModule({
      declarations: [ NewsComponent ],
      imports: [],
      providers: [
        {provide: NewsService, useValue: newsServiceMock},
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call delete news', () => {
    component.deleteNews(1);

    expect(newsService.deleteNews).toHaveBeenCalledWith(1);
  });
});
