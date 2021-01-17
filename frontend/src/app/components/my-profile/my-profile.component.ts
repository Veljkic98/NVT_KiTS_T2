
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationStart, Router } from '@angular/router';
import { User } from '../../models/user.model';
import { AuthService } from '../../services/auth-service/auth.service';
import { CulturalHeritage } from 'src/app/models/cultural-heritage.model';

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
    private router: Router,
    private route: ActivatedRoute

  ) {}

  ngOnInit(): void {



    this.route.paramMap.subscribe( params =>
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

}
