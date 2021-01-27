import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HomePageComponent } from './home-page.component';
import { SharedModule } from 'src/app/components/shared/shared.module';
import { DashboardComponent  } from '../../components/dashboard/dashboard.component';
import { MaterialModule } from 'src/app/components/shared/material.module';
import { CulturalHeritageModule } from 'src/app/components/features/cultural-heritage/cultural-heritage.module';
import { UserModule } from 'src/app/components/features/user/user.module';
import { NewsModule } from 'src/app/components/features/news/news.module';
import { ChTypesModule } from 'src/app/components/features/cultural-heritage-types/ch-types.module';

@NgModule({
    imports: [
        MaterialModule,
        CommonModule,
        RouterModule,
        SharedModule,
        CulturalHeritageModule,
        UserModule,
        NewsModule,
        ChTypesModule
    ],
    declarations: [
        HomePageComponent,
        DashboardComponent,
    ]
})

export class HomePageModule { }
