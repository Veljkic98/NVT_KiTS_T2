import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatMenuModule } from '@angular/material/menu';
import { RouterTestingModule } from '@angular/router/testing';
import { AuthService } from 'src/app/services/auth-service/auth.service';

import { ToolbarComponent } from './toolbar.component';

describe('ToolbarComponent', () => {
  let component: ToolbarComponent;
  let fixture: ComponentFixture<ToolbarComponent>;

  beforeEach(async () => {
    const authServiceMock = {
      getRole: jasmine.createSpy('getRole')
      .and.returnValue('ROLE_USER'),

      isLoggedIn: jasmine.createSpy('isLoggedIn')
      .and.returnValue(true)
    };

    await TestBed.configureTestingModule({
      declarations: [ ToolbarComponent ],
      providers: [
        {provide: AuthService, useValue: authServiceMock }
      ],
      imports: [
        RouterTestingModule,
        MatMenuModule
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ToolbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
