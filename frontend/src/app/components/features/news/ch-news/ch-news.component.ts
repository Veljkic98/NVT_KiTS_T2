import { ChangeDetectorRef, Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { NewsService } from 'src/app/services/news-service/news-service.service';
import { News } from 'src/app/models/news.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';

@Component({
  selector: 'app-ch-news',
  templateUrl: './ch-news.component.html',
  styleUrls: ['./ch-news.component.css']
})
export class ChNewsComponent implements OnInit, OnChanges {

  @Input() chID: number;
  newsList: News[];
  page = 1;
  totalPages: number;
  totalElements: number;
  error: string;
  total: number;
  content: string;
  url: string;
  lastPage: boolean;

  constructor(
    private newsService: NewsService,
    private authService: AuthService
) {}


  ngOnInit(): void {
    this.getNews(1);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (!changes.chID.firstChange) {
      this.page = 1;
      this.getNews(this.page);
    }
  }

  getNews(page): void {
    this.newsService.getNews(this.chID, page - 1)
    .subscribe(
      data => {
          this.lastPage = data.last;
          this.newsList = data.content;
          this.total = data.totalElements;
          this.page = data.number + 1;
          this.error = null;
      },
      error => {
         console.log(error);
         this.error = 'Somethnig went wrong, can not load all comments right now.';
      });
  }

  onKey(event): void { this.content = event.target.value; }

  onSelectFile(event): void {
      if (event.target.files && event.target.files[0]) {
        this.url = event.target.files[0];
      }
  }

}
