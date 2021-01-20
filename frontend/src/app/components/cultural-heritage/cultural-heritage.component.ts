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
  @Input() isSubscribed: Boolean;
  
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
    if (!changes.chID.firstChange) {
      this.getCH();
    }
  }

  ngOnInit(): void {
    console.log(this.isSubscribed)
    this.getCH();
  }

  /**
   * TODO: 
   */
  subscribe() {
    console.log("sub")
  }

  /**
   * TODO: 
   */
  unsubscribe() {
    console.log("unsub")
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
