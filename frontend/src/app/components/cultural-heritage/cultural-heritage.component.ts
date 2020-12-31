import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

import { CulturalHeritageService } from '../../services/cultural-heritage/cultural-heritage.service';
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

  constructor(
    private chService: CulturalHeritageService,
    private sanitizer: DomSanitizer
) { }

  ngOnInit(): void {
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
        console.log(data);
        let unsafeImageUrl = URL.createObjectURL(data);
        this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(unsafeImageUrl);
      }, 

      error => {
        console.log(error);
      }
    )
}

}
