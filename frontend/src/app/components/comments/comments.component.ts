import { ChangeDetectorRef, Component, Input, OnInit, SimpleChanges } from '@angular/core';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';

import { CommentService } from '../../services/comments-service/comment.service';
import { Comment } from '../../models/comment.model';
import { Page } from 'ngx-pagination/dist/pagination-controls.directive';

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
  content: string;
  url: string;
  lastPage: boolean;

  constructor(
    private commService: CommentService,
    private cdr: ChangeDetectorRef ,
    private modalService: NgbModal
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
          this.lastPage = data.last;
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

 
  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.addComment();
    }, (reason) => {
      this.content = '';
    });
  }

  addComment() {
    console.log(this.url);
    this.commService.postComment(this.chID, this.content, this.url)
    .subscribe(
      data => {
        this.content = '';
        if(this.lastPage)
          this.commentList.push(data);
      },
      error => {
        console.log(error);
        this.content = '';
        this.error = "Somethnig went wrong, can't load all comments right now.";
      });

  }

  onKey(event) {this.content = event.target.value;}

  onSelectFile(event) { 
      if (event.target.files && event.target.files[0]) {
        this.url = event.target.files[0];
      }
  }
}