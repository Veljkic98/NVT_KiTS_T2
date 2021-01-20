package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.requestDTO.CulturalHeritageRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CulturalHeritageResponseDTO;
import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.model.Location;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tim2.CulturalHeritage.model.Rating;

public class CulturalHeritageMapper
        implements MapperInterface<CulturalHeritage, CulturalHeritageResponseDTO, CulturalHeritageRequestDTO> {

    private CHSubtypeMapper subtypeMapper = new CHSubtypeMapper();
    private LocationMapper locationMapper = new LocationMapper();

    public CulturalHeritageMapper() {
        CHSubtypeMapper subTypeMapper = new CHSubtypeMapper();
        LocationMapper locationMapper = new LocationMapper();
    }

    @Override
    public CulturalHeritage toEntity(CulturalHeritageRequestDTO dto) {

        CulturalHeritage ch = new CulturalHeritage();

        ch.setName(dto.getName());
        ch.setDescription(dto.getDescription());

        Location location = new Location();
        location.setId(dto.getLocationID());
        ch.setLocation(location);

        CHSubtype chSubtype = new CHSubtype();
        chSubtype.setId(dto.getChsubtypeID());
        ch.setChsubtype(chSubtype);

        return ch;
    }

    @Override
    public CulturalHeritageResponseDTO toDto(CulturalHeritage entity) {

        String imageUri;

        try {
            imageUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/files/")
                    .path(entity.getImages().getId() + "").toUriString();
        } catch (NullPointerException e) {
            imageUri = null;
        }

        float avgRating;

        int ratingListSize = 0;
        try{
            avgRating = calcRating(entity.getRatings());
            ratingListSize = entity.getRatings().size();
        }catch (Exception e){

            avgRating = 0;
        }

        String locationName = entity.getLocation().getCountry() + " " + entity.getLocation().getCity();

        int ratingsSize = 0;

        try {
            ratingsSize = entity.getRatings().size();
        } catch (Exception e) {
            ratingsSize = 0;
        }

        // coordinates
        List<String> coordinates = new ArrayList<>();
        coordinates.add(entity.getLocation().getLongitude());
        coordinates.add(entity.getLocation().getLatitude());

        return new CulturalHeritageResponseDTO(entity.getId(), entity.getName(), entity.getDescription(),
                entity.getLocation().getId(), entity.getChsubtype().getId(), imageUri, avgRating, locationName, entity.getChsubtype().getName(), coordinates, ratingListSize);

    }

    @Override
    public List<CulturalHeritageResponseDTO> toDtoList(List<CulturalHeritage> entityList) {

        List<CulturalHeritageResponseDTO> results = new ArrayList<>();

        for (CulturalHeritage ch : entityList) {
            results.add(toDto(ch));
        }

        return results;
    }

    public float calcRating(List<Rating> list) {
        if (null == list)
            return 0;
        int size = list.size();
        if (size == 0) {
            return 0;
        } else {
            float sum = 0;
            for (Rating rate : list) {
                sum += rate.getGrade();
            }
            return sum / size;
        }
    }
}
