import { Component, OnInit } from '@angular/core';
import { CHTypeToAdd } from 'src/app/models/ch-type.model';
import { CHTypeService } from 'src/app/services/ch-type-service/ch-type.service';

@Component({
  selector: 'app-add-new-type',
  templateUrl: './add-new-type.component.html',
  styleUrls: ['./add-new-type.component.css']
})
export class AddNewTypeComponent implements OnInit {

  name = '';
  page = 1;

  nameValid = true;

  type: CHTypeToAdd;

  takenNames = '';

  constructor(
    private typeService: CHTypeService
  ) { }

  chTypes: Array<any> = [];

  ngOnInit(): void {
    this.loadTypes();
  }

  loadTypes() {
    this.typeService.getTypes(this.page - 1)
      .subscribe(
        data => {
          this.chTypes = data.content;
        }
      );
  }

  isNameValid() {
    this.nameValid = true;

    this.takenNames = '';

    this.chTypes.forEach(element => {
      this.takenNames += element.name + ' - ';
      if (element.name.toUpperCase() == this.name.toUpperCase()) {
        this.nameValid = false;
      }

    });
    try {
      this.takenNames = this.takenNames.slice(0, -2);
    } catch (error) { }
  }

  addType() {
    this.isNameValid();

    this.type = new CHTypeToAdd(this.name);

    if (this.nameValid) {
      this.typeService.addType(this.type)
        .subscribe(
          response => {
            this.chTypes.push(response);
          }
        );
    }
  }
}
