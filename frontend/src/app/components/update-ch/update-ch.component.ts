
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CHSubtype2 } from 'src/app/models/ch-subtype.model';
import { CulturalHeritageToAdd } from 'src/app/models/cultural-heritage-to-add.model';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';
import { Location } from 'src/app/models/location.model';
import { CHSubtypeService } from 'src/app/services/ch-subtype-service/ch-subtype.service';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';
import { LocationService } from 'src/app/services/location/location.service';
@Component({
  selector: 'app-update-ch',
  templateUrl: './update-ch.component.html',
  styleUrls: ['./update-ch.component.css']
})
export class UpdateChComponent implements OnInit {

  url: string;

  name: string = "";
  description: string = "";

  isLocationChosen: boolean = false;
  isSubtypeChosen: boolean = false;
  isFileChosen: boolean = false;

  location: Location;

  subtypes: Array<CHSubtype2> = [];

  subtype: CHSubtype2;  // selected value - subtype

  chid: number;

  culturalHeritage: CulturalHeritage;


  constructor(
    private chService: CulturalHeritageService,
    private locationService: LocationService,
    private subtypeService: CHSubtypeService,
    private _router: Router,
    private _route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    //get CH based on it's ID
    this._route.params.subscribe((params: Params) => {
      this.chid = params.chid;
      this.chService.getOne(this.chid)
        .subscribe(response => {
          this.culturalHeritage = response;

          //set this.lcation
          this.location = {
            latitude : this.culturalHeritage.coordinates[1],
            longitude : this.culturalHeritage.coordinates[0],
            country : undefined,
            city : undefined,
            street : undefined
          };
          

          //get all subtypes
          this.subtypeService.getAll().subscribe(
            data => {
              this.subtypes = data;
              //set this.subtype
              this.subtype = this.subtypes.find(subtype =>
                subtype.id == this.culturalHeritage.chsubtypeID);
            }
          );
        })
    });


  }

  /**
   * 
   */
  addCH(): void {
    // First add location
    this.locationService.post(this.location)
      .subscribe(
        location => {
          // then add cultural heritage
          this.chService.post(this.name, this.description, location.id, this.subtype.id, this.url)
            .subscribe(
              data => {
                // console.log(data);
                this._router.navigate(['/cultural-heritages']);
              },
              error => {
                console.log(error);
              }
            );
        },
        error => {
          console.log(error);
        }
      );
  }

  /**
   * Take url of choosen image.
   * 
   * @param event 
   */
  onSelectFile(event): void {
    if (event.target.files && event.target.files[0]) {
      this.url = event.target.files[0];
      // console.log(this.url)
      this.isFileChosen = true;
    }
  }

  /**
   * 
   * @param location location is passed from map component 
   * after geocoder search 
   */
  setLocation(location: Location) {
    this.location = location;
    if (location) {
      this.isLocationChosen = true;
      this.location = location;
    }
    else {
      this.isLocationChosen = false;
      this.location = null
    }

    // console.log(this.location);
  }
}
