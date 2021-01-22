import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateChComponent } from './update-ch.component';

describe('UpdateChComponent', () => {
  let component: UpdateChComponent;
  let fixture: ComponentFixture<UpdateChComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateChComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateChComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
