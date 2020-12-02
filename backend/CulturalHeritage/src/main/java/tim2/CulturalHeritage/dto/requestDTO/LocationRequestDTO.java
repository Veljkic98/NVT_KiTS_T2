package tim2.CulturalHeritage.dto.requestDTO;

public class LocationRequestDTO {

    private String latitude;

    private String longitude;

    private String country;

    private String city;

    private String street;

    public LocationRequestDTO(String latitude, String longitude, String country, String city, String street) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.street = street;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
