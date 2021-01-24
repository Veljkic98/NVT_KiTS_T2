import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { News, NewsRequest } from 'src/app/models/news.model';
import { NewsService } from 'src/app/services/news-service/news-service.service';

@Component({
  selector: 'app-update-news',
  templateUrl: './update-news.component.html',
  styleUrls: ['./update-news.component.css']
})
export class UpdateNewsComponent implements OnInit {

  newsID: number;
  news: News;
  // url: string;
  isFileChosen: boolean = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private newsService: NewsService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.newsID = +params.get('index');
      this.loadNews();
    });
  }

  loadNews() {
    this.newsService.getOne(this.newsID)
      .subscribe(data => {
        this.news = data;
        console.log(data)
      })
  }

  update() {

    this.newsService.update(this.news)
    .subscribe(response => {
      console.log(response)
    })
  }

  /**
   * Take url of choosen image.
   * 
   * @param event 
   */
  onSelectFile(event): void {
    if (event.target.files && event.target.files[0]) {
      // this.url = event.target.files[0];
      // console.log(this.url)
      this.news.imageUri = event.target.files[0];
      this.isFileChosen = true;
      console.log(this.news.imageUri)
    }
  }

}
