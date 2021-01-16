import { animate, state, style, transition, trigger } from '@angular/animations';
import { ChangeDetectorRef, Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { CHSubtype } from 'src/app/models/ch-subtype.model';
import { CHType } from 'src/app/models/ch-type.model';
import { CHTypeService } from 'src/app/services/ch-type-service/ch-type.service';

@Component({
    selector: 'app-ch-type',
    templateUrl: './ch-types.component.html',
    styleUrls: ['./ch-types.component.css'],
    animations: [
        trigger('detailExpand', [
          state('collapsed', style({height: '0px', minHeight: '0'})),
          state('expanded', style({height: '*'})),
          transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
        ]),
      ],
})
export class CHTypesComponent implements OnInit {
    chTypes: CHType[];
    page = 1;
    totalPages: number;
    totalElements: number;
    error: string;
    total: number;
    content: string;
    url: string;
    lastPage: boolean;
    dataSource;

    columnsToDisplay: string[] = ['Name', 'View Subtypes', 'Edit', 'Delete' ];
    innerDisplayedColumns = ['Name', 'Edit', 'Delete' ];

    expandedElement: CHType | null;

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChildren('innerTables') innerTables: QueryList<MatTable<CHSubtype>>;


    constructor(
        public service: CHTypeService,
        private cd: ChangeDetectorRef

    ){}

    ngOnInit(): void {
        this.getTypes(this.page);
    }


    toggleRow(element: CHType) {
        element.subtypes ? (this.expandedElement = this.expandedElement === element ? null : element) : null;
        this.cd.detectChanges();
      }

    getTypes(page: number): void{
        this.service.getTypes(page - 1).subscribe(
            data => {
                this.chTypes = data.content;
                this.lastPage = data.last;
                this.total = data.totalElements;
                this.page = data.number + 1;
                this.error = null;
                this.dataSource = new MatTableDataSource<CHType>(data.content);
                this.dataSource.paginator = this.paginator;

            },
            error => {
                console.log(error);
                this.error = 'Can\'t load types at the moment :(';
            }
        );
    }
}
