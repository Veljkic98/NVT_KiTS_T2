import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { AuthService } from 'src/app/services/auth-service/auth.service';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: any;

  beforeEach(async () => {


    const authServiceMock = {
      login: jasmine.createSpy('login')
      .and.returnValue(of({accessToken: 'eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6ImhlbGVuQGhvdG1haWwuY29tIiwiYXVkIjoid2ViIiwiaWF0IjoxNjA3NTM1NDMwLCJleHAiOjE2MDc1MzcyMzB9.eqYWSx436ToXTEXLMiRg4bf0S8iRcNxEQLkOocFC28WpG3NcE17I4YadXk8bOfDZ4Jw09NufsjbRDGtypLtcaw', expiresIn: 18000000000}))}
    

    const formBuilder = new FormBuilder();

    await TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports: [FormsModule, ReactiveFormsModule, RouterTestingModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatInputModule ],
      providers:    [
        {provide: AuthService, useValue: authServiceMock },
        {provide: FormBuilder, useValue: formBuilder } ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set local storage after successful login ', fakeAsync(() => {
    const store = {};

    spyOn(localStorage, 'getItem').and.callFake(function (key) {
      return store[key];
    });
    spyOn(localStorage, 'setItem').and.callFake(function (key, value) {
      return store[key] = value + '';
    });

    expect(component.loginForm.valid).toBeFalsy();

    component.loginForm.controls.email.setValue('admin@gmail.com');
    component.loginForm.controls.password.setValue('123');
    component.onSubmit();
    tick();

    expect(authService.login).toHaveBeenCalled();
    expect(component.submitted).toBe(true);
    expect(component.success).toBe(true);
    expect(localStorage.setItem).toHaveBeenCalled();

  }));

  it('should display password is too short error', () => {
    expect(component.loginForm.valid).toBeFalsy();

    component.loginForm.controls.email.setValue('admin@gmail.com');
    component.loginForm.controls.password.setValue('1');
    expect(component.loginForm.valid).toBeFalsy();
    component.onSubmit();

    expect(authService.login).not.toHaveBeenCalled();
    expect(component.submitted).toBe(true);
    expect(component.success).toBe(false);
    expect(component.loginForm.controls.password.errors.minlength).toBeTruthy();
  });



});






describe('LoginComponentFailure', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: any;

  beforeEach(async () => {


    const authServiceMock = {
      login: jasmine.createSpy('login')
      .and.returnValue(throwError(new Error('error')))}
    

    const formBuilder = new FormBuilder();

    await TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports: [FormsModule, ReactiveFormsModule, RouterTestingModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatInputModule ],
      providers:    [
        {provide: AuthService, useValue: authServiceMock },
        {provide: FormBuilder, useValue: formBuilder } ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    fixture.detectChanges();
  });

  it('should set error and should not set local storage after failed login ', fakeAsync(() => {
    const store = {};

    spyOn(localStorage, 'getItem').and.callFake(function (key) {
      return store[key];
    });
    spyOn(localStorage, 'setItem').and.callFake(function (key, value) {
      return store[key] = value + '';
    });

    expect(component.loginForm.valid).toBeFalsy();

    component.loginForm.controls.email.setValue('admin@gmail.com');
    component.loginForm.controls.password.setValue('123');
    component.onSubmit();
    tick();

    expect(authService.login).toHaveBeenCalled();
    expect(component.submitted).toBe(true);
    expect(component.success).toBe(false);
    expect(localStorage.setItem).not.toHaveBeenCalled();
    expect(component.error).not.toBe(''); // erro nije prazan
  }));

});
