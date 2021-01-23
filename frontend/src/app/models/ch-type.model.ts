import { CHSubtype } from './ch-subtype.model';

export class CHType {
    public id: number;
    public name: string;
    public subtypes: Array<CHSubtype>;

    constructor(typeCfg: ChTYpeInterface) {
        this.id = typeCfg.id;
        this.name = typeCfg.name;
        this.subtypes = typeCfg.subtypes;
    }
}

interface ChTYpeInterface{
	id?: number;
    name: string;
    subtypes?: Array<CHSubtype>;
}

export class CHTypeToAdd {
    public name: string;

    constructor(name: string) {
        this.name = name;
    }
}
