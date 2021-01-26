export class Location{
  public latitude: string;
  public longitude: string;
  public country: string;
  public city: string;
  public street: string;
  public id?: number;

  constructor(latitude: string, longitude: string, country: string, city: string, street: string) {
      this.latitude = latitude;
      this.longitude = longitude;
      this.country = country;
      this.city = city;
      this.street = street;
  }
}
