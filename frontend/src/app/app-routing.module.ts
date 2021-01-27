import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MapsComponent } from './components/maps/maps.component';
import { MyProfileComponent } from './components/my-profile/my-profile.component';
import { HomePageComponent } from './layouts/home-page/home-page.component';
import { RegisterComponent } from './components/register/register.component';
import { VerificationPageComponent } from './components/verification-page/verification-page.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginComponent } from './components/login/login.component';
import { LoginGuard } from './guards/login.guard';
import { RoleGuard } from './guards/role.guard';
import { CHTypesComponent } from './components/ch-types/ch-types.component';
import { NewsComponent } from './components/news/news.component';
import { UpdateChComponent } from './components/update-ch/update-ch.component';
import { AddNewTypeComponent } from './components/add-new-type/add-new-type.component';
import { UpdateNewsComponent } from './components/update-news/update-news.component';
import { AddNewsComponent } from './components/add-news/add-news.component';
import { AddSubtypeComponent } from './components/add-subtype/add-subtype.component';
import { CulturalHeritageModule } from './components/features/cultural-heritage/cultural-heritage.module';
import { CulturalHeritagesComponent } from './components/features/cultural-heritage/cultural-heritages/cultural-heritages.component';
import { AddNewCulturalHeritageComponent } from './components/features/cultural-heritage/add-new-cultural-heritage/add-new-cultural-heritage.component';
import { SharedModule } from './components/shared/shared.module';

const routes: Routes = [
  {
    path: '',
    component: HomePageComponent,
    children: [
      {
        path: '',
        component: DashboardComponent
      },
      {
        path: 'manage/types',
        component: CHTypesComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: 'ROLE_ADMIN' }
      },
      {
        path: 'new-type',
        component: AddNewTypeComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: 'ROLE_ADMIN' }
      },
      {
        path: 'update-ch/:chid',
        component: UpdateChComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: 'ROLE_ADMIN' }
      },
      {
        path: 'new-ch',
        component: AddNewCulturalHeritageComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: 'ROLE_ADMIN' }
      },
      {
        path: 'add/news/:chid',
        component: AddNewsComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: 'ROLE_ADMIN' }
      },
      {
        path: 'add/subtype/:typeid',
        component: AddSubtypeComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: 'ROLE_ADMIN' }
      },
      {
        path: 'update/news/:index',
        component: UpdateNewsComponent,
        canActivate: [RoleGuard],
        data: {expectedRoles: 'ROLE_ADMIN'}
      },
      {
        path: 'cultural-heritages',
        component: CulturalHeritagesComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: 'ROLE_ADMIN' },
      },
      {
        path: 'manage/news/:index',
        component: NewsComponent,
        canActivate: [RoleGuard],
        data: {expectedRoles: 'ROLE_ADMIN'}
      },
      {
        path: 'me/:index',
        component: MyProfileComponent,
        canActivate: [RoleGuard],
        data: { expectedRoles: 'ROLE_ADMIN|ROLE_USER' }
      },
      {
        path: 'maps',
        component: MapsComponent,
      },
      {
        path: 'register',
        component: RegisterComponent,
        canActivate: [LoginGuard]
      },
      {
        path: 'login',
        component: LoginComponent,
        canActivate: [LoginGuard]
      },
      {
        path: 'verify/:id',
        component: VerificationPageComponent,
        canActivate: [LoginGuard]
      },
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    CulturalHeritageModule,
    SharedModule
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
