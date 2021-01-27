import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MapsComponent } from './components/shared/maps/maps.component';
import { HomePageComponent } from './layouts/home-page/home-page.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginGuard } from './guards/login.guard';
import { RoleGuard } from './guards/role.guard';
import { CulturalHeritageModule } from './components/features/cultural-heritage/cultural-heritage.module';
import { CulturalHeritagesComponent } from './components/features/cultural-heritage/cultural-heritages/cultural-heritages.component';
import { AddNewCulturalHeritageComponent } from './components/features/cultural-heritage/add-new-cultural-heritage/add-new-cultural-heritage.component';
import { SharedModule } from './components/shared/shared.module';
import { CHTypesComponent } from './components/features/cultural-heritage-types/ch-types/ch-types.component';
import { AddNewTypeComponent } from './components/features/cultural-heritage-types/add-new-type/add-new-type.component';
import { UpdateChComponent } from './components/features/cultural-heritage/update-ch/update-ch.component';
import { AddNewsComponent } from './components/features/news/add-news/add-news.component';
import { NewsComponent } from './components/features/news/news/news.component';
import { MyProfileComponent } from './components/features/user/my-profile/my-profile.component';
import { RegisterComponent } from './components/features/user/register/register.component';
import { LoginComponent } from './components/features/user/login/login.component';
import { VerificationPageComponent } from './components/features/user/verification-page/verification-page.component';
import { AddSubtypeComponent } from './components/features/cultural-heritage-types/add-subtype/add-subtype.component';

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
        component: UpdateChComponent,
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
