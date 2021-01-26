export class CHSubtype {
    public id: number;
    public name: string;
    public parentId: number;

    constructor(typeCfg: ChSubtypeInterface) {
        this.id = typeCfg.id;
        this.name = typeCfg.name;
        this.parentId = typeCfg.parentId;
    }
}

interface ChSubtypeInterface{
	id?: number;
    name: string;
    parentId?: number;
}

export class CHSubtype2 {
    public id: number;
    public name: string;
    public chTypeId: number;
}

export class CHSubtype3 {
    public name: string;
    public chTypeID: number;
}


