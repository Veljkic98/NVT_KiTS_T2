package tim2.CulturalHeritage.helper.LocationMappers;

import tim2.CulturalHeritage.dto.responseDTO.LocationResponseDTO;
import tim2.CulturalHeritage.helper.MapperInterface;
import tim2.CulturalHeritage.model.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationResponseMapper implements MapperInterface<Location, LocationResponseDTO> {

    @Override
    public Location toEntity(LocationResponseDTO dto) {
            Location location = new Location();
            location.setCity(dto.getCity());
            location.setCountry(dto.getCountry());
            location.setLatitude(dto.getLatitude());
            location.setLongitude(dto.getLongitude());
            location.setStreet(dto.getStreet());
            location.setId(dto.getId());

            return location;
            }

    @Override
    public LocationResponseDTO toDto(Location entity) {

            return new LocationResponseDTO(entity.getId(), entity.getLatitude(), entity.getLongitude(), entity.getCountry(), entity.getCity(), entity.getStreet());
            }

    @Override
    public List<LocationResponseDTO> toDtoList(List<Location> entityList) {
            List<LocationResponseDTO> locationsDTOlist = new ArrayList<>();
            for (Location u : entityList) {
                locationsDTOlist.add(toDto(u));
            }

            return locationsDTOlist;
    }
}
