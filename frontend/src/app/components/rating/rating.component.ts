import { Component, Input, OnInit, SimpleChanges, Output, EventEmitter, OnChanges, } from '@angular/core';

import { RatingService } from '../../services/rating-service/rating.service';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit, OnChanges {
  @Input() chID: number;
  userRating = 0;
  userRated = false;
  ratingID: number;
  oldRating: number;

  @Output() calcAvgChangedRating: EventEmitter<{ rating: number, oldRating: number}> = new EventEmitter();
  @Output() calcAvgAddedRating: EventEmitter<number> = new EventEmitter();

  constructor(
    private ratingService: RatingService
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (!changes.chID.firstChange) {
      this.getUserRate();
    }
  }

  ngOnInit(): void {
   this.getUserRate();
  }

  getUserRate(): void {
    this.ratingService.getUserRating(this.chID)
    .subscribe(
        data => {
          if (data) {
            this.userRating = data.grade;
            this.userRated = true;
            this.ratingID = data.id;
            this.oldRating = this.userRating;
          } else {
            this.userRating = 0;
          }
        },
        error => {
           this.userRating = 0;
        });
  }

  rateChanged(): void {
    if (!this.userRated) {
      this.newRate();
    } else {
      if (this.oldRating !== this.userRating) {
        this.changeRate();
      }
    }
  }

  newRate(): void {
    this.ratingService.postRating(this.chID, this.userRating)
    .subscribe(
        data => {
          this.userRated = true;
          this.calcAvgAddedRating.emit(this.userRating);
          this.oldRating = this.userRating;
          this.ratingID = data.id;
        },
        error => {
           console.log(error);
        });
  }
  changeRate(): void {
    this.ratingService.updateRating(this.ratingID, this.chID, this.userRating)
    .subscribe(
        data => {
          this.userRated = true;
          this.calcAvgChangedRating.emit({
            rating: this.userRating,
            oldRating: this.oldRating
            });
          this.oldRating = this.userRating;
        },
        error => {
           console.log(error);
        });
  }
}
