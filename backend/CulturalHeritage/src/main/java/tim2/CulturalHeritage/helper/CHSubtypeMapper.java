package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.CHSubtypeDTO;
import tim2.CulturalHeritage.model.CHSubtype;

import java.util.List;

public class CHSubtypeMapper implements MapperInterface<CHSubtype, CHSubtypeDTO>{


    @Override
    public CHSubtype toEntity(CHSubtypeDTO dto) {
        return null;
    }

    @Override
    public CHSubtypeDTO toDto(CHSubtype entity) {

        return new CHSubtypeDTO(entity.getId(),entity.getName());
    }

    @Override
    public List<CHSubtypeDTO> toDtoList(List<CHSubtype> entityList) {
        return null;
    }
}
