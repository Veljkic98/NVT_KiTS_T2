
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth-service/auth.service';


@Component({
  selector: 'app-verification-page',
  templateUrl: './verification-page.component.html',
  styleUrls: ['./verification-page.component.css']
})
export class VerificationPageComponent implements OnInit {
  loading = false;
  error: string;
  success = false;
  id: string;

  constructor(
      private authService: AuthService,
      private route: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.authService.verify(this.id)
    .subscribe(
        data => {
            this.loading = false;
            this.success = true;
        },
        error => {
            this.error = 'Something went wrong please try again.';
            this.loading = false;
        });
    }
}
