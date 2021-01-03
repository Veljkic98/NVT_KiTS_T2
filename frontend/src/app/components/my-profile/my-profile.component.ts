import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user.model';

import { AuthService } from '../../services/auth-service/auth.service';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {
  error: string = null;
  user: User;
  
  constructor(
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.authService.getProfile()
    .subscribe(
      data => {
         this.user = data;
      },
      error => {
         console.log(error);
         this.error = "Somethnig went wrong, can't load all comments right now.";
      });
  }

}
