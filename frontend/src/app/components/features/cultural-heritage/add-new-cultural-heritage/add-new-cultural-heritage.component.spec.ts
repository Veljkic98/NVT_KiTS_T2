import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewCulturalHeritageComponent } from './add-new-cultural-heritage.component';

describe('AddNewCulturalHeritageComponent', () => {
  let component: AddNewCulturalHeritageComponent;
  let fixture: ComponentFixture<AddNewCulturalHeritageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddNewCulturalHeritageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddNewCulturalHeritageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
