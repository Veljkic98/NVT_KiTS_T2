package tim2.CulturalHeritage.helper.LocationMappers;

import tim2.CulturalHeritage.dto.requestDTO.LocationRequestDTO;
import tim2.CulturalHeritage.helper.MapperInterface;
import tim2.CulturalHeritage.model.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationRequestMapper implements MapperInterface<Location, LocationRequestDTO> {

    @Override
    public Location toEntity(LocationRequestDTO dto) {
        Location location = new Location();
        location.setCity(dto.getCity());
        location.setCountry(dto.getCountry());
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        location.setStreet(dto.getStreet());

        return location;
    }

    @Override
    public LocationRequestDTO toDto(Location entity) {

        return new LocationRequestDTO(entity.getLatitude(), entity.getLongitude(), entity.getCountry(), entity.getCity(), entity.getStreet());
    }

    @Override
    public List<LocationRequestDTO> toDtoList(List<Location> entityList) {
        List<LocationRequestDTO> locationsDTOlist = new ArrayList<>();
        for (Location u : entityList) {
            locationsDTOlist.add(toDto(u));
        }

        return locationsDTOlist;
    }
}