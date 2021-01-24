import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { NewsRequest } from 'src/app/models/news.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { NewsService } from 'src/app/services/news-service/news-service.service';

@Component({
  selector: 'app-add-news',
  templateUrl: './add-news.component.html',
  styleUrls: ['./add-news.component.css']
})
export class AddNewsComponent implements OnInit {

  news: NewsRequest = new NewsRequest();
  isFileChosen: boolean = false;


  constructor(
    private route: ActivatedRoute,
    private newsService: NewsService,
    private authService: AuthService,
    private _snackBar: MatSnackBar,
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
        this.openSnackBar('Successfuly added the news!')
      }, error => this.openSnackBar('Can\'t add that news.'));
  }

  openSnackBar(message: string): void {
    this._snackBar.open(message, 'Dismiss', {
      duration: 4000,
    });
  }

}
