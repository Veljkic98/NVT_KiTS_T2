import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MapsComponent } from './components/maps/maps.component';
import { MyProfileComponent } from './components/my-profile/my-profile.component';
import { NotificationsComponent } from './components/notifications/notifications.component';
import { HomePageComponent } from './layouts/home-page/home-page.component';
import { RegisterComponent } from './components/register/register.component';
import { VerificationPageComponent } from './components/verification-page/verification-page.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginComponent } from './components/login/login.component';

const routes: Routes = [
  {
    path: '',
    component: HomePageComponent,
    children: [
      { path: '', component: DashboardComponent },
      { path: 'notifications', component: NotificationsComponent },
      { path: 'myProfile', component: MyProfileComponent },
      { path: 'maps', component: MapsComponent },
      { path: 'register', component: RegisterComponent },
      { path:'login', component: LoginComponent },
      { path: 'verify/:id', component: VerificationPageComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
