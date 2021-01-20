import { Component, OnInit } from '@angular/core';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';
import { AuthService } from 'src/app/services/auth-service/auth.service';

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
  ) { }

  ngOnInit(): void {  
    this.getCulturalHeritages(0);
  }

  closeDetails(): void {
    this.selectedCH = null;
  }
  setChId($event){
    this.selectedCH = $event;
  }

  

  getCulturalHeritages(page): void {
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

  filterData():void {
    this.chService.filterCulturalHeritages({type:this.filterType, value:this.filterValue}).subscribe(
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


  
  resetData():void {
    this.filterValue = '';
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
