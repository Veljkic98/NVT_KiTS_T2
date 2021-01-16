import { Component, OnInit } from '@angular/core';
import {Map, Marker} from 'mapbox-gl';

@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrls: ['./maps.component.css']
})
export class MapsComponent implements OnInit {
  //default values
  zoom: number = 5;
  latitude: number = 47;
  longitude: number = 13;
  center: [number, number] = [this.longitude, this.latitude];
  maxBounds: number[][] =[[-180,-85], [180,85]];
  style: string = 'mapbox://styles/tim2/ckjgzxl2koofq19qkxsa2ogt0';
  map: Map;
  
  constructor() { }

  ngOnInit(): void {

    setTimeout( ()=> {
      
    }, 5000)
    
  }

  onMapLoad(map: Map){
    this.map = map;
    let marker = new Marker().setLngLat(this.center).addTo(this.map);
  }

}
