package tim2.CulturalHeritage.dto.requestDTO;

import javax.validation.constraints.NotBlank;

public class LocationRequestDTO {

    @NotBlank(message="Latitude can not be blank")
    private String latitude;

    @NotBlank(message="Longitude can not be blank")
    private String longitude;

    @NotBlank(message="Country can not be blank")
    private String country;

    @NotBlank(message="City can not be blank")
    private String city;

    private String street;

    public LocationRequestDTO(String latitude, String longitude, String country, String city, String street) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.street = street;
    }

    public LocationRequestDTO() {}

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
