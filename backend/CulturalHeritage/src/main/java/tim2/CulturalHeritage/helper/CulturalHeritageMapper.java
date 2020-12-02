package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.CulturalHeritageDTO;
import tim2.CulturalHeritage.model.CulturalHeritage;

import java.util.ArrayList;
import java.util.List;

public class CulturalHeritageMapper implements MapperInterface<CulturalHeritage, CulturalHeritageDTO> {



    @Override
    public CulturalHeritage toEntity(CulturalHeritageDTO dto) {
        return null;
    }

    @Override
    public CulturalHeritageDTO toDto(CulturalHeritage entity) {
        // location i subtype dtos su isti kao entiteti?
        CHSubtypeMapper subTypeMapper = new CHSubtypeMapper();
        return new CulturalHeritageDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getLocation(), subTypeMapper.toDto(entity.getChsubtype()));
    }

    @Override
    public List<CulturalHeritageDTO> toDtoList(List<CulturalHeritage> entityList) {
        List<CulturalHeritageDTO> results = new ArrayList<>();
        CHSubtypeMapper subTypeMapper = new CHSubtypeMapper();

        for(CulturalHeritage ch: entityList ){
            results.add(new CulturalHeritageDTO(ch.getId(), ch.getName(), ch.getDescription(), ch.getLocation(), subTypeMapper.toDto(ch.getChsubtype())));
        }

        return null;
    }
}
