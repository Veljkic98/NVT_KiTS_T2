import { NgModule } from '@angular/core';
import { MaterialModule } from '../../shared/material.module';
import { SharedModule } from '../../shared/shared.module';
import { AddNewTypeComponent } from './add-new-type/add-new-type.component';
import { AddSubtypeComponent } from './add-subtype/add-subtype.component';
import { CHTypesComponent, EditTypeDialogComponent, SubtypeDeleteDialogComponent } from './ch-types/ch-types.component';

@NgModule({
    imports: [SharedModule, MaterialModule],
    declarations: [
        CHTypesComponent,
        AddNewTypeComponent,
        AddSubtypeComponent,
        SubtypeDeleteDialogComponent,
        EditTypeDialogComponent
    ],
    exports: [
        CHTypesComponent,
        AddNewTypeComponent,
        AddSubtypeComponent,
        SubtypeDeleteDialogComponent,
        EditTypeDialogComponent
    ],

})
export class ChTypesModule { }
