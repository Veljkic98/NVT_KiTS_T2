import { Component, Input, OnInit, Output, EventEmitter, SimpleChanges, OnChanges } from '@angular/core';

import { CulturalHeritageService } from '../../../../services/cultural-heritage-service/cultural-heritage.service';
import { CulturalHeritage } from '../../../../models/cultural-heritage.model';
import { AuthService } from '../../../../services/auth-service/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-cultural-heritage',
  templateUrl: './cultural-heritage.component.html',
  styleUrls: ['./cultural-heritage.component.css']
})
export class CulturalHeritageComponent implements OnInit, OnChanges {

  @Input() chID: number;
  @Output() closeDetails: EventEmitter<void> = new EventEmitter();

  isSubscribed = false;

  loading = true;
  ch: CulturalHeritage;
  error: string;
  openedSection = false;

  constructor(
    private chService: CulturalHeritageService,
    private authService: AuthService,
    private snackBar: MatSnackBar,
  ) { }

  ngOnChanges(changes: SimpleChanges): void {
    
    if (!changes.chID.firstChange) {
      this.getCH();
      if (this.authService.getRole() === 'ROLE_USER') {
        this.isSub();
      }
    }
  }

  ngOnInit(): void {
    if (this.authService.getRole() === 'ROLE_USER') {
      this.isSub();
    }

    this.getCH();
  }

  /**
   * check if user is subscribed to choosen CH
   */
  isSub(): void {
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
  subscribe(): void {
    this.chService.subscribe(this.chID)
      .subscribe(
        response => {
          this.isSubscribed = true;
          this.openSnackBar('Successfuly subscribed!');
        }, error => { this.openSnackBar('Unsuccessfuly subscribed!'); }
      );
  }

  /**
   *
   */
  unsubscribe(): void {
    this.chService.unsubscribe(this.chID)
      .subscribe(
        response => {
          this.isSubscribed = false;
          this.openSnackBar('Successfuly unsubscribed!');
        }, error => { this.openSnackBar('Unsuccessfuly unsubscribed!'); }
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

  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Dismiss', {
      duration: 4000,
    });
  }
}
