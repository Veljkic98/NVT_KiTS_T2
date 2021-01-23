import { Component, OnInit } from '@angular/core';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  //should be set to null
  //for testing set to 1 (id of chosen CH)
  selectedCH: number = null;
  filterValue:string;
  filterType:string;
  culturalHeritages: CulturalHeritage[];
  page = 1;
  totalPages: number;
  totalElements: number;
  error: string;
  total: number;
  content: string;
  url: string;
  lastPage: boolean;



  constructor(
    private chService: CulturalHeritageService,
    private _snackBar: MatSnackBar,      
  ) { }

  ngOnInit(): void {  
    this.filterValue = '';
    this.getCulturalHeritages(this.page);
  }

  closeDetails(): void {
    this.selectedCH = null;
  }
  setChId($event){
    this.selectedCH = $event;
  }

  openSnackBar(message: string): void{
    this._snackBar.open(message, 'Dismiss', {
      duration: 4000,
    });
}

  getCulturalHeritages(page: number): void {
    if(this.filterValue !== ''){
      this.filterData(page);      
    }else{
      this.chService.getCulturalHeritages(page-1)
      .subscribe(
        data => {
            this.lastPage = data.last;
            this.culturalHeritages = data.content;
            this.total = data.totalElements;
            this.page = data.number + 1;
            this.error = null
        },
        error => {
          console.log(error);
          this.error = 'Can\'t load cultural heritages at the moment :(';
        });
    }
  }

  filterData(page: number):void {
    this.chService.filterCulturalHeritages({type:this.filterType, value:this.filterValue}, page-1).subscribe(
      data => {
          this.lastPage = data.last;
          this.culturalHeritages = data.content;
          this.total = data.totalElements;
          this.page = data.number + 1;
          this.error = null;

          const append = this.total == 1 ? '' : 's';
          this.openSnackBar(`Found ${this.total} result${append}.`);

      },
      error => console.log(error),
    );

  }


  
  resetData():void {
    this.filterValue = '';
    this.filterType = '';
    this.chService.getCulturalHeritages(0).subscribe(
      data => {
          this.lastPage = data.last;
          this.culturalHeritages = data.content;
          this.total = data.totalElements;
          this.page = data.number + 1;
          this.error = null;
      },
      error => console.log(error),
    );

  }
}
