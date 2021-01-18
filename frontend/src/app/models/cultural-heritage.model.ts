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

  constructor(id: number, avgRating: number, chsubtypeID: number, description: string, 
    imageUri: string, locationID: number, name: string, coordinates: [string, string], totalRatings: number) {
    this.name = name;
    this.id = id;
    this.chsubtypeID = chsubtypeID;
    this.description = description;
    this.avgRating = avgRating;
    this.imageUri = imageUri;
    this.locationID = locationID;
    this.coordinates = coordinates;
    this.totalRatings = totalRatings;
  }
}