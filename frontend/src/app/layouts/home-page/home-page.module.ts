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
import { VerificationPageComponent } from '../../components/verification-page/verification-page.component';
import { CulturalHeritageComponent  } from '../../components/cultural-heritage/cultural-heritage.component';
import { DashboardComponent  } from '../../components/dashboard/dashboard.component';
import {MatExpansionModule} from '@angular/material/expansion';
import { LoginComponent } from 'src/app/components/login/login.component';
import { RatingComponent } from '../../components/rating/rating.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { CommentsComponent } from '../../components/comments/comments.component';
import { NgxPaginationModule } from 'ngx-pagination';
import {MatTabsModule} from '@angular/material/tabs';
import { CHTypesComponent, EditTypeDialog, SubtypeDeleteDialog } from 'src/app/components/ch-types/ch-types.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { CulturalHeritagesComponent } from 'src/app/components/cultural-heritages/cultural-heritages.component';
import { NewsComponent } from 'src/app/components/news/news.component';

import { NgxMapboxGLModule } from 'ngx-mapbox-gl';
import { environment } from 'src/environments/environment';
import { MatFormFieldModule } from '@angular/material/form-field';
import { AddNewCulturalHeritageComponent } from 'src/app/components/add-new-cultural-heritage/add-new-cultural-heritage.component';
import { MatSelectModule } from '@angular/material/select';
import { UpdateChComponent } from 'src/app/components/update-ch/update-ch.component';


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
        MatFormFieldModule,
        MatProgressSpinnerModule,
        MatExpansionModule,
        NgxPaginationModule,
        MatTabsModule,
        NgxMapboxGLModule.withConfig({
          accessToken: environment.mapboxApiKey
        }),
        MatTableModule,
        MatPaginatorModule,
        MatDialogModule,
        MatSnackBarModule,
        NgbModule,
        MatSelectModule,
    ],
    declarations: [
        HomePageComponent,
        NotificationsComponent,
        MapsComponent,
        MyProfileComponent,
        CHTypesComponent,
        SubtypeDeleteDialog,
        EditTypeDialog,
        RegisterComponent,
        DashboardComponent,
        VerificationPageComponent,
        CulturalHeritageComponent,
        LoginComponent,
        CommentsComponent,
        RatingComponent,
        CulturalHeritagesComponent,
        AddNewCulturalHeritageComponent,
        NewsComponent,
        UpdateChComponent
    ]
})

export class HomePageModule { }
