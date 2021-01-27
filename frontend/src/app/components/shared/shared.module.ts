import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './material.module';
import { MapsComponent } from './maps/maps.component';
import { NgxMapboxGLModule } from 'ngx-mapbox-gl';
import { environment } from 'src/environments/environment';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  declarations: [
    ToolbarComponent, MapsComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    MaterialModule,
    NgbModule,
    NgxPaginationModule,
    NgxMapboxGLModule.withConfig({
      accessToken: environment.mapboxApiKey
    }),
  ],
  exports: [
    ToolbarComponent,
    CommonModule,
    FormsModule,
    MapsComponent,
    RouterModule,
    ReactiveFormsModule,
    NgxPaginationModule,
  ]
})
export class SharedModule { }
