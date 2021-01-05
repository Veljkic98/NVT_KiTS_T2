import { Component, OnInit } from '@angular/core';

import { AuthService } from '../../../services/auth-service/auth.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {  
  value = '';

  constructor(
    public authService: AuthService
  ) { }

  ngOnInit(): void {
  }

}
