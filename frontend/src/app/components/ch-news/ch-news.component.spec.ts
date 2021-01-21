import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChNewsComponent } from './ch-news.component';

describe('ChNewsComponent', () => {
  let component: ChNewsComponent;
  let fixture: ComponentFixture<ChNewsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChNewsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
