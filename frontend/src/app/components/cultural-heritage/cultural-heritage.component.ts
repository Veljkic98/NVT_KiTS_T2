import { Component, Input, OnInit, Output, EventEmitter, SimpleChanges  } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

import { CulturalHeritageService } from '../../services/cultural-heritage-serice/cultural-heritage.service';
import { CulturalHeritage } from '../../models/cultural-heritage.model';

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
  imageUrl : SafeUrl;

  @Output() closeDetails: EventEmitter<void> = new EventEmitter();

  constructor(
    private chService: CulturalHeritageService,
    private sanitizer: DomSanitizer
) { }

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
            if (this.ch.imageUri) {
              this.getImageFromService();
            }
        },
        error => {
            this.error = "Something went wrong please try again.";
            this.loading = false;
        });
  }

  getImageFromService() {
    this.chService.getChImage(this.ch.imageUri)
    .subscribe(
      data => {
        let unsafeImageUrl = URL.createObjectURL(data);
        this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(unsafeImageUrl);
      }, 
      error => {
        console.log(error);
      }
    )
}

  close() {
    this.closeDetails.emit();
  }
}
