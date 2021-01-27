import { Overlay } from '@angular/cdk/overlay';
import { ComponentFixture, fakeAsync, flush, TestBed, tick } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { CHSubtype } from 'src/app/models/ch-subtype.model';
import { CHType } from 'src/app/models/ch-type.model';
import { Page } from 'src/app/models/page.model';
import { CHTypeService } from 'src/app/services/ch-type-service/ch-type.service';
import { CHTypesComponent } from '../ch-types/ch-types.component';

import { AddNewTypeComponent } from './add-new-type.component';

describe('AddNewTypeComponent', () => {
  let component: AddNewTypeComponent;
  let fixture: ComponentFixture<AddNewTypeComponent>;
  let router: Router;
  let typeService: CHTypeService;


  let typeServiceMock = {
    getTypes: jasmine.createSpy('getTypes').and.returnValue(of(new Page<CHType>(
      {
        content: [{
          id: 1,
          name: 'type1',
          subtypes: []
        },
        {
          id: 2,
          name: 'type2',
          subtypes: []
        },
        {
          id: 3,
          name: 'type3',
          subtypes: [new CHSubtype({
            id: 1,
            name: 'subtype',
            chTypeID: 3
          })]
        }],
        empty: false,
        number: 0,
        numberOfElements: 3,
        size: 3,
        totalElements: 3,
        totalPages: 1,
        last: true
      }
    ))),

    addType: jasmine.createSpy('addType').and.returnValue(of(
      new CHType({ name: 'new type', subtypes: [], id: 4 }
    ))),
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddNewTypeComponent],
      providers: [
        { provide: CHTypeService, useValue: typeServiceMock },
        MatSnackBar, Overlay,
      ],
      imports: [
        RouterTestingModule.withRoutes([{ path: 'manage/types', component: CHTypesComponent },]),
        BrowserAnimationsModule
      ],
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddNewTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    typeService = TestBed.inject(CHTypeService);
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should call get all types', fakeAsync(() => {
      component.ngOnInit();
      expect(typeService.getTypes).toHaveBeenCalled();
    }))
  })

  describe('loadTypes()', () => {
    it('should load types', fakeAsync(() => {
      component.ngOnInit();
      tick();

      component.loadTypes();
      tick();
      
      expect(typeService.getTypes).toHaveBeenCalled();
      expect(component.chTypes.length).toEqual(3);
    }))



    it('should add types ', fakeAsync(() => {
      spyOn(component, 'openSnackBar');
      const navigateSpy = spyOn(router, 'navigate');

      component.ngOnInit();
      tick();
      component.nameValid = true;
      component.type = new CHType({ name: 'new type', subtypes: [], id: 4 });
      component.name = 'new type';
      component.addType();
      tick();
      
      expect(typeService.addType).toHaveBeenCalledWith(component.type);
      expect(component.openSnackBar).toHaveBeenCalledWith(`Successfuly added ${component.name} type.`);
      expect(navigateSpy).toHaveBeenCalledWith(['/manage/types'])
      flush();
    }));
  })
});
