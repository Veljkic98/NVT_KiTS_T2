import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AuthService } from '../services/auth-service/auth.service';

import { LoginGuard } from './login.guard';

describe('LoginGuard', () => {
  let guard: LoginGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {provide: AuthService, useValue: {}}
      ],
      imports: [
        RouterTestingModule
      ],
    });
    guard = TestBed.inject(LoginGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
