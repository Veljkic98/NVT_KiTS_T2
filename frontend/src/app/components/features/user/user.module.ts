import { NgModule } from '@angular/core';
import { MaterialModule } from '../../shared/material.module';
import { SharedModule } from '../../shared/shared.module';
import { LoginComponent } from './login/login.component';
import { MyProfileComponent } from './my-profile/my-profile.component';
import { RegisterComponent } from './register/register.component';
import { VerificationPageComponent } from './verification-page/verification-page.component';


@NgModule({
    imports: [SharedModule, MaterialModule],
    declarations: [
        LoginComponent,
        MyProfileComponent,
        RegisterComponent,
        VerificationPageComponent
    ],
    exports: [
        LoginComponent,
        MyProfileComponent,
        RegisterComponent,
        VerificationPageComponent
    ]
})

export class UserModule {}
