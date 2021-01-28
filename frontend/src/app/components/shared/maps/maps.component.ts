import { Component, DoCheck, EventEmitter, Input, IterableDiffer, IterableDiffers, OnInit, Output, SimpleChanges } from '@angular/core';
import { LngLatLike, Map, Marker } from 'mapbox-gl';
import * as MapboxGeocoder from '@mapbox/mapbox-gl-geocoder';
import { CulturalHeritageService } from '../../../services/cultural-heritage-service/cultural-heritage.service';
import { CulturalHeritage } from '../../../models/cultural-heritage.model';
import { Page } from 'src/app/models/page.model';
import { Location } from 'src/app/models/location.model';
import { environment } from 'src/environments/environment';


@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrls: ['./maps.component.css']
})
export class MapsComponent implements OnInit, DoCheck {
  // default map values
  zoom = 3;
  latitude = 47;
  longitude = 13;
  center: [number, number] = [this.longitude, this.latitude];
  maxBounds: number[][] = [[-180, -85], [180, 85]];
  style = 'mapbox://styles/tim2/ckjgzxl2koofq19qkxsa2ogt0';
  map: Map;
  markersArray: Marker[] = [];
  markerColors: string[];
  currentPage = 0;
  isPreviousButtonDisabled: boolean;
  isNextButtonDisabled: boolean;

  iterableDiffer: IterableDiffer<CulturalHeritage>;

  geocoder: MapboxGeocoder;

  @Output() chChangedEvent = new EventEmitter<number>();
  @Output() chLocationSelectedEvent = new EventEmitter<Location>();
  @Input() adminManagesCH = false;
  @Input() adminLocationForGeocoder: Location;
  @Input() culturalHeritages: CulturalHeritage[];


  constructor(
    private culturalHeritageService: CulturalHeritageService,
    private iterableDiffers: IterableDiffers
  ) {
    this.iterableDiffer = iterableDiffers.find([]).create(null);

  }


  ngOnInit(): void {
    this.setMarkerColors();
    // this.consoleLogColors();
  }


  /**
   * Method is trigged when a map is fully loaded.
   * Initialize map when map is loaded.
   * Load CHs from backend when map is loaded.
   * Add search for geocoding if and only if admin is logged in
   * and admin is managing CHs (adding new, updating existing).
   * @param map is object that represents the whole map.
   */
  onMapLoad(map: Map): void {
    this.map = map;
    this.addCulturalHeritagesToMap();
    if (this.adminManagesCH === true) {
      this.geocoder = new MapboxGeocoder({
        accessToken: environment.mapboxApiKey,
        minLength: 6,
        types: 'address',
        zoom: 6,
        marker: false,
      });
      this._addGeocoderInputEventListener();
      this.map.addControl(this.geocoder, 'top-right');
      // if admin is updating CH location
      if (this.adminLocationForGeocoder){
        this._addMarkerFromGeocoder(this.adminLocationForGeocoder);
        this.map.setCenter([
          parseFloat(this.adminLocationForGeocoder.longitude),
          parseFloat(this.adminLocationForGeocoder.latitude)
        ]);
      }
    }
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
  async addCulturalHeritagesToMap(): Promise<void> {
    // let culturalHeritages: CulturalHeritage[];
    let coords: [number, number];
    let color: string;

    // let retval: Page = await this.culturalHeritageService.getCulturalHeritages(page).toPromise();
    // culturalHeritages = retval.content;
    if (this.culturalHeritages) {
      this.culturalHeritages.forEach(culturalHeritage => {
        coords = [
          parseFloat(culturalHeritage.coordinates[0]), // longitude
          parseFloat(culturalHeritage.coordinates[1]), // latitude
        ];
        color = this.markerColors[culturalHeritage.chsubtypeID - 1];
        this.addMarker(coords, color, culturalHeritage.id);
      });

      this.checkIfButtonDisabled();
    }
  }

  ngDoCheck(): void {
    const changes = this.iterableDiffer.diff(this.culturalHeritages);
    if (changes) {
      this.removeCulturalHeritagesFromMap();
      this.addCulturalHeritagesToMap();
    }
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
  addMarker(coordinates: LngLatLike, color = 'blue', culturalHeritageID: number = null): void{
    const markerIcon: HTMLDivElement = document.createElement('div');
    markerIcon.id = 'ch_' + culturalHeritageID;

    markerIcon.innerHTML = `
    <button style="outline: none; border:none; background-color: rgba(0, 0, 0, 0); cursor: pointer;">
      <i class="material-icons"
        style="color: ${color}; font-size: 40px">
        place
      </i>
    </button>`;

    const marker = new Marker(markerIcon).setLngLat(coordinates).addTo(this.map);

    // add marker to the array of markers
    this.markersArray.push(marker);

    // set drop animation
    markerIcon.style.animation = 'dropMarker 0.7s ease-in';

    // set hover and click events for a marker
    this.addMarkerEvents(markerIcon);
  }

  /**
   * @param markerIcon is div element inside every marker
   * Method shows CH component on the right side when user clicks on a marker.
   * When user hovers marker, hover animation is set.
   */
  addMarkerEvents(markerIcon: HTMLDivElement): void {
    // add animation on hover
    this._addHoverMarkerAnimation(markerIcon);

    // add animation on click
    markerIcon.addEventListener('click', () => {
      this._addSelectMarkerAnimation(markerIcon);
      this._showCHDetails(markerIcon);
    });
  }

  removeCulturalHeritagesFromMap(): void{
    this.markersArray.forEach(marker => {
      marker.remove();
    });
    this.markersArray = [];
  }



  async checkIfButtonDisabled(): Promise<void> {
    let page: number;
    let retval: Page<CulturalHeritage>;

    // check if previous page button should be disabled
    if (this.currentPage === 0) {
      this.isPreviousButtonDisabled = true;
    }
    else {
      page = this.currentPage - 1;
      retval = await this.culturalHeritageService.getCulturalHeritages(page).toPromise();
      if (retval.content.length === 0) {
        this.isPreviousButtonDisabled = true;
      }
      else {
        this.isPreviousButtonDisabled = false;
      }
    }

    // check if next page button should be disabled
    page = this.currentPage + 1;
    retval = await this.culturalHeritageService.getCulturalHeritages(page).toPromise();
    if (retval.content.length === 0) {
      this.isNextButtonDisabled = true;
    }
    else {
      this.isNextButtonDisabled = false;
    }
  }

  _showCHDetails(markerIcon: HTMLDivElement): void {
    const id: number = parseInt(markerIcon.id.split('ch_')[1]);
    this.chChangedEvent.emit(id);
  }
  _addHoverMarkerAnimation(markerIcon: HTMLDivElement): void {
    markerIcon.addEventListener('mouseenter', () => {
      markerIcon.style.animation = null;
      markerIcon.offsetHeight; /* trigger reflow */
      markerIcon.style.animation = 'hoverMarker 0.2s linear';
    });
  }

  _addSelectMarkerAnimation(markerIcon: HTMLDivElement): void {
    // if there is already selected marker, reset it's size
    this.markersArray.forEach(element => {
      const icon = element.getElement();
      icon.getElementsByTagName('i')[0].style.fontSize = '40px';
    });

    // increase size of a selected marker
    const iconImg = markerIcon.getElementsByTagName('i')[0];
    iconImg.style.animation = null;
    iconImg.offsetHeight; /* trigger reflow */
    iconImg.style.animation = 'selectMarker 0.2s linear';
    iconImg.style.fontSize = '50px';
  }

  // this is only for debuging so you can see colors array
  consoleLogColors(): void {
    for (let i = 0; i < this.markerColors.length; i++) {
      console.log(`%c ${this.markerColors[i]}`, `color: ${this.markerColors[i]}`);
      console.log(`%c     `, `background-color: ${this.markerColors[i]}`);
    }
  }

  setMarkerColors(): void {
    this.markerColors = [
      'Teal',
      'PaleVioletRed',
      'LightSalmon',
      'Orange',
      'YellowGreen',
      'Indigo',
      'DarkBlue',
      'SeaGreen',
      'LightSlateGray',
      'DarkKhaki',
      'DarkViolet',
      'MediumPurple',
      'DarkSlateGray',
      'DarkOrange',
      'LightGrey',
      'DarkSalmon',
      'Grey',
      'SaddleBrown',
      'DarkGray',
      'Red',
      'SlateGray',
      'Chocolate',
      'Turquoise',
      'PowderBlue',
      'Gold',
      'MidnightBlue',
      'SpringGreen',
      'Peru',
      'MediumAquaMarine',
      'Blue',
      'FireBrick',
      'BurlyWood',
      'Aquamarine',
      'MediumSpringGreen',
      'Fuchsia'
    ];
  }

  /**
   * event listner on user input.
   * First remove marker if set by previous search
   * then find the location properties.
   * then add marker to the map
   */
  _addGeocoderInputEventListener(): void {
    this.geocoder.on('result', (event) => {
      this._removeMarkerFromGeocoder();
      const location = this._getLocationFromGeocoder(event);
      this.chLocationSelectedEvent.emit(location);
      this._addMarkerFromGeocoder(location);
    });

    this.geocoder.on('clear', () => {
      this._removeMarkerFromGeocoder();
      this.chLocationSelectedEvent.emit(null);
    });
  }

  /**
   * @return value is a location with properties lng, lat, country, city, street
   * @param event is an event fired from geocoder
   * This function is extracting properties from an event.
   * how placeNameEnGB looks like: "Фрушкогорска 20, Novi Sad 21203, South Bačka, Serbia"
   */
  _getLocationFromGeocoder(event: any): Location {
    const result = event.result;
    console.log(result);
    const placeNameEnGB = result.place_name;
    let [street, city, region, country] = placeNameEnGB.split(', ');
    if (!country) {
      country = region;
    }

    const location: Location = {
      longitude: result.center[0].toString(),
      latitude: result.center[1].toString(),
      country,
      city,
      street,
    };
    return location;
  }

  _addMarkerFromGeocoder(location: Location): void {
    const coordinates: [number, number] = [parseFloat(location.longitude), parseFloat(location.latitude)];
    const color = 'red';
    const fontSize = '60px';

    const markerIcon: HTMLDivElement = document.createElement('div');
    markerIcon.id = 'geocoder_marker';
    markerIcon.innerHTML = `
    <button style="outline: none; border:none; background-color: rgba(0, 0, 0, 0); cursor: pointer;">
      <i class="material-icons"
        style="color: ${color}; font-size: ${fontSize}">
        place
      </i>
    </button>`;
    const marker = new Marker(markerIcon).setLngLat(coordinates).addTo(this.map);
  }
  _removeMarkerFromGeocoder(): void {
    try {
      const marker = document.getElementById('geocoder_marker');
      marker.remove();
    }
    catch (error) { }
  }
}
