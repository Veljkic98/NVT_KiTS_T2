import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from './material.module';
import { MapsComponent } from '../maps/maps.component';
import { NgxMapboxGLModule } from 'ngx-mapbox-gl';
import { environment } from 'src/environments/environment';

@NgModule({
  declarations: [
    ToolbarComponent, MapsComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    MaterialModule,
    NgxMapboxGLModule.withConfig({
      accessToken: environment.mapboxApiKey
    }),
  ],
  exports: [
    ToolbarComponent, CommonModule, FormsModule, MapsComponent, RouterModule 
  ]
})
export class SharedModule { }
