package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.responseDTO.CHSubtypeResponseDTO;
import tim2.CulturalHeritage.model.CHSubtype;

import java.util.List;

public class CHSubtypeMapper implements MapperInterface<CHSubtype, CHSubtypeResponseDTO>{


    @Override
    public CHSubtype toEntity(CHSubtypeResponseDTO dto) {

        CHSubtype subtype = new CHSubtype();
        subtype.setId(dto.getId());
        subtype.setName(dto.getName());

        return subtype;
    }

    @Override
    public CHSubtypeResponseDTO toDto(CHSubtype entity) {

        return new CHSubtypeResponseDTO(entity.getId(),entity.getName());
    }

    @Override
    public List<CHSubtypeResponseDTO> toDtoList(List<CHSubtype> entityList) {
        return null;
    }
}
