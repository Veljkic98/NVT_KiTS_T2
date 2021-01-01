import { ChangeDetectorRef, Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

import { CommentService } from '../../services/comments-service/cultural-heritage.service';
import { Comment } from '../../models/comment.model';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {
  @Input() chID: number;
  commentList: Comment[];
  page: number = 0;
  totalPages: number;
  totalElements: number;
  error: string;

  constructor(
    private commService: CommentService,
    private sanitizer: DomSanitizer,
    private cdr: ChangeDetectorRef 
) { }


  ngOnInit(): void {
    this.getComments();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (!changes.chID.firstChange) {
      this.page = 0;
      this.getComments();
    }
  }

  getComments() {
    this.commService.getComments(this.chID, this.page)
    .subscribe(
      data => {
          console.log("komentari: ", data);
          this.commentList = data.content;
          console.log('conten', this.totalElements);
          console.log(this.commentList);
          this.error = null;
      },
      error => {
         console.log(error);
         this.error = "Somethnig went wrong, can't load all comments right now.";
      });

  }

}
