export class CulturalHeritage {
  public id: number;
  public avgRating: number;
  public chsubtypeID: number;
  public description: string;
  public imageUri: string;
  public locationID: number;
  public name: string;
  public coordinates: [string, string];
  public totalRatings: number;
  public locationName: string;

  constructor(chCfg: CulturalHeritageInterface) {
    this.name = chCfg.name;
    this.id = chCfg.id;
    this.chsubtypeID = chCfg.chsubtypeID;
    this.description = chCfg.description;
    this.avgRating = chCfg.avgRating;
    this.imageUri = chCfg.imageUri;
    this.locationID = chCfg.locationID;
    this.coordinates = chCfg.coordinates;
    this.totalRatings = chCfg.totalRatings;
    this.locationName = chCfg.locationName;
  }
}

interface CulturalHeritageInterface{
	  id?: number;
    avgRating: number;
    chsubtypeID: number;
    description: string;
    imageUri: string;
    locationID: number;
    name: string;
    coordinates: [string, string];
    totalRatings: number;
    locationName: string;
}
