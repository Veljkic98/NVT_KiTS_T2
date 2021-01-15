import { Component, OnInit } from '@angular/core';
import { CHType } from 'src/app/models/ch-type.model';
import { CHTypeService } from 'src/app/services/ch-type-service/ch-type.service';

@Component({
    selector: 'app-ch-type',
    templateUrl: './ch-types.component.html',
    styleUrls: ['./ch-types.component.css']
})
export class CHTypesComponent implements OnInit {
    private chTypes: Array<CHType>;

    constructor(
        service: CHTypeService
    ){}

    ngOnInit(): void {

    }
}
