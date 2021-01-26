export class CHSubtype {
    public id: number;
    public name: string;
    public chTypeID: number;

    constructor(typeCfg: ChSubtypeInterface) {
        this.id = typeCfg.id;
        this.name = typeCfg.name;
        this.chTypeID = typeCfg.chTypeID;
    }
}

interface ChSubtypeInterface{
	id?: number;
    name: string;
    chTypeID?: number;
}
