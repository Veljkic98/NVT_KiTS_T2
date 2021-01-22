import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { News2 } from 'src/app/models/news.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { NewsService } from 'src/app/services/news-service/news-service.service';

@Component({
  selector: 'app-add-news',
  templateUrl: './add-news.component.html',
  styleUrls: ['./add-news.component.css']
})
export class AddNewsComponent implements OnInit {

  news: News2 = new News2();
  isFileChosen: boolean = false;


  constructor(
    private route: ActivatedRoute,
    private newsService: NewsService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.news.culturalHeritageID = +params.get('chid');
    });

    this.news.adminID = this.authService.getId();
  }

  /**
   * Take url of choosen image.
   * 
   * @param event 
   */
  onSelectFile(event): void {
    if (event.target.files && event.target.files[0]) {
      this.news.imageUri = event.target.files[0];
      this.isFileChosen = true;
    }
  }

  add() {
    this.newsService.add(this.news)
    .subscribe(response => {
      console.log(response);
    })
  }

}
