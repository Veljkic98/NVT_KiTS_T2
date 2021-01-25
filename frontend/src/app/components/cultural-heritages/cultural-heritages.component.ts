import { Component, OnInit } from '@angular/core';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';
import { PageEvent } from '@angular/material/paginator';
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { MatSnackBar } from "@angular/material/snack-bar";
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  { position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H' },
  { position: 2, name: 'Helium', weight: 4.0026, symbol: 'He' },
  { position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li' },
  { position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be' },
  { position: 5, name: 'Boron', weight: 10.811, symbol: 'B' },
  { position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C' },
  { position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N' },
  { position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O' },
  { position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F' },
  { position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne' },
];

@Component({
  selector: 'app-cultural-heritages',
  templateUrl: './cultural-heritages.component.html',
  styleUrls: ['./cultural-heritages.component.css']
})
export class CulturalHeritagesComponent implements OnInit { // AfterViewInit 

  totalPages = 0;
  length = 0;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25];
  showFirstLastButtons = true;
  
  error: string;

  displayedColumns: string[] = ['name', 'subtypeName', 'totalRatings', 'actions'];
  dataSource = [];

  pageEvent: PageEvent;

  selectedCH: CulturalHeritage;

  constructor(
    private service: CulturalHeritageService,
    private modalService: NgbModal,
    private _snackBar: MatSnackBar,   
  ) { }

  ngOnInit(): void {
    this.getCulturalHeritages(this.pageIndex, this.pageSize);
  }

  getCulturalHeritages(page: number, size: number): void {
    this.service.getCulturalHeritagesWithSize(page, size).subscribe(
      data => {
        // console.log(data.content)
        this.dataSource = data.content;
        this.length = data.totalElements;
        this.totalPages = data.totalPages;
      },
      error => {
        console.log(error);
        this.error = 'Can\'t load cultural heritages for some reason';
      }
    );
  }

  handlePageEvent(event: PageEvent) {
    this.length = event.length;
    this.pageSize = event.pageSize;
    this.pageIndex = event.pageIndex;

    this.getCulturalHeritages(this.pageIndex, this.pageSize);
  }

  openDeleteModal(deleteModal, ch:CulturalHeritage){
    this.selectedCH = ch;
    const activeModal = this.modalService.open(deleteModal, {ariaLabelledBy: 'modal-basic-title'});

    activeModal.result.then( ()=> {
      this.deleteCH(ch.id);
    }, () => {})
  }

  async deleteCH(id:number){
    this.service.delete(id).subscribe(
      data =>  {
        this.openSnackBar(`Successfuly deleted ${this.selectedCH.name}.`)
        this.getCulturalHeritages(this.pageIndex -1, this.pageSize);
      },
      error => {
        if(error.status == 409)
          this.openSnackBar(`Can\'t delete ${this.selectedCH.name} because there are subscribed users.`)
        else
          this.openSnackBar(`Can\'t delete ${this.selectedCH.name}.`)
      }
    );
  }

  openSnackBar(message: string): void{
    this._snackBar.open(message, 'Dismiss', {
      duration: 4000,
    });
  }
}
