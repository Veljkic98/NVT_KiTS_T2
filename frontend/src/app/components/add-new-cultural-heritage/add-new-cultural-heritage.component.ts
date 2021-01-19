import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CHSubtype2 } from 'src/app/models/ch-subtype.model';
import { CulturalHeritageToAdd } from 'src/app/models/cultural-heritage-to-add.model';
import { Location } from 'src/app/models/location.module';
import { CHSubtypeService } from 'src/app/services/ch-subtype-service/ch-subtype.service';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';
import { LocationService } from 'src/app/services/location/location.service';

@Component({
  selector: 'app-add-new-cultural-heritage',
  templateUrl: './add-new-cultural-heritage.component.html',
  styleUrls: ['./add-new-cultural-heritage.component.css']
})
export class AddNewCulturalHeritageComponent implements OnInit {

  url: string;

  name: string = "";
  description: string = "";

  areCoordinatesChoosen: Boolean = false;
  isSubtypeChoosen: Boolean = false;

  coordinates: [number, number];

  location: Location;

  subtypes: Array<CHSubtype2> = [];

  subtype: CHSubtype2;  // selected value - subtype

  constructor(
    private chService: CulturalHeritageService,
    private locationService: LocationService,
    private subtypeService: CHSubtypeService
  ) { }

  ngOnInit(): void {
    this.subtypeService.getAll()
      .subscribe(
        data => {
          this.subtypes = data;
          console.log(this.subtypes);
        },
        error => {
          console.log(error);
        }
      );
  }

  /**
   * 
   */
  addCH(): void {
    this.location = this.initializeLocation();

    // First add location
    this.locationService.post(this.location)
      .subscribe(
        location => {
          console.log(location);
          console.log(location.id);

          // then add cultural heritage
          this.chService.post(this.name, this.description, location.id, this.subtype.id, this.url)
            .subscribe(
              data => {
                console.log(data);
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

  initializeLocation() {
    return new Location(this.coordinates[0].toString(), this.coordinates[1].toString(), "country", "city", "street");
  }

  /**
   * Take url of choosen image.
   * 
   * @param event 
   */
  onSelectFile(event): void {
    if (event.target.files && event.target.files[0]) {
      this.url = event.target.files[0];
      console.log(this.url)
    }
  }

  /**
   * Get coordinates from maps
   * 
   * @param crds first elem is longitude and second is latitude
   */
  onCoordinates(crds: [number, number]) {
    this.coordinates = crds;
    this.areCoordinatesChoosen = true;
    console.log(this.coordinates);
  }

}
