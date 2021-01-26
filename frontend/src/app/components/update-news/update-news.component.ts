import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { News } from 'src/app/models/news.model';
import { NewsService } from 'src/app/services/news-service/news-service.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-update-news',
  templateUrl: './update-news.component.html',
  styleUrls: ['./update-news.component.css']
})
export class UpdateNewsComponent implements OnInit {

  newsID: number;
  news: News;
  url: File;
  isFileChosen = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private newsService: NewsService,
    private snackBar: MatSnackBar,
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
        // console.log(data)
      });
  }

  async update() {

    let file: File;
    // if new file hasn't been chosen then create file from existing image
    if (!this.url) {

      // If no image is present, then file shoould be null
      if (!this.news.imageUri) {
        file = null;
      }
      else {
        file = await fetch(this.news.imageUri)
          .then(r => r.blob())
          .then(blobFile => new File([blobFile], 'slika.png', { type: 'image/png' }));
      }
    }
    // if file has been chosen
    else {
      file = this.url;
    }


    this.newsService.update(this.news, file)
      .subscribe(() => {
        // console.log(response)
        this.router.navigate([`/manage/news/${this.news.culturalHeritageID}`]);
        this.openSnackBar(`Successfuly updated ${this.news.heading}.`);
      },
        (error) => {this.openSnackBar(`There was a problem updating ${this.news.heading}.`); });
  }

  /**
   * Take url of choosen image.
   *
   * @param event
   */
  onSelectFile(event): void {
    if (event.target.files && event.target.files[0]) {
      this.url = event.target.files[0];
      this.isFileChosen = true;
    }
  }


  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Dismiss', {
      duration: 4000,
    });
  }
}
