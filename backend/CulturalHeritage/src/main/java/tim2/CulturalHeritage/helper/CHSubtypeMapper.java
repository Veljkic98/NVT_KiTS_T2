package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.CHSubtypeDTO;
import tim2.CulturalHeritage.model.CHSubtype;

import java.util.List;

public class CHSubtypeMapper implements MapperInterface<CHSubtype, CHSubtypeDTO>{


    @Override
    public CHSubtype toEntity(CHSubtypeDTO dto) {

        CHSubtype subtype = new CHSubtype();
        subtype.setId(dto.getId());
        subtype.setName(dto.getName());

        return subtype;
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
