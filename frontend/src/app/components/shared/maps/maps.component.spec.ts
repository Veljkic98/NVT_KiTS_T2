import { HttpClientTestingModule } from '@angular/common/http/testing';
import { IterableDiffers } from '@angular/core';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { Map, Marker, MapboxOptions } from 'mapbox-gl';
import { of } from 'rxjs';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';

import { MapsComponent } from './maps.component';

describe('MapsComponent', () => {
  let component: MapsComponent;
  let fixture: ComponentFixture<MapsComponent>;
  let culturalHeritageService: CulturalHeritageService;
  let  iterableDiffers: IterableDiffers;

  beforeEach(async () => {

    let chServiceMock = {}


    await TestBed.configureTestingModule({
      declarations: [ MapsComponent ],
      providers: [
        {provide: CulturalHeritageService, userValue: chServiceMock}
      ],
      imports: [HttpClientTestingModule], 
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MapsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    culturalHeritageService = TestBed.inject(CulturalHeritageService);

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  
  describe('addMarker()', () => {
    it('should put markers in the array of markers', fakeAsync(() => {
      fixture.detectChanges();
      
      component.map = new Map({center: [44, 13], container: document.createElement('div')} );
      component.addMarker([47, 13], 'red', 1);
      tick();

      expect(component.markersArray.length).toEqual(1);
    }))
  })

  describe('removeCulturalHeritagesFromMap()', () => {
    it('should remove markers from the array of markers', fakeAsync(() => {
      fixture.detectChanges();
      
      component.map = new Map({center: [44, 13], container: document.createElement('div')} );
      component.addMarker([47, 13], 'red', 1);
      component.addMarker([48, 13], 'red', 1);
      component.addMarker([49, 13], 'red', 1);
      component.removeCulturalHeritagesFromMap();
      tick();

      expect(component.markersArray.length).toEqual(0);
    }))
  })

});
