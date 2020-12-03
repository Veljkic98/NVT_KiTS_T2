package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.requestDTO.CHSubtypeRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CHSubtypeResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.CHTypeResponseDTO;
import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.model.CHType;

import java.util.ArrayList;
import java.util.List;

public class CHSubtypeMapper implements MapperInterface<CHSubtype, CHSubtypeResponseDTO>{



    @Override
    public CHSubtype toEntity(CHSubtypeResponseDTO dto) {

        CHSubtype subtype = new CHSubtype();
        subtype.setId(dto.getId());
        subtype.setName(dto.getName());



        return subtype;
    }


    public CHSubtype toEntity(CHSubtypeRequestDTO dto) {

        CHSubtype subtype = new CHSubtype();
        subtype.setName(dto.getName());

        //CHTypeMapper typeMapper = new CHTypeMapper();
        //subtype.setChtype(typeMapper.toEntity(dto.getType()));

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
            results.add(toDto(subtype));
        }

        return results;
    }
}
