import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MapsComponent } from './components/maps/maps.component';
import { MyProfileComponent } from './components/my-profile/my-profile.component';
import { NotificationsComponent } from './components/notifications/notifications.component';
import { HomePageComponent } from './layouts/home-page/home-page.component';

const routes: Routes = [
  {
    path: '',
    component: HomePageComponent,
    children: [
      { path: 'notifications', component: NotificationsComponent },
      { path: 'myProfile', component: MyProfileComponent },
      { path: 'maps', component: MapsComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
