package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.requestDTO.CHSubtypeRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CHSubtypeResponseDTO;
import tim2.CulturalHeritage.model.CHSubtype;

import java.util.ArrayList;
import java.util.List;

public class CHSubtypeMapper implements MapperInterface<CHSubtype, CHSubtypeResponseDTO, CHSubtypeRequestDTO>{

    public CHSubtype toEntity(CHSubtypeRequestDTO dto) {

        CHSubtype subtype = new CHSubtype();
        subtype.setName(dto.getName());

        return subtype;
    }


    @Override
    public CHSubtypeResponseDTO toDto(CHSubtype entity) {

        return new CHSubtypeResponseDTO(entity.getId(),entity.getName());
    }

    @Override
    public List<CHSubtypeResponseDTO> toDtoList(List<CHSubtype> entityList) {
        List<CHSubtypeResponseDTO> results = new ArrayList<>();

        if(entityList == null){
            return results;
        }

        for(CHSubtype subtype: entityList ){
            results.add(new CHSubtypeResponseDTO(subtype.getId(), subtype.getName()));
        }

        return results;
    }
}
