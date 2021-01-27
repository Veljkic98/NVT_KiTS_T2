import { Overlay } from '@angular/cdk/overlay';
import { ComponentFixture, fakeAsync, flush, TestBed, tick } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { CHSubtype } from 'src/app/models/ch-subtype.model';
import { CHSubtypeService } from 'src/app/services/ch-subtype-service/ch-subtype.service';
import { ActivatedRouteStub } from 'src/app/testing/router-stubs';
import { CHTypesComponent } from '../ch-types/ch-types.component'
import { AddSubtypeComponent } from './add-subtype.component';

describe('AddSubtypeComponent', () => {
  let component: AddSubtypeComponent;
  let fixture: ComponentFixture<AddSubtypeComponent>;
  let router: Router;
  let route: ActivatedRouteStub = new ActivatedRouteStub();
  route.testParams = { index: 1 };// we edit a subtype whnere type id is 1. Its id is in route url
  let subtypeService: CHSubtypeService;

  let mockSubtypes = [
    new CHSubtype({ name: 'festival 1', chTypeID: 1, id: 1 }),
    new CHSubtype({ name: 'festival 2', chTypeID: 1, id: 2 })
  ]

  let subtypeServiceMock = {
    getAll: jasmine.createSpy('getAll')
      .and.returnValue(of(mockSubtypes)),

    add: jasmine.createSpy('add')
      .and.returnValue(of(new CHSubtype({ name: 'festival 3', chTypeID: 1, id: 3 })))
  }



  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddSubtypeComponent],
      providers: [
        { provide: CHSubtypeService, useValue: subtypeServiceMock },
        { provide: ActivatedRoute, useValue: route },
        MatSnackBar, Overlay
      ],
      imports: [
        RouterTestingModule.withRoutes([{ path: 'manage/types', component: CHTypesComponent },]),
        BrowserAnimationsModule
      ],
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddSubtypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    subtypeService = TestBed.inject(CHSubtypeService);
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });




  describe('ngOnInit()', () => {
    it('should start init', fakeAsync(() => {
      component.ngOnInit();

      expect(subtypeService.getAll).toHaveBeenCalled();

    }))
  })

  describe('loadSubtypes()', () => {
    it('should load subtypes for a specific type', fakeAsync(() => {
      component.ngOnInit();
      tick();

      component.loadSubtypes();
      tick();
      expect(subtypeService.getAll).toHaveBeenCalled();
      expect(component.subtypes.length).toEqual(2);
    }));
  
  
    it('should add subtypes ', fakeAsync(() => {
      spyOn(component, 'openSnackBar');
      const navigateSpy = spyOn(router, 'navigate');
      fixture.detectChanges();
      component.ngOnInit();
      tick();
      component.nameValid = true;
      component.subtype = new CHSubtype({ name: 'festival 3', chTypeID: 1, id: 3 });
      component.add();
      tick();
      expect(subtypeService.add).toHaveBeenCalledWith(component.subtype);
      expect(component.openSnackBar).toHaveBeenCalledWith('Successfuly added the subtype!');
      expect(navigateSpy).toHaveBeenCalledWith(['/manage/types'])
    }));
  })
});
