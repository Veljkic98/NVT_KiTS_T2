import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { NotificationsComponent } from 'src/app/components/notifications/notifications.component';
import { HomePageComponent } from './home-page.component';
import { SharedModule } from 'src/app/components/shared/shared.module';
import { MapsComponent } from 'src/app/components/maps/maps.component';
import { MyProfileComponent } from 'src/app/components/my-profile/my-profile.component';
import { RegisterComponent } from '../../components/register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';


@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        SharedModule,
        FormsModule,
        ReactiveFormsModule,
        MatCardModule, 
        MatInputModule,
        MatButtonModule,
        MatProgressSpinnerModule
    ],
    declarations: [
        HomePageComponent,
        NotificationsComponent,
        MapsComponent,
        MyProfileComponent,
        RegisterComponent
    ]
})

export class HomePageModule { }
