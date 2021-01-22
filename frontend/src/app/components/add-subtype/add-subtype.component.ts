import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CHSubtype2, CHSubtype3 } from 'src/app/models/ch-subtype.model';
import { CHSubtypeService } from 'src/app/services/ch-subtype-service/ch-subtype.service';

@Component({
  selector: 'app-add-subtype',
  templateUrl: './add-subtype.component.html',
  styleUrls: ['./add-subtype.component.css']
})
export class AddSubtypeComponent implements OnInit {

  subtype: CHSubtype3 = new CHSubtype3();

  nameValid: boolean = true;

  takenNames: string = "";

  subtypes: Array<CHSubtype2> = [];

  constructor(
    private route: ActivatedRoute,
    private subtypeService: CHSubtypeService
  ) { }

  ngOnInit(): void {
    this.subtype.name = "";
    this.loadTypeId();
    this.loadSubtypes();
  }

  loadTypeId() {
    this.route.paramMap.subscribe(params => {
      this.subtype.chTypeID = +params.get('typeid');
    });
  }

  loadSubtypes() {
    this.subtypeService.getAll()
      .subscribe(data => {
        this.subtypes = data;
      });
  }

  isNameValid() {
    this.nameValid = true;

    this.takenNames = "";

    this.subtypes.forEach(element => {
      this.takenNames += element.name + " - ";
      if (element.name.toUpperCase() == this.subtype.name.toUpperCase()) {
        this.nameValid = false;
      }
    });
    try {
      this.takenNames = this.takenNames.slice(0, -2);
    } catch (error) { }
  }

  add() {
    this.isNameValid();

    if (this.nameValid) {
      this.subtypeService.add(this.subtype)
        .subscribe(
          response => {
            console.log("added")
            this.subtypes.push(response);
          }
        )
    }
  }

}
