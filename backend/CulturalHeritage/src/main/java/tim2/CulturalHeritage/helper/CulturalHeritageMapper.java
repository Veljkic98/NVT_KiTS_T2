package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.requestDTO.CulturalHeritageRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CulturalHeritageResponseDTO;
import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.model.Location;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class CulturalHeritageMapper
        implements MapperInterfaceEnhanced<CulturalHeritage, CulturalHeritageResponseDTO, CulturalHeritageRequestDTO> {

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

    // public CulturalHeritage toEntity(CulturalHeritageResponseDTO dto) {
    //     CulturalHeritage ch = new CulturalHeritage();
    //     ch.setId(dto.getId());
    //     ch.setName(dto.getName());
    //     ch.setDescription(dto.getDescription());
    //     CHSubtype sub = subtypeMapper.toEntity(dto.getChsubtype());
    //     ch.setChsubtype(sub);
    //     // ch.setLocation(locationMapper.toEntity(dto.getLocation()));

    //     return ch;
    // }

    @Override
    public CulturalHeritageResponseDTO toDto(CulturalHeritage entity) {

        String imageUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/files/")
                .path(entity.getImages().getId() + "").toUriString();

        return new CulturalHeritageResponseDTO(entity.getId(), entity.getName(), entity.getDescription(),
                entity.getLocation().getId(), entity.getChsubtype().getId(), imageUri);
    }

    @Override
    public List<CulturalHeritageResponseDTO> toDtoList(List<CulturalHeritage> entityList) {
        List<CulturalHeritageResponseDTO> results = new ArrayList<>();

        for(CulturalHeritage ch: entityList ){
            String imageUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/news/images/")
                .path(ch.getImages().getId() + "").toUriString();
                
            results.add(new CulturalHeritageResponseDTO(ch.getId(), ch.getName(), ch.getDescription(), ch.getLocation().getId(), ch.getChsubtype().getId(), imageUri));
        }

        return results;
    }
}
