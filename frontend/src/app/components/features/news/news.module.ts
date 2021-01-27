import { NgModule } from '@angular/core';
import { MaterialModule } from '../../shared/material.module';
import { SharedModule } from '../../shared/shared.module';
import { AddNewsComponent } from './add-news/add-news.component';
import { ChNewsComponent } from './ch-news/ch-news.component';
import { NewsComponent } from './news/news.component';
import { UpdateNewsComponent } from './update-news/update-news.component';

@NgModule({
    imports: [SharedModule, MaterialModule],
    declarations: [NewsComponent, ChNewsComponent, AddNewsComponent, UpdateNewsComponent],
    exports: [NewsComponent, ChNewsComponent, AddNewsComponent, UpdateNewsComponent]
})

export class NewsModule {}
