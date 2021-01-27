import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { CHSubtype } from 'src/app/models/ch-subtype.model';
import { CHSubtypeService } from 'src/app/services/ch-subtype-service/ch-subtype.service';

@Component({
  selector: 'app-add-subtype',
  templateUrl: './add-subtype.component.html',
  styleUrls: ['./add-subtype.component.css']
})
export class AddSubtypeComponent implements OnInit {

  subtype: CHSubtype;

  nameValid = true;

  takenNames = '';

  subtypes: Array<CHSubtype> = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private subtypeService: CHSubtypeService,
    private snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {
    this.subtype = new CHSubtype({name: ''});
    this.loadTypeId();
    this.loadSubtypes();
  }

  loadTypeId(): void {
    this.route.paramMap.subscribe(params => {
      this.subtype.chTypeID = +params.get('typeid');
    });
  }

  loadSubtypes(): void {
    this.subtypeService.getAll()
      .subscribe(data => {
        this.subtypes = data;
      });
  }

  isNameValid(): boolean {
    this.nameValid = true;

    this.takenNames = '';

    this.subtypes.forEach(element => {
      this.takenNames += element.name + ' - ';
      if (element.name.toUpperCase() === this.subtype.name.toUpperCase()) {
        this.nameValid = false;
      }
    });
    try {
      this.takenNames = this.takenNames.slice(0, -2);
      return this.nameValid;
    } catch (error) { return this.nameValid; }
  }

  add(): void {
    console.log('ovdee');
    if (this.nameValid) {
      this.subtypeService.add(this.subtype)
        .subscribe(
          response => {
            this.router.navigate(['/manage/types']);
            this.openSnackBar('Successfuly added the subtype!');
            this.subtypes.push(response);
          }
        );
    }
  }

  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Dismiss', {
      duration: 4000,
    });
  }

}
