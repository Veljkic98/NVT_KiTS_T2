import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../services/auth-service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  error: string;
  success = false;

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private authService: AuthService
  ) {
      if (this.authService.isLoggedIn()) {
          this.router.navigate(['/']);
      }
  }

  ngOnInit() {
      this.registerForm = this.formBuilder.group({
          firstName: ['', Validators.required],
          lastName: ['', Validators.required],
          email: ['', [Validators.email, Validators.required]],
          password: ['', [Validators.required, Validators.minLength(8)]],
          confirmPass: ['', Validators.required]
      });
  }


  get f() { return this.registerForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.registerForm.invalid) {
        return;
    }

    this.loading = true;
    this.success = false;
    this.error = '';
    this.authService.register(this.registerForm.value)
        .subscribe(
            data => {
                this.loading = false;
                this.success = true;
            },
            error => {
                this.error = error.error.messages[0];
                ;
                this.loading = false; 
            });
}
}