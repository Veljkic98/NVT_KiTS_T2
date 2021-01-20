import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { LngLatLike, Map, Marker } from 'mapbox-gl';
import * as MapboxGeocoder from '@mapbox/mapbox-gl-geocoder'
import { CulturalHeritageService } from '../../services/cultural-heritage-service/cultural-heritage.service'
import { CulturalHeritage } from '../../models/cultural-heritage.model'
import { Page } from 'src/app/models/page.model';
import { environment } from 'src/environments/environment';


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
  geocoder: MapboxGeocoder;

  @Output() chChangedEvent = new EventEmitter<number>();

  constructor(private culturalHeritageService: CulturalHeritageService) { }

  ngOnInit(): void {
    this.setMarkerColors();
    // this.consoleLogColors();
  }

  @Output() coordinates = new EventEmitter<[number, number]>();
  @Input() ifNewCH: Boolean;


  /**
   * Method is trigged when a map is fully loaded.
   * @param map is object that represents the whole map.
   */
  onMapLoad(map: Map) {
    this.map = map;
    this.addCulturalHeritagesToMap(this.currentPage);

    this.geocoder = new MapboxGeocoder({ 
      accessToken: environment.mapboxApiKey,
      minLength: 7,
      types: "address",
      zoom: 6
    });
    this._addGeocoderInputEventListener();
    this.map.addControl(this.geocoder);
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
   */
  async addCulturalHeritagesToMap(page: number) {
    let culturalHeritages: CulturalHeritage[];
    let coords: [number, number];
    let color: string;

    let retval: Page = await this.culturalHeritageService.getCulturalHeritages(page).toPromise();
    culturalHeritages = retval.content;

    culturalHeritages.forEach(culturalHeritage => {
      coords = [
        parseFloat(culturalHeritage.coordinates[0]), //longitude
        parseFloat(culturalHeritage.coordinates[1]), //latitude
      ];
      color = this.markerColors[culturalHeritage.chsubtypeID - 1];
      this.addMarker(coords, color, culturalHeritage.id);
    });

    this.checkIfButtonDisabled();
  }

  /**
   * 
   * @param coordinates LngLatLike object
   * @param color string. Select one el from markerColors.
   * 
   * Creates new html element (marker for the map). 
   * The new element is pushed in the markersArray array.
   * Animation is set to each marker.
   * Each marker listens for a click event.
   * Each markerIcon has an id corresponding to cultural heritage id.
   * examples of id: "ch_1", "ch_2",...
   */
  addMarker(coordinates: LngLatLike, color = "blue", culturalHeritageID: number = null) {
    let markerIcon: HTMLDivElement = document.createElement('div');
    markerIcon.id = "ch_" + culturalHeritageID;

    markerIcon.innerHTML = `  
    <button style="outline: none; border:none; background-color: rgba(0, 0, 0, 0); cursor: pointer;">
      <i class="material-icons" 
        style="color: ${color}; font-size: 40px">
        place
      </i>
    </button>`;

    let marker = new Marker(markerIcon).setLngLat(coordinates).addTo(this.map);

    //add marker to the array of markers 
    this.markersArray.push(marker);

    //set drop animation
    markerIcon.style.animation = "dropMarker 0.7s ease-in";

    //set hover and click events for a marker
    this.addMarkerEvents(markerIcon);
  }

  /**
   * @param markerIcon is div element inside every marker
   * Method shows CH component on the right side when user clicks on a marker.
   * When user hovers marker, hover animation is set.
   */
  addMarkerEvents(markerIcon: HTMLDivElement) {
    //add animation on hover
    this._addHoverMarkerAnimation(markerIcon);

    //add animation on click
    markerIcon.addEventListener('click', () => {
      this._addSelectMarkerAnimation(markerIcon);
      this._showCHDetails(markerIcon);
    })
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

  _showCHDetails(markerIcon: HTMLDivElement) {
    let id: number = parseInt(markerIcon.id.split('ch_')[1]);
    this.chChangedEvent.emit(id);
  }
  _addHoverMarkerAnimation(markerIcon: HTMLDivElement) {
    markerIcon.addEventListener('mouseenter', () => {
      markerIcon.style.animation = null;
      markerIcon.offsetHeight; /* trigger reflow */
      markerIcon.style.animation = "hoverMarker 0.2s linear";
    })
  }

  _addSelectMarkerAnimation(markerIcon: HTMLDivElement) {
    //if there is already selected marker, reset it's size 
    this.markersArray.forEach(element => {
      let icon = element.getElement();
      icon.getElementsByTagName("i")[0].style.fontSize = "40px";
    });

    //increase size of a selected marker
    let iconImg = markerIcon.getElementsByTagName("i")[0];
    iconImg.style.animation = null;
    iconImg.offsetHeight; /* trigger reflow */
    iconImg.style.animation = "selectMarker 0.2s linear";
    iconImg.style.fontSize = "50px";
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

  /**
   * event listner on user input.
   * First remove marker if set by previous search
   * then find the location properties.
   * then add marker to the map
   */
  _addGeocoderInputEventListener(){
    this.geocoder.on('result', (event) =>{
      this._removeMarkerFromGeocoder();
      let location = this._getLocationFromGeocoder(event);
      console.log(location);
      this._addMarkerFromGeocoder(location);
    });

    this.geocoder.on('clear', () => {
      this._removeMarkerFromGeocoder();
    });
  }

  /**
   * @return value is a location with properties lng, lat, country, city, street
   * @param event is an event fired from geocoder
   * This function is extracting properties from an event.
   * how place_name_en_GB looks like: "Фрушкогорска 20, Novi Sad 21203, South Bačka, Serbia"
   */
  _getLocationFromGeocoder(event:any): object{
    let result = event.result;
    let place_name_en_GB =  result['place_name_en-GB'];
    let [street, city,region, country] = place_name_en_GB.split(", ");
    if(!country){
      country = region;
    }

    let location = {
      lat: parseFloat(result.center[0]),
      lng: parseFloat(result.center[1]),
      country: country,
      city: city,
      street: street,
    }
    return location;
  }

  _addMarkerFromGeocoder(location: any){
    let coordinates: [number, number] = [location.lat.toString(), location.lng.toString()];
    let color = "red";
    let fontSize = "60px";

    let markerIcon: HTMLDivElement = document.createElement('div');
    markerIcon.id = "geocoder_marker";
    markerIcon.innerHTML = `  
    <button style="outline: none; border:none; background-color: rgba(0, 0, 0, 0); cursor: pointer;">
      <i class="material-icons" 
        style="color: ${color}; font-size: ${fontSize}">
        place
      </i>
    </button>`;
    let marker = new Marker(markerIcon).setLngLat(coordinates).addTo(this.map);
  }
  _removeMarkerFromGeocoder(){
    try{
      let marker = document.getElementById("geocoder_marker");
      marker.remove();
    }
    catch(error){ }
  }
}