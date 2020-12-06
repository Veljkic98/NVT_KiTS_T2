import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../services/auth-service/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  error: string;

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
    console.log(this.registerForm)
    if (this.registerForm.invalid) {
        return;
    }

    this.loading = true;
    this.authService.register(this.registerForm.value)
        .subscribe(
            data => {
                this.router.navigate(['/login'], { queryParams: { registered: true }});
            },
            error => {
                this.error = error;
                this.loading = false;
            });
}
}