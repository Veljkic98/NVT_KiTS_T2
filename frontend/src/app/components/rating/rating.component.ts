import { Component, Input, OnInit, SimpleChanges, Output, EventEmitter, } from '@angular/core';

import { RatingService } from '../../services/rating-service/rating.service';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit {
  @Input() chID: number;
  userRating: number = 0;
  userRated: boolean = false;
  ratingID: number;
  oldRating : number;

  @Output() calcAvgChangedRating: EventEmitter<{ rating: number, oldRating: number}> = new EventEmitter();
  @Output() calcAvgAddedRating: EventEmitter<number> = new EventEmitter();

  constructor(
    private ratingService: RatingService
  ) { }

  ngOnChanges(changes: SimpleChanges) {
    if (!changes.chID.firstChange) {
      this.getUserRate();
    }
  }

  ngOnInit(): void {
   this.getUserRate();
  }
  
  getUserRate() {
    this.ratingService.getUserRating(this.chID)
    .subscribe(
        data => {
          this.userRating = data.grade;
          this.userRated = true;
          this.ratingID = data.id;
          this.oldRating = this.userRating;
        },
        error => {
           this.userRating = 0;
        });
  }

  rateChanged() {
    if (!this.userRated) {
      this.newRate();
    } else {
      if (this.oldRating !== this.userRating)
        this.changeRate();
    }
  }

  newRate() {
    this.ratingService.postRating(this.chID, this.userRating)
    .subscribe(
        data => {
          this.userRated = true;
          this.calcAvgAddedRating.emit(this.userRating);
          this.oldRating = this.userRating;
        },
        error => {
           console.log(error);
        });
  }
  changeRate() {
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
