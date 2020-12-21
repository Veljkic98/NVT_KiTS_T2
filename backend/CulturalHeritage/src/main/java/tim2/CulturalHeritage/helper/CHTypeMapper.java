package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.responseDTO.CHSubtypeResponseDTO;
import tim2.CulturalHeritage.dto.requestDTO.CHTypeRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CHTypeResponseDTO;
import tim2.CulturalHeritage.model.CHType;

import java.util.ArrayList;
import java.util.List;

public class CHTypeMapper implements MapperInterface<CHType, CHTypeResponseDTO, CHTypeRequestDTO> {

    private CHSubtypeMapper subtypeMapper = new CHSubtypeMapper();

    @Override
    public CHType toEntity(CHTypeRequestDTO dto) {
        CHType type = new CHType();
        type.setName(dto.getName());

        return type;
    }

    public CHType toEntity(CHTypeResponseDTO dto) {
        CHType type = new CHType();
        type.setId(dto.getId());
        type.setName(dto.getName());

        return type;
    }


    @Override
    public CHTypeResponseDTO toDto(CHType entity) {

        List<CHSubtypeResponseDTO> subs = subtypeMapper.toDtoList(entity.getSubtypes());
        return new CHTypeResponseDTO(entity.getId(), entity.getName(), subs);
    }

    @Override
    public List<CHTypeResponseDTO> toDtoList(List<CHType> entityList) {

        List<CHTypeResponseDTO> results = new ArrayList<>();

        for(CHType ch: entityList ){
            List<CHSubtypeResponseDTO> subs = subtypeMapper.toDtoList(ch.getSubtypes());
            results.add(new CHTypeResponseDTO(ch.getId(), ch.getName(), subs));
        }

        return results;
    }
}
