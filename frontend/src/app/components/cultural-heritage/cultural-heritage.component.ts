import { Component, Input, OnInit, Output, EventEmitter, SimpleChanges, OnChanges } from '@angular/core';

import { CulturalHeritageService } from '../../services/cultural-heritage-service/cultural-heritage.service';
import { CulturalHeritage } from '../../models/cultural-heritage.model';
import { AuthService } from '../../services/auth-service/auth.service';


@Component({
  selector: 'app-cultural-heritage',
  templateUrl: './cultural-heritage.component.html',
  styleUrls: ['./cultural-heritage.component.css']
})
export class CulturalHeritageComponent implements OnInit, OnChanges {

  @Input() chID: number;
  isSubscribed: Boolean = false;

  loading = true;
  ch: CulturalHeritage;
  error: string;
  openedSection = false;

  @Output() closeDetails: EventEmitter<void> = new EventEmitter();

  constructor(
    private chService: CulturalHeritageService,
    private authService: AuthService
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    this.isSub()
    if (!changes.chID.firstChange) {
      this.getCH();
    }
  }

  ngOnInit(): void {
    this.isSub()
    console.log(this.isSubscribed)
    this.getCH();
  }

  /**
   * check if user is subscribed to choosen CH
   */
  isSub() {
    this.isSubscribed = false;

    this.authService.getSubscriptions()
    .subscribe(
      data => {
        if (data) {
          data.forEach(element => {
            if (element.id === this.chID) {
              this.isSubscribed = true;
            }
          });
        }
      }
    );
  }

  /**
   * 
   */
  subscribe() {
    console.log("sub")

    this.chService.subscribe(this.chID)
    .subscribe(
      response => {
        console.log(response.statusText);
        if (response.statusText == "OK") {
          this.isSubscribed = true;
        }
      }
    );
  }

  /**
   * TODO: 
   */
  unsubscribe() {
    console.log("unsub")

    this.chService.unsubscribe(this.chID)
    .subscribe(
      response => {
        console.log(response.statusText);
        if (response.statusText == "OK") {
          this.isSubscribed = false;
        }
      }
    );
  }

  getCH(): void {
    this.chService.getOne(this.chID)
      .subscribe(
        data => {
          this.loading = false;
          this.ch = data;
          this.error = null;
        },
        error => {
          this.error = 'Something went wrong please try again.';
          this.loading = false;
        });
  }

  close(): void {
    this.closeDetails.emit();
  }

  calcAvgChangedRating({ rating, oldRating }): void {
    this.ch.avgRating = (this.ch.avgRating * this.ch.totalRatings - oldRating + rating) / this.ch.totalRatings;
  }

  calcAvgAddedRating(rating: number): void {
    this.ch.avgRating = (this.ch.avgRating * this.ch.totalRatings + rating) / (this.ch.totalRatings + 1);
    this.ch.totalRatings++;
  }
}
