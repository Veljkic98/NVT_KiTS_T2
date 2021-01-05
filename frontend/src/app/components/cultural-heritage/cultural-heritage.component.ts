import { Component, Input, OnInit, Output, EventEmitter, SimpleChanges  } from '@angular/core';

import { CulturalHeritageService } from '../../services/cultural-heritage-serice/cultural-heritage.service';
import { CulturalHeritage } from '../../models/cultural-heritage.model';
import { AuthService } from '../../services/auth-service/auth.service';


@Component({
  selector: 'app-cultural-heritage',
  templateUrl: './cultural-heritage.component.html',
  styleUrls: ['./cultural-heritage.component.css']
})
export class CulturalHeritageComponent implements OnInit {
  @Input() chID : number;
  loading = true;
  ch: CulturalHeritage
  error: string;
  openedSection : boolean = false;  

  @Output() closeDetails: EventEmitter<void> = new EventEmitter();

  constructor(
    private chService: CulturalHeritageService,
    private authService: AuthService
) {}

  ngOnChanges(changes: SimpleChanges) {
    if (!changes.chID.firstChange) {
      this.getCH();
    }
  }

  ngOnInit(): void {
   this.getCH();
  }

  getCH() {
    this.chService.getOne(this.chID)
    .subscribe(
        data => {
            this.loading = false;
            this.ch = data;
            this.error = null;
        },
        error => {
            this.error = "Something went wrong please try again.";
            this.loading = false;
        });
  }

  close() {
    this.closeDetails.emit();
  }

  calcAvgChangedRating({rating, oldRating}): void {
    this.ch.avgRating = (this.ch.avgRating * this.ch.totalRatings - oldRating + rating )  / this.ch.totalRatings;
  }

  calcAvgAddedRating(rating: number): void {
    this.ch.avgRating = (this.ch.avgRating * this.ch.totalRatings + rating ) / (this.ch.totalRatings + 1);
    this.ch.totalRatings++;
  }
}
