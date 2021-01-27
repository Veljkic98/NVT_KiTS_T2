import { TestBed, getTestBed, inject } from '@angular/core/testing';
import { CommentService } from './comment.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {fakeAsync, tick} from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { Comment } from 'src/app/models/comment.model';
import { Page } from 'src/app/models/page.model';

describe('CommentService', () => {
  let injector;
  let commentService: CommentService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

	 beforeEach(() => {

    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers:    [CommentService]
    });

    injector = getTestBed();
    commentService = TestBed.inject(CommentService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

 	it('should pass simple test', () => {
	    expect(true).toBe(true);
    });

  it('getComments() should return list of comments for chosen ch', fakeAsync(() => {
        let commentsPage: Page<Comment>;

        const mockComments = [
            new Comment({
                id: 1,
                authenticatedUserID: 4,
                content: 'Duis at velit eu est congue elementum. In hac habitasse platea dictumst.',
                culturaHeritageID: 1,
                imageUri: 'http://localhost:8080/api/files/1',
                userName: 'Margene Weatherwax'
            }),
            new Comment({
                id: 2,
                authenticatedUserID: 3,
                content: 'Duis bibendum.',
                culturaHeritageID: 1,
                imageUri: null,
                userName: 'Sima Matas',
            })
        ];

        const mockCommentPage = {
            content: mockComments,
            empty: false,
            first: true,
            last: false,
            number: 0,
            numberOfElements: 3,
            pageable: {sort: {sorted: true, unsorted: false, empty: false}, offset: 0, pageNumber: 0, pageSize: 3},
            size: 3,
            sort: {sorted: true, unsorted: false, empty: false},
            totalElements: 12,
            totalPages: 4
        };

        const page = 0;
        commentService.getComments(1, page).subscribe(res => commentsPage = res);

        const req = httpMock.expectOne('http://localhost:8080/api/comments/by-page/1/?page=0&size=3&sort=id,ASC');
        expect(req.request.method).toBe('GET');
        req.flush(mockCommentPage);

        tick();


        const content: Comment[] = commentsPage.content;
        expect(content).toBeDefined();
        expect(content.length).toEqual(2);

        expect(content[0].id).toEqual(1);
        expect(content[0].authenticatedUserID).toEqual(4);
        expect(content[0].content).toEqual('Duis at velit eu est congue elementum. In hac habitasse platea dictumst.');
        expect(content[0].imageUri).toEqual('http://localhost:8080/api/files/1');
        expect(content[0].userName).toEqual( 'Margene Weatherwax');

        expect(content[1].id).toEqual(2);
        expect(content[1].authenticatedUserID).toEqual(3);
        expect(content[1].content).toEqual('Duis bibendum.');
        expect(content[1].imageUri).toEqual(null);
        expect(content[1].userName).toEqual( 'Sima Matas');
        }));

  it('deleteComment() should query url and delete comment', fakeAsync(() => {
        commentService.deleteComment(2).subscribe(res => { });

        const req = httpMock.expectOne('http://localhost:8080/api/comments/2');
        expect(req.request.method).toBe('DELETE');
        req.flush({});

    }));

  it('postComment() should query url and save a comment', fakeAsync(() => {
        let newComment: Comment = new Comment(
        {
            content: 'Duis at velit eu est congue elementum. In hac habitasse platea dictumst.',
            culturaHeritageID: 1
        });

        const image: File = new File([''], 'imageName');

        const mockComment: Comment = {
            id: 1,
            authenticatedUserID: 4,
            content: 'Duis at velit eu est congue elementum. In hac habitasse platea dictumst.',
            culturaHeritageID: 1,
            imageUri: 'http://localhost:8080/api/files/1',
            userName: 'Margene Weatherwax'
        };

        commentService.postComment(newComment.culturaHeritageID, newComment.content, image.name).subscribe(res => newComment = res);

        const req = httpMock.expectOne('http://localhost:8080/api/comments');
        expect(req.request.method).toBe('POST');
        req.flush(mockComment);

        tick();

        expect(newComment).toBeDefined();
        expect(newComment.id).toEqual(1);
        expect(newComment.authenticatedUserID).toEqual(4);
        expect(newComment.content).toEqual('Duis at velit eu est congue elementum. In hac habitasse platea dictumst.');
        expect(newComment.imageUri).toEqual('http://localhost:8080/api/files/1');
        expect(newComment.userName).toEqual( 'Margene Weatherwax');

    }));

});
