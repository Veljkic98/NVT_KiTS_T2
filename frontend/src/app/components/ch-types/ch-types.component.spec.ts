import { Overlay } from '@angular/cdk/overlay';
import { HttpClientModule } from '@angular/common/http';
import { DebugElement } from '@angular/core';
import { ComponentFixture, fakeAsync, flush, TestBed, tick } from '@angular/core/testing';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { By } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';
import { Observable, of } from 'rxjs';
import { CHType } from 'src/app/models/ch-type.model';
import { News } from 'src/app/models/news.model';
import { Page, PageEnchanced } from 'src/app/models/page.model';
import { CHSubtypeService } from 'src/app/services/ch-subtype-service/ch-subtype.service';
import { CHTypeService } from 'src/app/services/ch-type-service/ch-type.service';
import { NewsService } from 'src/app/services/news-service/news-service.service';
import { ActivatedRouteStub } from 'src/app/testing/router-stubs';
import { CHTypesComponent } from './ch-types.component';


fdescribe('CHTypesComponent', () => {
  let component: CHTypesComponent;
  let fixture: ComponentFixture<CHTypesComponent>;
  let service: any;
  let serviceSubtypes: any;
  let _snackBar: any;
  let modalService: any;
  let route: any;


  beforeEach(() => {
    let chTypesService = {
        getTypes: jasmine.createSpy('getTypes').and
                        .returnValue(of(new PageEnchanced<CHType>(
                            {
                                content:[{
                                  id: 1,
                                  name: "type1",
                                  subtypes: []
                                },
                                {
                                  id: 2,
                                  name: "type2",
                                  subtypes: []
                                },
                                {
                                  id: 3,
                                  name: "type3",
                                  subtypes: []
                                }],
                                id: 1,
                                empty: false,
                                number:0,
                                numberOfElements:3,
                                size:3,
                                totalElements:12,
                                totalPages:6,
                                last:false
                              }                     
                        ))),
       
    }

    let subtypeServices = {
        deleteSubtype: jasmine.createSpy('deleteSubtype').and 
                               .returnValue(of(new Observable())),
    }


 
    
    TestBed.configureTestingModule({
      declarations: [ CHTypesComponent ],
      imports: [NgxPaginationModule, MatDialogModule, MatTableModule, BrowserAnimationsModule ],
      providers: [
        {provide: CHTypeService, useValue: chTypesService},
        {provide: CHSubtypeService, useValue: subtypeServices},
        MatSnackBar, Overlay, NgbModal,
      ]
    });

    fixture = TestBed.createComponent(CHTypesComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(CHTypeService);
    serviceSubtypes = TestBed.inject(CHSubtypeService);

  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch types on load', fakeAsync(() => {
    component.ngOnInit();
    expect(service.getTypes).toHaveBeenCalledWith(0);
    tick();

    expect(component.chTypes.length).toBe(3);
    expect(component.chTypes[0].id).toEqual(1);
    expect(component.chTypes[0].name).toEqual('type1');
    
    expect(component.chTypes[1].id).toEqual(2);
    expect(component.chTypes[1].name).toEqual('type2');

    fixture.detectChanges();
    tick();
    fixture.detectChanges();


    let allCells: DebugElement[] = fixture.debugElement.queryAll(By.css('table tr td'));
    expect(allCells[0].nativeElement.textContent).toContain('Type1');
    expect(allCells[5].nativeElement.textContent).toContain('Type2');
    expect(allCells[10].nativeElement.textContent).toContain('Type3');



  }));

  it('should call delete subtype', fakeAsync( () => {
    component.deleteSubtype(1);
    flush();
    expect(serviceSubtypes.deleteSubtype).toHaveBeenCalledWith(1); // brisanje s id-em 1
    expect(service.getTypes).toHaveBeenCalledWith(0);  // poziva za getTypes za page 0
    
  }));
 
});