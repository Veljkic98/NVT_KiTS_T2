import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { AuthService } from 'src/app/services/auth-service/auth.service';

import { MyProfileComponent } from './my-profile.component';
import { ActivatedRoute } from '@angular/router';
import { ActivatedRouteStub } from 'src/app/testing/router-stubs';
import { User } from 'src/app/models/user.model';

describe('MyProfileComponent', () => {
  let component: MyProfileComponent;
  let fixture: ComponentFixture<MyProfileComponent>;
  let authService: AuthService;
  let route: ActivatedRoute;

  beforeEach(async () => {
    let authServiceMock = {
        getRole: jasmine.createSpy('getRole')
        .and.returnValue(of("USER")),

        getId: jasmine.createSpy('getId')
        .and.returnValue(of(3)),

        getProfile: jasmine.createSpy('getId')
        .and.returnValue(of(new User({
          id: 3,
          firstName: "Sima",
          lastName: "Matas",
          email: "sima12@hotmail.com",
          approved: true
        })))
      }
    let fakeActivatedRoute =  new ActivatedRouteStub();
    fakeActivatedRoute.testParams = { index: 1 }; 
  
    await TestBed.configureTestingModule({
      declarations: [ MyProfileComponent ],
      providers:    [ 
        {provide: AuthService, useValue: authServiceMock },
        {provide: ActivatedRoute, useValue: fakeActivatedRoute}  
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyProfileComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    route = TestBed.inject(ActivatedRoute);
  
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
  it('should fetch user profile details and subscriptions if user is not admin', fakeAsync(() => {
    component.ngOnInit();
    expect(authService.getProfile).toHaveBeenCalled(); 
    tick();

    expect(component.user.id).toBe(3);
    expect(component.user.firstName).toBe("Sima");
    expect(component.user.lastName).toEqual("Matas");
    expect(component.user.email).toEqual("sima12@hotmail.com");
    expect(component.user.approved).toEqual(true);


    //should display fetched profile
    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const profile = fixture.debugElement.query(By.css('.profile-head')).nativeElement;
    expect(profile.textContent).toContain("Sima Matas");
    expect(profile.textContent).toContain("sima12@hotmail.com");
  }));
})
});
