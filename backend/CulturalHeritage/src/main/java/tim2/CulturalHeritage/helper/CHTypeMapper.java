package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.CHTypeDTO;
import tim2.CulturalHeritage.model.CHType;

import java.util.List;

public class CHTypeMapper implements MapperInterface<CHType, CHTypeDTO> {


    @Override
    public CHType toEntity(CHTypeDTO dto) {
        return null;
    }

    @Override
    public CHTypeDTO toDto(CHType entity) {
        return new CHTypeDTO(entity.getId(), entity.getName(), entity.getSubtypes());
    }

    @Override
    public List<CHTypeDTO> toDtoList(List<CHType> entityList) {
        return null;
    }
}
