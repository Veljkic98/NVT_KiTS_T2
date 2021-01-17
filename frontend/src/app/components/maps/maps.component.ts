import { Component, OnInit } from '@angular/core';
import {Map, Marker} from 'mapbox-gl';
import {CulturalHeritageService} from '../../services/cultural-heritage-serice/cultural-heritage.service';
import {CulturalHeritage} from '../../models/cultural-heritage.model'

@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrls: ['./maps.component.css']
})
export class MapsComponent implements OnInit {
  //default map values
  zoom: number = 5;
  latitude: number = 47;
  longitude: number = 13;
  center: [number, number] = [this.longitude, this.latitude];
  maxBounds: number[][] =[[-180,-85], [180,85]];
  style: string = 'mapbox://styles/tim2/ckjgzxl2koofq19qkxsa2ogt0';
  map: Map;
  markerIcon: HTMLDivElement;
  iconColors: string[];
  //CulturalHeritages
  culturalHeritage: CulturalHeritage;
  
  constructor(private culturalHeritageService: CulturalHeritageService) { }

  ngOnInit(): void {
    this.iconColors =  ["Teal", "Lavender", "PaleVioletRed", "LightSalmon", "Orange", "YellowGreen", "Indigo", "Silver", "Moccasin", "DarkBlue", "Bisque", "SeaGreen", "LightSlateGray", "DarkKhaki", "DarkViolet", "MediumPurple", "DarkSlateGray", "DarkOrange", "LightGrey", "Linen", "DarkSalmon", "PaleGoldenRod", "HoneyDew", "Grey", "LightYellow", "SaddleBrown", "DarkGray", "Cornsilk", "SlateGray", "Chocolate", "Turquoise", "PowderBlue", "Gold", "MidnightBlue", "SpringGreen", "OldLace", "Peru", "MediumAquaMarine", "Blue", "White", "FireBrick", "LemonChiffon", "BurlyWood", "AliceBlue", "Red", "Aquamarine", "LightCyan", "MediumSpringGreen", "Fuchsia"];
    // this.consoleLogColors();
    this.getCH();

  }
  getCH(){
    this.culturalHeritageService.getCHCoordinates(0);
  }

  onMapLoad(map: Map){
    this.map = map;

    this.addMarker(this.center, this.iconColors[3]);
  }

  addMarker(coordinates, color="red"){
    this.markerIcon = document.createElement('div');

    //creating this: <i class="material-icons">place</i>
    let icon = document.createElement('i');             //html elem name
    icon.classList.add('material-icons');               //class name
    icon.style.fontSize = "40px";                       //font size
    icon.appendChild(document.createTextNode("place")); //google icon name
    icon.style.color = color;                            //marker color
    this.markerIcon.appendChild(icon);
    let marker = new Marker(this.markerIcon).setLngLat(coordinates).addTo(this.map);
  }

  //this is only for debuging so you can see colors array
  consoleLogColors(){
    for(let i = 0; i < this.iconColors.length; i++){
      // console.log(`%c ${this.iconColors[i]}`, `color: ${this.iconColors[i]}`);
      console.log(`%c     `, `background-color: ${this.iconColors[i]}`);
    }
  }


}
