import { ChangeDetectorRef, Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

import { CommentService } from '../../services/comments-service/cultural-heritage.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {
  @Input() chID: number;

  constructor(
    private commService: CommentService,
    private sanitizer: DomSanitizer,
    private cdr: ChangeDetectorRef 
) { }


  ngOnInit(): void {
    this.getComments();
  }

  ngOnChanges(changes: SimpleChanges) {
    console.log("ovdeee", this.chID);
    if (!changes.chID.firstChange) {
      this.getComments();
    }
  }

  getComments() {
    this.commService.getComments(this.chID)
    .subscribe(
      data => {
          console.log("komentari: ", data);
      },
      error => {
         console.log(error);
      });

  }

}
