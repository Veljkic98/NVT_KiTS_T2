import { ComponentFixture, TestBed, fakeAsync } from '@angular/core/testing';

import { RegisterComponent } from './register.component';
import { FormsModule, ReactiveFormsModule, FormBuilder } from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {of} from 'rxjs';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { User } from 'src/app/models/user.model';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authService: any;

  beforeEach(async () => {
    let authServiceMock = {
      register: jasmine.createSpy('register')
      .and.returnValue(of({body: new User({
        id: 1, 
        firstName: 'Petar', 
        lastName: 'Petrovic', 
        email: "some1667@gmail.com"
      })}))
    }

    let formBuilder = new FormBuilder();
    
    await TestBed.configureTestingModule({
      declarations: [ RegisterComponent ],
      imports: [FormsModule, ReactiveFormsModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatInputModule ],
      providers:    [ 
        {provide: AuthService, useValue: authServiceMock },
        {provide: FormBuilder, useValue: formBuilder } ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should initialize attributes ngOnInit lifecycle', () => {

      component.ngOnInit();
      fixture.detectChanges();
      expect(component.error).toBe("");
      expect(component.success).toBe(false);
    });
  });

  describe('onSubmit()', () => {
    it('should create new user', fakeAsync(() => {
      expect(component.registerForm.valid).toBeFalsy();
     
      component.registerForm.controls.firstName.setValue('Petar');
      component.registerForm.controls.lastName.setValue('Petrovic');
      component.registerForm.controls.email.setValue('some1667@gmail.com');
      component.registerForm.controls.password.setValue('12345678');
      component.registerForm.controls.confirmPass.setValue('12345678');
      expect(component.registerForm.valid).toBeTruthy();
      component.onSubmit();
      expect(authService.register).toHaveBeenCalled();
      fixture.detectChanges();
      expect(component.submitted).toBe(true);
      expect(component.success).toBe(true);
      expect(component.error).toBe("");
    }));

    it('should report validation errors', fakeAsync(() => {
      expect(component.registerForm.valid).toBeFalsy();
     
      component.registerForm.controls.firstName.setValue('Petar');
      component.registerForm.controls.lastName.setValue('Petrovic');
      component.registerForm.controls.firstName.setValue('some1667@gmail.com');
      component.registerForm.controls.password.setValue('123');
      component.registerForm.controls.confirmPass.setValue('123');
      expect(component.registerForm.valid).toBeFalsy();
      component.onSubmit();
      
      expect(authService.register).not.toHaveBeenCalled();
      expect(component.submitted).toBe(true);
      expect(component.success).toBe(false);
      expect(component.registerForm.controls.password.errors.minlength).toBeTruthy()
    }));
});
});
