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
    let authServiceMock = {
        getRole: jasmine.createSpy('getRole')
        .and.returnValue(of("USER")),

        getId: jasmine.createSpy('getId')
        .and.returnValue(of(3))
      }
    
    const mockComments = [ 
        new Comment({
            id: 1,
            authenticatedUserID: 4,
            content: "Duis at velit eu est congue elementum. In hac habitasse platea dictumst.",
            culturaHeritageID: 1,
            imageUri: "http://localhost:8080/api/files/1",
            userName: "Margene Weatherwax"
        }),
        new Comment({
            id: 2,
            authenticatedUserID: 3,
            content: "Duis bibendum.",
            culturaHeritageID: 1,
            imageUri: null,
            userName: "Sima Matas",
        }),
        new Comment({
            id: 3,
            authenticatedUserID: 3,
            content: "Duis bibendum2.",
            culturaHeritageID: 1,
            imageUri: null,
            userName: "Sima Matas",
        })
    ];

    let commServiceMock = {
      getComments: jasmine.createSpy('getComments')
        .and.returnValue(of({
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
        }
        )),

        deleteComment: jasmine.createSpy('deleteComment')
        .and.returnValue(of({}))
      }
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
    expect(component.lastPage).toBe(false);
    expect(component.totalPages).toEqual(4);
    expect(component.total).toEqual(12);
    expect(component.page).toEqual(1);
    expect(component.error).toEqual(null);
  
    expect(component.commentList.length).toBe(3);
    expect(component.error).toBe(null);

    //should display fetched comments
    fixture.detectChanges();
    tick();
    fixture.detectChanges();


    let comments: DebugElement[] = fixture.debugElement.queryAll(By.css('.comment-root'));
    expect(comments.length).toBe(3); 
    expect(comments[0].nativeElement.textContent).toContain("Margene Weatherwax");
    expect(comments[0].nativeElement.textContent).toContain("Duis at velit eu est congue elementum. In hac habitasse platea dictumst.");

    expect(comments[1].nativeElement.textContent).toContain("Sima Matas");
    expect(comments[1].nativeElement.textContent).toContain("Duis bibendum.");

    expect(comments[2].nativeElement.textContent).toContain("Sima Matas");
    expect(comments[2].nativeElement.textContent).toContain("Duis bibendum2.");   
  }));
})

  describe('deleteComment()', () => {
    it('should delete', fakeAsync(() => {
      component.chID = 1;
      fixture.detectChanges();
      component.deleteComment(3);
      tick();

      expect(commService.deleteComment).toHaveBeenCalledWith(3); 

  
      fixture.detectChanges();
    
      let comments: DebugElement[] = fixture.debugElement.queryAll(By.css('.comment-root'));
      expect(comments.length).toBe(2); 
    
    }));
  })
});
