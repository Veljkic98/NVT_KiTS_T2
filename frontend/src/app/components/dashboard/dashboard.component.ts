import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  //should be set to null
  //for testing set to 1 (id of chosen CH)
  selectedCH: number = 1;
  constructor() { }

  ngOnInit(): void {
  }

  closeDetails() {
    this.selectedCH = null;
  }

  changeID() {
    this.selectedCH = this.selectedCH == 1? 2: 1;
  }

}
