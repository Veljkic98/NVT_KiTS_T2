import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { VerificationPageComponent } from './verification-page.component';
import { ActivatedRoute, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';


describe('VerificationPageComponent', () => {
  let component: VerificationPageComponent;
  let fixture: ComponentFixture<VerificationPageComponent>;
  let authService: AuthService;
  let route: ActivatedRoute;

  beforeEach(async () => {
    let authServiceMock = {
        verify: jasmine.createSpy('verify')
        .and.returnValue(of({}))
      }
    let fakeActivatedRoute = {
      snapshot: {
        paramMap: convertToParamMap({
          id: '2'
        })
      }
    } 

    await TestBed.configureTestingModule({
      declarations: [ VerificationPageComponent ],
      providers:    [ 
        {provide: AuthService, useValue: authServiceMock },
        {provide: ActivatedRoute,   useValue: fakeActivatedRoute }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VerificationPageComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    route = TestBed.inject(ActivatedRoute);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  fdescribe('ngOnInit()', () => {
  it('should verify user profile and display success message', fakeAsync(() => {
    component.ngOnInit();
    expect(authService.verify).toHaveBeenCalledWith('2'); 
    tick();

    expect(component.id).toBe('2');
    expect(component.error).toBe(undefined);
    expect(component.success).toBe(true);

    //should display success message
    fixture.detectChanges();
    tick();
    fixture.detectChanges();

    const message = fixture.debugElement.query(By.css('.success')).nativeElement;
    expect(message.textContent).toContain("Your account has been successfully activated! Yo can login now.");
  }));
})
});
