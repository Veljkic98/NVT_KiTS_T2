import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { NgxPaginationModule } from 'ngx-pagination';

import { Comment } from 'src/app/models/comment.model';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { CommentService } from 'src/app/services/comments-service/comment.service';

import { CommentsComponent } from './comments.component';
import { DebugElement } from '@angular/core';

describe('CommentsComponent', () => {
  let component: CommentsComponent;
  let fixture: ComponentFixture<CommentsComponent>;
  let authService: AuthService;
  let commService: CommentService;

  beforeEach(async () => {
    const authServiceMock = {
        getRole: jasmine.createSpy('getRole')
        .and.returnValue(of('USER')),

        getId: jasmine.createSpy('getId')
        .and.returnValue(of(3))
      };

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

    const commServiceMock = {
      getComments: jasmine.createSpy('getComments')
        .and.returnValue(of({
            content: mockComments,
            empty: false,
            first: true,
            last: true,
            number: 0,
            numberOfElements: 2,
            pageable: {sort: {sorted: true, unsorted: false, empty: false}, offset: 0, pageNumber: 0, pageSize: 2},
            size: 2,
            sort: {sorted: true, unsorted: false, empty: false},
            totalElements: 2,
            totalPages: 1
        }
        )),

        deleteComment: jasmine.createSpy('deleteComment')
        .and.returnValue(of({})),

        postComment: jasmine.createSpy('postComment')
        .and.returnValue(of(new Comment({
          id: 5,
          content: 'This is awesome, we will pass 2 exams with one project!!',
          authenticatedUserID: 3,
          culturaHeritageID: 1,
          imageUri: null,
          userName: 'Sima Matas'
        })))
      };
    await TestBed.configureTestingModule({
      declarations: [ CommentsComponent ],
      providers:    [
        {provide: AuthService, useValue: authServiceMock },
        {provide: CommentService, useValue: commServiceMock } ],
        imports : [ NgxPaginationModule ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentsComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    commService = TestBed.inject(CommentService);
    expect(component.commentList).toEqual(undefined);
    expect(component.page).toBe(1);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
  it('should fetch all comments for one cultural heritage on init (with paggination)', fakeAsync(() => {
    component.chID = 1;
    fixture.detectChanges();
    component.ngOnInit();
    expect(commService.getComments).toHaveBeenCalledWith(1, 0);
    tick();

    expect(component.chID).toBe(1);
    expect(component.lastPage).toBe(true);
    expect(component.totalPages).toEqual(1);
    expect(component.total).toEqual(2);
    expect(component.page).toEqual(1);
    expect(component.error).toEqual(null);

    expect(component.commentList.length).toBe(2);
    expect(component.error).toBe(null);

    // should display fetched comments
    fixture.detectChanges();
    tick();
    fixture.detectChanges();


    const comments: DebugElement[] = fixture.debugElement.queryAll(By.css('.comment-root'));
    expect(comments.length).toBe(2);
    expect(comments[0].nativeElement.textContent).toContain('Margene Weatherwax');
    expect(comments[0].nativeElement.textContent).toContain('Duis at velit eu est congue elementum. In hac habitasse platea dictumst.');

    expect(comments[1].nativeElement.textContent).toContain('Sima Matas');
    expect(comments[1].nativeElement.textContent).toContain('Duis bibendum.');
  }));
});

  describe('deleteComment()', () => {
    it('should delete', fakeAsync(() => {
      component.chID = 1;
      fixture.detectChanges();
      component.deleteComment(2);
      tick();

      expect(commService.deleteComment).toHaveBeenCalledWith(2);


      fixture.detectChanges();

      const comments: DebugElement[] = fixture.debugElement.queryAll(By.css('.comment-root'));
      expect(comments.length).toBe(1);

    }));
  });
  describe('addComment()', () => {
    it('should delete', fakeAsync(() => {
      component.chID = 1;
      component.content = 'This is awesome, we will pass 2 exams with one project!!';
      component.url = null;
      fixture.detectChanges();
      component.addComment();
      tick();

      expect(commService.postComment).toHaveBeenCalledWith(component.chID, 'This is awesome, we will pass 2 exams with one project!!', component.url);
      tick();
      fixture.detectChanges();

      const comments: DebugElement[] = fixture.debugElement.queryAll(By.css('.comment-root'));
      expect(comments.length).toBe(3);

      expect(comments[2].nativeElement.textContent).toContain('Sima Matas');
      expect(comments[2].nativeElement.textContent).toContain('This is awesome, we will pass 2 exams with one project');
      expect(component.content).toBe('');
    }));
  });
});
