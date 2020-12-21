package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.requestDTO.CHSubtypeRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CHSubtypeResponseDTO;
import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.model.CHType;

import java.util.ArrayList;
import java.util.List;

public class CHSubtypeMapper implements MapperInterface<CHSubtype, CHSubtypeResponseDTO, CHSubtypeRequestDTO>{

    public CHSubtype toEntity(CHSubtypeRequestDTO dto) {

        CHSubtype subtype = new CHSubtype();
        subtype.setName(dto.getName());
        CHType type = new CHType();
        type.setId(dto.getChTypeID());
        subtype.setChtype(type);

        return subtype;
    }


    @Override
    public CHSubtypeResponseDTO toDto(CHSubtype entity) {

        return new CHSubtypeResponseDTO(entity.getId(),entity.getName(), entity.getChtype().getId());
    }

    @Override
    public List<CHSubtypeResponseDTO> toDtoList(List<CHSubtype> entityList) {
        List<CHSubtypeResponseDTO> results = new ArrayList<>();

        if(entityList == null){
            return results;
        }

        for(CHSubtype entity: entityList ){
            results.add(toDto(entity));
        }

        return results;
    }
}
