
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth-service/auth.service';

@Component({
  selector: 'app-verification-page',
  templateUrl: './verification-page.component.html',
  styleUrls: ['./verification-page.component.css']
})
export class VerificationPageComponent implements OnInit {
  loading = false;
  error: string; 
  success : boolean;
  id: string;

  constructor(
      private router: Router,
      private authService: AuthService,
      private route: ActivatedRoute,
  ) {
      if (this.authService.isLoggedIn()) {
          this.router.navigate(['/']);
      }
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    this.authService.verify(this.id)
    .subscribe(
        data => {
            this.loading = false;
            this.success = true;
        },
        error => {
            this.error = "Something went wrong please try again."
            this.loading = false; 
        });
}
}