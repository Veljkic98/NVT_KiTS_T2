import { Input, SimpleChanges } from '@angular/core';
import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { News } from 'src/app/models/news.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { NewsService } from 'src/app/services/news-service/news-service.service';

@Component({
    selector: 'app-news',
    templateUrl: './news.component.html',
    styleUrls: ['./news.component.css'],
})
export class NewsComponent{
    chID: number;
    news: News[] = [];
    page = 1;
    totalPages: number;
    totalElements: number;
    error: string;
    total: number;
    content: string;
    url: string;
    lastPage: boolean;

    constructor(
      private service: NewsService,
      private route: ActivatedRoute,
      private _snackBar: MatSnackBar,
      private modalService: NgbModal,

  ) {}



    ngOnInit(): void {
        this.route.paramMap.subscribe( params =>
                this.chID = +params.get('index')
        );

        this.getNews(this.page);
    }

    ngOnChanges(changes: SimpleChanges): void {
      if (!changes.chID.firstChange) {
        this.page = 1;
        this.getNews(this.page - 1);
      }
    }



    getNews(page): void {
      this.service.getNews(this.chID, page - 1)
      .subscribe(
        data => {
            // console.log(data, ' je lasttaaa');
            this.lastPage = data.last;
            this.news = data.content;
            this.total = data.totalElements;
            this.page = data.number + 1;
            this.error = null;
        },
        error => {
           console.log(error);
           this.error = 'Can\'t load news at the moment :(';
        });
    }

    deleteNews(id): void {
      this.service.deleteNews(id).subscribe(
        data =>  {
          this.openSnackBar('Successfuly deleted the news!');
          this.getNews(this.page - 1);
        },
        error => this.openSnackBar('Can\'t delete that news.')
      );
    }

    openDeleteModal(deleteModal, news): void {
      this.modalService.open(deleteModal, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
          this.deleteNews(news.id);
      }, (reason) => {
      });
  }

    openSnackBar(message: string): void{
      this._snackBar.open(message, 'Dismiss', {
        duration: 4000,
      });
  }

}
