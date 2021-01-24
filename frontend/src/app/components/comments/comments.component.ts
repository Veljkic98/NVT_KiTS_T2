import { ChangeDetectorRef, Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { CommentService } from '../../services/comments-service/comment.service';
import { AuthService } from '../../services/auth-service/auth.service';
import { Comment } from '../../models/comment.model';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit, OnChanges {
  @Input() chID: number;
  commentList: Comment[];
  page = 1;
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
    private modalService: NgbModal,
    private authService: AuthService
) {}


  ngOnInit(): void {
    this.getComments(1);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (!changes.chID.firstChange) {
      this.page = 1;
      this.getComments(this.page);
    }
  }

  getComments(page): void {
    this.commService.getComments(this.chID, page - 1)
    .subscribe(
      data => {
          this.lastPage = data.last;
          this.commentList = data.content;
          this.total = data.totalElements;
          this.page = data.number + 1;
          this.error = null;
          this.totalPages = data.totalPages;
      },
      error => {
         console.log(error);
         this.error = 'Somethnig went wrong, can not load all comments right now.';
      });
  }


  openAddModal(content): void {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.addComment();
    }, (reason) => {
      this.content = '';
    });
  }

  openDeleteModal(deleteModal, commentID): void {
    this.modalService.open(deleteModal, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.deleteComment(commentID);
    }, (reason) => {
      this.content = '';
    });
  }

  addComment(): void {
    this.commService.postComment(this.chID, this.content, this.url)
    .subscribe(
      data => {
        this.content = '';
        if (this.lastPage) {
            this.commentList.push(data);
        }
      },
      error => {
        console.log(error);
        this.content = '';
        this.error = 'Somethnig went wrong, can nott load all comments right now.';
      });

  }

  onKey(event): void { this.content = event.target.value; }

  onSelectFile(event): void {
      if (event.target.files && event.target.files[0]) {
        this.url = event.target.files[0];
      }
  }

  deleteComment(commentID: number): void {
    this.commService.deleteComment(commentID)
    .subscribe(
      data => {
        this.commentList = this.commentList.filter(el => el.id !== commentID);
      },
      error => {
        console.log(error);
      });
  }
}
