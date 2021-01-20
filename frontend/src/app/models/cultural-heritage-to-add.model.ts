export class CulturalHeritageToAdd {
    public name: string;
    public description: string;
    public locationID: number;
    public chsubtypeID: number;

    constructor(name: string, chsubtypeID: number, description: string,
        locationID: number) {
        this.name = name;
        this.chsubtypeID = chsubtypeID;
        this.description = description;
        this.locationID = locationID;
    }
}