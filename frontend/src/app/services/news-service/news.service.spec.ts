import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { News } from 'src/app/models/news.model';
import { Page } from 'src/app/models/page.model';
import { NewsService } from './news-service.service';


describe('NewsService', () => {
    let injector;
    let newsService: NewsService;
    let httpMock: HttpTestingController;
    let httpClient: HttpClient;

	   beforeEach(() => {

    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [NewsService]
    });

    injector = getTestBed();
    newsService = TestBed.inject(NewsService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

    afterEach(() => {
    httpMock.verify();
  });

 	  it('should pass simple test', () => {
	    expect(true).toBe(true);
    });

    it('deleteNews() should query url and delete the news', fakeAsync(() => {
        newsService.deleteNews(1).subscribe(res => { });
        const req = httpMock.expectOne('http://localhost:8080/api/news/1');
        expect(req.request.method).toBe('DELETE');
        req.flush({});
    }));

    it('getNews() should return some news', fakeAsync(() => {
        let news: News[];
        let last: boolean;
        let totalElements: number;
        let totalPages: number;
        let numberOfElements: number;
        let size: number;

        const mockResponse: Page<News> = new Page<News>(
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
              }
        );

        newsService.getNews(1, 0).subscribe(data => {
            news = data.content;
            numberOfElements = data.numberOfElements;
            size = data.size;
            totalElements = data.totalElements;
            totalPages = data.totalPages;
            last = data.last;
          });

        // zakucano na 3 radi eksperimenta i u news-service
        const req = httpMock.expectOne('http://localhost:8080/api/news/by-page/1/?page=0&size=3&sort=id,ASC');
        expect(req.request.method).toBe('GET');
        req.flush(mockResponse);


        tick();

        expect(news.length).toEqual(3, 'should contain given amount of news');
        expect(news[0].adminID).toEqual(1);
        expect(news[0].culturalHeritageID).toEqual(1);
        expect(news[0].id).toEqual(5);
        expect(news[0].heading).toEqual('naslov1');
        expect(news[0].content).toEqual('sadrzaj1');
        expect(news[0].imageUri).toEqual('http://localhost:8080/api/files/2');

        expect(news[1].adminID).toEqual(1);
        expect(news[1].culturalHeritageID).toEqual(1);
        expect(news[1].id).toEqual(6);
        expect(news[1].heading).toEqual('naslov2');
        expect(news[1].content).toEqual('sadrzaj2');
        expect(news[1].imageUri).toEqual('http://localhost:8080/api/files/2');

        expect(news[2].adminID).toEqual(1);
        expect(news[2].culturalHeritageID).toEqual(1);
        expect(news[2].id).toEqual(7);
        expect(news[2].heading).toEqual('naslov3');
        expect(news[2].content).toEqual('sadrzaj3');
        expect(news[2].imageUri).toEqual('http://localhost:8080/api/files/2');


    }));
});
