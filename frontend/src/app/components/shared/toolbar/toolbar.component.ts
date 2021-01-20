import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth-service/auth.service';


@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {
  value = '';

  constructor(
    public authService: AuthService,
    public router: Router
  ) {}

  ngOnInit(): void {
  }

  logout(): void {
    this.authService.logOut();
    this.router.navigate(['/login']);
  }

}
