export class Page<Type> implements PageInterface<Type> {
    public content: Type[];
    public id: number;
    public empty: boolean;
    public number: number;
    public numberOfElements: number;
    public size: number;
    public totalElements: number;
    public totalPages: number;
    public last: boolean;

    constructor(pageCfg: PageInterface<Type>){
        this.content = pageCfg.content;
        this.id = pageCfg.id;
        this.empty = pageCfg.empty;
        this.number = pageCfg.number;
        this.numberOfElements = pageCfg.numberOfElements;
        this.size = pageCfg.size;
        this.totalElements = pageCfg.totalElements;
        this.totalPages = pageCfg.totalPages;
        this.last =  pageCfg.last;
    }
}

interface PageInterface<Type> {
    content: Type[];
    id?: number;
    empty: boolean;
    number: number;
    numberOfElements: number;
    size: number;
    totalElements: number;
    totalPages: number;
    last: boolean;
}
