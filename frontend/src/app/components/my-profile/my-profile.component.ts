
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationStart, Router } from '@angular/router';
import { User } from '../../models/user.model';
import { AuthService } from '../../services/auth-service/auth.service';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';
import { CulturalHeritageService } from 'src/app/services/cultural-heritage-service/cultural-heritage.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {
  error: string = null;
  user: User;
  tabIndex: number;
  subscriptions: Array<CulturalHeritage>;

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private chService: CulturalHeritageService,
    private _snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {

    this.route.paramMap.subscribe(params =>
      this.tabIndex = +params.get('index')
    );

    if (this.authService.getRole() === 'ROLE_USER') {
      this.authService.getSubscriptions()
        .subscribe(
          data => { this.subscriptions = data; },
          error => {
            console.log(error);
            this.error = 'Couldn\'t fetch subscriptions now :(';
          });
    }
    this.authService.getProfile()
      .subscribe(
        data => {
          this.user = data;
        },
        error => {
          console.log(error);
          this.error = 'Somethnig went wrong, can\'t load all comments right now.';
        });
  }

  unsub(chid) {
    this.chService.unsubscribe(chid)
      .subscribe(
        response => {
          if (response.statusText == 'OK') {
            this.subscriptions = this.subscriptions.filter(({ id }) => id !== chid);
            this.openSnackBar('Successfuly unsubscribed!');
          } else {
            this.openSnackBar('Unsuccessfuly unsubscribed!');
          }
        }, error => { this.openSnackBar('Unsuccessfuly unsubscribed!'); }
      );
  }

  openSnackBar(message: string): void {
    this._snackBar.open(message, 'Dismiss', {
      duration: 4000,
    });
  }

}
