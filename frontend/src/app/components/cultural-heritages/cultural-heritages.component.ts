import { Component, OnInit, ViewChild } from '@angular/core';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
  {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
  {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
  {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
  {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
  {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
  {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
  {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
  {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
  {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
];

@Component({
  selector: 'app-cultural-heritages',
  templateUrl: './cultural-heritages.component.html',
  styleUrls: ['./cultural-heritages.component.css']
})
export class CulturalHeritagesComponent implements OnInit { // AfterViewInit 

  page = 1;
  error: string;

  displayedColumns: string[] = ['name', 'subtypeName', 'totalRatings'];
  dataSource = [];

  constructor(
    private service: CulturalHeritageService
  ) { }

  ngOnInit(): void {
    this.getCulturalHeritages(this.page);
  }

  getCulturalHeritages(page: number): void {
    this.service.getCulturalHeritages(page - 1).subscribe(
      data => {
        console.log(data.content)
        this.dataSource = data.content;
      },
      error => {
        console.log(error);
        this.error = 'Can\'t load cultural heritages for some reason';
      }
    );
  }

}
