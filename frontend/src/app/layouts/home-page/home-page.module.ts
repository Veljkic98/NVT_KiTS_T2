import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { NotificationsComponent } from 'src/app/components/notifications/notifications.component';
import { HomePageComponent } from './home-page.component';
import { SharedModule } from 'src/app/components/shared/shared.module';
import { MyProfileComponent } from 'src/app/components/my-profile/my-profile.component';
import { RegisterComponent } from '../../components/register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { VerificationPageComponent } from '../../components/verification-page/verification-page.component';
import { DashboardComponent  } from '../../components/dashboard/dashboard.component';
import { LoginComponent } from 'src/app/components/login/login.component';
import { RatingComponent } from '../../components/rating/rating.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { CommentsComponent } from '../../components/comments/comments.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { CHTypesComponent, EditTypeDialogComponent, SubtypeDeleteDialogComponent } from 'src/app/components/ch-types/ch-types.component';
import { NewsComponent } from 'src/app/components/news/news.component';

import { NgxMapboxGLModule } from 'ngx-mapbox-gl';
import { environment } from 'src/environments/environment';
import { UpdateChComponent } from 'src/app/components/update-ch/update-ch.component';

import { AddNewTypeComponent } from 'src/app/components/add-new-type/add-new-type.component';
import { ChNewsComponent } from 'src/app/components/ch-news/ch-news.component';
import { UpdateNewsComponent } from 'src/app/components/update-news/update-news.component';
import { AddNewsComponent } from 'src/app/components/add-news/add-news.component';
import { AddSubtypeComponent } from 'src/app/components/add-subtype/add-subtype.component';
import { MaterialModule } from 'src/app/components/shared/material.module';
import { CulturalHeritageModule } from 'src/app/components/features/cultural-heritage/cultural-heritage.module';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        RouterModule,
        SharedModule,
        FormsModule,
        ReactiveFormsModule,
        NgxPaginationModule,
     
        NgbModule,
        CulturalHeritageModule
    ],
    declarations: [
        HomePageComponent,
        NotificationsComponent,
        MyProfileComponent,
        CHTypesComponent,
        SubtypeDeleteDialogComponent,
        EditTypeDialogComponent,
        RegisterComponent,
        DashboardComponent,
        VerificationPageComponent,
        LoginComponent,
        CommentsComponent,
        RatingComponent,
        NewsComponent,
        UpdateChComponent,
        AddNewTypeComponent,
        ChNewsComponent,
        UpdateNewsComponent,
        AddNewsComponent,
        AddSubtypeComponent,
    ]
})

export class HomePageModule { }
