import { CHSubtype } from './ch-subtype.model';

export class CHType {
    public id: number;
    public name: string;
    public subtypes: Array<CHSubtype>;
}

export class CHTypeToAdd {
    public name: string;

    constructor(name: string) {
        this.name = name;
    }
}
