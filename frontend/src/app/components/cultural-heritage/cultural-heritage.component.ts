import { Component, Input, OnInit, Output, EventEmitter, SimpleChanges, OnChanges  } from '@angular/core';

import { CulturalHeritageService } from '../../services/cultural-heritage-service/cultural-heritage.service';
import { CulturalHeritage } from '../../models/cultural-heritage.model';

@Component({
  selector: 'app-cultural-heritage',
  templateUrl: './cultural-heritage.component.html',
  styleUrls: ['./cultural-heritage.component.css']
})
export class CulturalHeritageComponent implements OnInit, OnChanges {
  @Input() chID: number;
  loading = true;
  ch: CulturalHeritage;
  error: string;
  openedSection = false;

  @Output() closeDetails: EventEmitter<void> = new EventEmitter();

  constructor(
    private chService: CulturalHeritageService
) { }

  ngOnChanges(changes: SimpleChanges): void {
    if (!changes.chID.firstChange) {
      this.getCH();
    }
  }

  ngOnInit(): void {
   this.getCH();
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
}
