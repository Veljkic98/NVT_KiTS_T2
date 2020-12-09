package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.requestDTO.CulturalHeritageRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CulturalHeritageResponseDTO;
import tim2.CulturalHeritage.helper.LocationMappers.LocationResponseMapper;
import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.model.CulturalHeritage;

import java.util.ArrayList;
import java.util.List;

public class CulturalHeritageMapper implements MapperInterfaceEnhanced<CulturalHeritage, CulturalHeritageResponseDTO, CulturalHeritageRequestDTO> {

    private CHSubtypeMapper subtypeMapper = new CHSubtypeMapper();
    private LocationResponseMapper locationMapper = new LocationResponseMapper();

    public CulturalHeritageMapper(){
        CHSubtypeMapper subTypeMapper = new CHSubtypeMapper();
        LocationResponseMapper locationMapper = new LocationResponseMapper();
    }

    @Override
    public CulturalHeritage toEntity(CulturalHeritageRequestDTO dto) {
        CulturalHeritage ch = new CulturalHeritage();
        ch.setName(dto.getName());
        ch.setDescription(dto.getDescription());
        //ch.setLocation(locationMapper.toEntity(dto.getLocation()));

        return ch;
    }

    public CulturalHeritage toEntity(CulturalHeritageResponseDTO dto) {
        CulturalHeritage ch = new CulturalHeritage();
        ch.setId(dto.getId());
        ch.setName(dto.getName());
        ch.setDescription(dto.getDescription());
        CHSubtype sub = subtypeMapper.toEntity(dto.getChsubtype());
        ch.setChsubtype(sub);
        //ch.setLocation(locationMapper.toEntity(dto.getLocation()));

        return ch;
    }


    @Override
    public CulturalHeritageResponseDTO toDto(CulturalHeritage entity) {
        // location i subtype dtos su isti kao entiteti?
        return new CulturalHeritageResponseDTO(entity.getId(), entity.getName(), entity.getDescription(), subtypeMapper.toDto(entity.getChsubtype()));
    }

    @Override
    public List<CulturalHeritageResponseDTO> toDtoList(List<CulturalHeritage> entityList) {
        List<CulturalHeritageResponseDTO> results = new ArrayList<>();

        for(CulturalHeritage ch: entityList ){
            results.add(new CulturalHeritageResponseDTO(ch.getId(), ch.getName(), ch.getDescription(), subtypeMapper.toDto(ch.getChsubtype())));
        }

        return results;
    }
}
