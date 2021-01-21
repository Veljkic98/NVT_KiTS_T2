import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalHeritagesComponent } from './cultural-heritages.component';

describe('CulturalHeritagesComponent', () => {
  let component: CulturalHeritagesComponent;
  let fixture: ComponentFixture<CulturalHeritagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CulturalHeritagesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalHeritagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
