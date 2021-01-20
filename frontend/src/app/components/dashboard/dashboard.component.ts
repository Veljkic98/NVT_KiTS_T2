import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  //should be set to null
  //for testing set to 1 (id of chosen CH)
  selectedCH: number = null;

  isSubscribed: Boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  closeDetails(): void {
    this.selectedCH = null;
  }
  setChId($event){
    this.selectedCH = $event;
    this.checkIfSubscribed()
  }

  checkIfSubscribed() {
    //TODO: Proveriti da li je trenutno ulogovani korisnik prijavljen
  }
}
