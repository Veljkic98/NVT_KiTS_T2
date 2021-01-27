import { NgModule } from '@angular/core';
import { MaterialModule } from '../../shared/material.module';
import { SharedModule } from '../../shared/shared.module';
import { AddNewCulturalHeritageComponent } from './add-new-cultural-heritage/add-new-cultural-heritage.component';
import { CulturalHeritageComponent } from './cultural-heritage/cultural-heritage.component';
import { CulturalHeritagesComponent } from './cultural-heritages/cultural-heritages.component';

@NgModule({
    imports: [SharedModule, MaterialModule],
    exports: [],
    declarations: [
        CulturalHeritagesComponent, 
        CulturalHeritageComponent,
        AddNewCulturalHeritageComponent
    ]
})
export class CulturalHeritageModule { }