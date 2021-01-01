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
  page: number = 1;
  totalPages: number;
  totalElements: number;
  error: string;
  total: number;

  constructor(
    private commService: CommentService,
    private sanitizer: DomSanitizer,
    private cdr: ChangeDetectorRef 
) { }


  ngOnInit(): void {
    this.getComments(1);
  }

  ngOnChanges(changes: SimpleChanges) {
    if (!changes.chID.firstChange) {
      this.page = 1;
      this.getComments(this.page);
    }
  }

  getComments(page) {
    this.commService.getComments(this.chID, page - 1)
    .subscribe(
      data => {
          this.commentList = data.content;
          this.total = data.totalElements;
          this.page = data.number + 1;
          this.error = null;
      },
      error => {
         console.log(error);
         this.error = "Somethnig went wrong, can't load all comments right now.";
      });
  }
}