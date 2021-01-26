import { Component, OnInit } from '@angular/core';
import { CHTypeToAdd } from 'src/app/models/ch-type.model';
import { CHTypeService } from 'src/app/services/ch-type-service/ch-type.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import {  Router } from '@angular/router';

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
    private typeService: CHTypeService,
    private _router: Router,
    private _snackBar: MatSnackBar,

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
    return this.nameValid;
  }

  addType() {
    this.isNameValid();

    this.type = new CHTypeToAdd(this.name);

    if (this.nameValid) {
      this.typeService.addType(this.type)
        .subscribe(
          response => {
            this.chTypes.push(response);
            this._router.navigate(['/manage/types']);
            this.openSnackBar(`Successfuly added ${this.name} type.`);
          },
          () => {this.openSnackBar(`Problem occured while adding ${this.name} type.`); }
        );
    }
  }

  openSnackBar(message: string): void {
    this._snackBar.open(message, 'Dismiss', {
      duration: 4000,
    });
  }
}
