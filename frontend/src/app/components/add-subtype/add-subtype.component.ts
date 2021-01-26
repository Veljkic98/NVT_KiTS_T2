import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { CHSubtype2, CHSubtype3 } from 'src/app/models/ch-subtype.model';
import { CHSubtypeService } from 'src/app/services/ch-subtype-service/ch-subtype.service';

@Component({
  selector: 'app-add-subtype',
  templateUrl: './add-subtype.component.html',
  styleUrls: ['./add-subtype.component.css']
})
export class AddSubtypeComponent implements OnInit {

  subtype: CHSubtype3 = new CHSubtype3();

  nameValid = true;

  takenNames = '';

  subtypes: Array<CHSubtype2> = [];

  constructor(
    private route: ActivatedRoute,
    private _router: Router,
    private subtypeService: CHSubtypeService,
    private _snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {
    this.subtype.name = '';
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

    this.takenNames = '';

    this.subtypes.forEach(element => {
      this.takenNames += element.name + ' - ';
      if (element.name.toUpperCase() == this.subtype.name.toUpperCase()) {
        this.nameValid = false;
      }
    });
    try {
      this.takenNames = this.takenNames.slice(0, -2);
      return this.nameValid;
    } catch (error) { return this.nameValid; }
  }

  add() {

    if (this.nameValid) {
      this.subtypeService.add(this.subtype)
        .subscribe(
          response => {
            this._router.navigate(['/manage/types']);
            this.openSnackBar('Successfuly added the subtype!');
            this.subtypes.push(response);
          }
        );
    }
  }

  openSnackBar(message: string): void {
    this._snackBar.open(message, 'Dismiss', {
      duration: 4000,
    });
  }

}
