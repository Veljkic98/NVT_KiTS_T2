import { Component, OnInit } from '@angular/core';
import { LngLatLike, Map, Marker } from 'mapbox-gl';
import { CulturalHeritageService } from '../../services/cultural-heritage-serice/cultural-heritage.service';
import { CulturalHeritage } from '../../models/cultural-heritage.model'
import { Page } from 'src/app/models/page.model';
@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrls: ['./maps.component.css']
})
export class MapsComponent implements OnInit {
  //default map values
  zoom: number = 3;
  latitude: number = 47;
  longitude: number = 13;
  center: [number, number] = [this.longitude, this.latitude];
  maxBounds: number[][] = [[-180, -85], [180, 85]];
  style: string = 'mapbox://styles/tim2/ckjgzxl2koofq19qkxsa2ogt0';
  map: Map;
  markerIcon: HTMLDivElement;
  markerColors: string[];


  constructor(private culturalHeritageService: CulturalHeritageService) { }

  ngOnInit(): void {
    this.setMarkerColors();
    // this.consoleLogColors();
  }

  onMapLoad(map: Map) {
    this.map = map;
    this.loadCH();
    // this.addMarker(this.center);
  }

  
  /**
   * @param page should be a number of pageable object for backend.
   * Iterate over all CH.  
   * Set coordinates for a specific ch.
   * Set a map marker color based on ch subtype.
   * Each subtype should be represented with one and only one color. 
   * subtype ids start from index 1. markerColors start from index 0.
   * => substract 1 from subtype in order to match begging of the markerColors array. 
   * Add marker function will render html markers on the map.
   * 
   */
  async loadCH(page: number = 0) {
    let culturalHeritages: CulturalHeritage[];
    let coords: [number, number];
    let color:string;
    let ch: CulturalHeritage;

    let retval: Page = await this.culturalHeritageService.getCulturalHeritages(page).toPromise();
    culturalHeritages = retval.content;

    culturalHeritages.forEach(culturalHeritage => {
      ch = culturalHeritage;
      
      coords = [
        parseFloat(ch.coordinates[0]), //longitude
        parseFloat(ch.coordinates[1]), //latitude
      ];

      color = this.markerColors[ch.chsubtypeID-1];
    
      this.addMarker(coords, color);
    });
  }

  addMarker(coordinates: LngLatLike, color = "blue") {
    this.markerIcon = document.createElement('div');

    //creating this: <i class="material-icons">place</i>
    let icon = document.createElement('i');             //html elem name
    icon.classList.add('material-icons');               //class name
    icon.style.fontSize = "40px";                       //font size
    icon.appendChild(document.createTextNode("place")); //google icon name
    icon.style.color = color;                            //marker color
    this.markerIcon.appendChild(icon);
    let marker = new Marker(this.markerIcon).setLngLat(coordinates).addTo(this.map);

    //set drop animation
    this.markerIcon.style.animation="dropMarker 0.8s ease-in";
  }

  //this is only for debuging so you can see colors array
  consoleLogColors() {
    for (let i = 0; i < this.markerColors.length; i++) {
      console.log(`%c ${this.markerColors[i]}`, `color: ${this.markerColors[i]}`);
      console.log(`%c     `, `background-color: ${this.markerColors[i]}`);
    }
  }


  setMarkerColors() {
    this.markerColors = [
      "Teal",
      "PaleVioletRed",
      "LightSalmon",
      "Orange",
      "YellowGreen",
      "Indigo",
      "DarkBlue",
      "SeaGreen",
      "LightSlateGray",
      "DarkKhaki",
      "DarkViolet",
      "MediumPurple",
      "DarkSlateGray",
      "DarkOrange",
      "LightGrey",
      "DarkSalmon",
      "Grey",
      "SaddleBrown",
      "DarkGray",
      "Red",
      "SlateGray",
      "Chocolate",
      "Turquoise",
      "PowderBlue",
      "Gold",
      "MidnightBlue",
      "SpringGreen",
      "Peru",
      "MediumAquaMarine",
      "Blue",
      "FireBrick",
      "BurlyWood",
      "Aquamarine",
      "MediumSpringGreen",
      "Fuchsia"
    ];
  }

}
