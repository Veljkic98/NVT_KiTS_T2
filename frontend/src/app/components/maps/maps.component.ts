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
  markersArray: Marker[] = [];
  markerColors: string[];
  currentPage: number = 0;
  isPreviousButtonDisabled: boolean;
  isNextButtonDisabled: boolean;


  constructor(private culturalHeritageService: CulturalHeritageService) { }

  ngOnInit(): void {
    this.setMarkerColors();
    // this.consoleLogColors();
  }

  onMapLoad(map: Map) {
    this.map = map;
    this.addCulturalHeritagesToMap(this.currentPage);
  }


  /**
   * @param page should be a number of pageable object for backend.
   * Page starts from index 0,..
   * Iterate over all CH.  
   * Set coordinates for a specific ch.
   * Set a map marker color based on ch subtype.
   * Each subtype should be represented with one and only one color. 
   * subtype ids start from index 1. markerColors start from index 0.
   * => substract 1 from subtype in order to match begging of the markerColors array. 
   * Add marker function will render html markers on the map.
   * At the end check if previous and next buttons should be disabled.
   * 
   */
  async addCulturalHeritagesToMap(page: number) {
    let culturalHeritages: CulturalHeritage[];
    let coords: [number, number];
    let color: string;
    let ch: CulturalHeritage;

    let retval: Page = await this.culturalHeritageService.getCulturalHeritages(page).toPromise();
    culturalHeritages = retval.content;

    culturalHeritages.forEach(culturalHeritage => {
      ch = culturalHeritage;

      coords = [
        parseFloat(ch.coordinates[0]), //longitude
        parseFloat(ch.coordinates[1]), //latitude
      ];

      color = this.markerColors[ch.chsubtypeID - 1];

      this.addMarker(coords, color);
    });

    this.checkIfButtonDisabled();
  }

  addMarker(coordinates: LngLatLike, color = "blue") {
    let markerIcon: HTMLDivElement = document.createElement('div');

    //creating this: <i class="material-icons">place</i>
    let icon = document.createElement('i');             //html elem name
    icon.classList.add('material-icons');               //class name
    icon.style.fontSize = "40px";                       //font size
    icon.appendChild(document.createTextNode("place")); //google icon name
    icon.style.color = color;                            //marker color
    markerIcon.appendChild(icon);
    let marker = new Marker(markerIcon).setLngLat(coordinates).addTo(this.map);

    //add marker to the array of markers 
    this.markersArray.push(marker);

    //set drop animation
    markerIcon.style.animation = "dropMarker 0.7s ease-in";
  }

  removeCulturalHeritagesFromMap() {
    this.markersArray.forEach(marker => {
      marker.remove();
    });
    this.markersArray = [];
  }

  getPreviousPage() {
    this.removeCulturalHeritagesFromMap();
    this.currentPage -= 1;
    this.addCulturalHeritagesToMap(this.currentPage);
  }
  getNextPage() {
    this.removeCulturalHeritagesFromMap();
    this.currentPage += 1;
    this.addCulturalHeritagesToMap(this.currentPage);
  }

  async checkIfButtonDisabled() {
    let page: number;
    let retval: Page;

    //check if previous page button should be disabled
    if (this.currentPage === 0) {
      this.isPreviousButtonDisabled = true;
    }
    else {
      page = this.currentPage - 1;
      retval = await this.culturalHeritageService.getCulturalHeritages(page).toPromise();
      if (retval.content.length === 0)
        this.isPreviousButtonDisabled = true;
      else
        this.isPreviousButtonDisabled = false;
    }

    //check if next page button should be disabled
    page = this.currentPage + 1;
    retval = await this.culturalHeritageService.getCulturalHeritages(page).toPromise();
    if (retval.content.length === 0)
      this.isNextButtonDisabled = true;
    else
      this.isNextButtonDisabled = false;
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
