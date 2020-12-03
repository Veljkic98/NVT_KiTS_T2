package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.CHTypeDTO;
import tim2.CulturalHeritage.dto.responseDTO.CulturalHeritageResponseDTO;
import tim2.CulturalHeritage.model.CHType;
import tim2.CulturalHeritage.model.CulturalHeritage;

import java.util.ArrayList;
import java.util.List;

public class CHTypeMapper implements MapperInterface<CHType, CHTypeDTO> {


    @Override
    public CHType toEntity(CHTypeDTO dto) {
        return null;
    }

    @Override
    public CHTypeDTO toDto(CHType entity) {
        return new CHTypeDTO(entity.getId(), entity.getName());
    }

    @Override
    public List<CHTypeDTO> toDtoList(List<CHType> entityList) {

        List<CHTypeDTO> results = new ArrayList<>();

        for(CHType ch: entityList ){
            results.add(new CHTypeDTO(ch.getId(), ch.getName()));
        }

        return results;
    }
}
