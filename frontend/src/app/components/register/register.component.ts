import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';

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
    error = '';
    success = false;

    constructor(
        private formBuilder: FormBuilder,
        private authService: AuthService
    ) { }

    ngOnInit(): void {
        this.registerForm = this.formBuilder.group({
            firstName: ['', Validators.required],
            lastName: ['', Validators.required],
            email: ['', [Validators.email, Validators.required]],
            password: ['', [Validators.required, Validators.minLength(8)]],
            confirmPass: ['', Validators.required]
        });
    }


    get f(): any { return this.registerForm.controls; }

    onSubmit(): void {
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
                    try {
                        this.error = error.error.messages[0];
                    } catch (e) {
                        this.error = 'Somethnig went wrong please try again';
                    }
                    this.loading = false;
                });
    }
}
