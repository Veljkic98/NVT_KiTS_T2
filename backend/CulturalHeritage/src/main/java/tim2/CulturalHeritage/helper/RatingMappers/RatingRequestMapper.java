package tim2.CulturalHeritage.helper.RatingMappers;


import tim2.CulturalHeritage.dto.requestDTO.RatingRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CulturalHeritageResponseDTO;
import tim2.CulturalHeritage.helper.CulturalHeritageMapper;
import tim2.CulturalHeritage.helper.MapperInterface;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.model.Rating;

import java.util.ArrayList;
import java.util.List;

public class RatingRequestMapper  implements MapperInterface<Rating, RatingRequestDTO> {

    private CulturalHeritageMapper chMapper = new CulturalHeritageMapper();

    @Override
    public Rating toEntity(RatingRequestDTO dto) {
        Rating rating = new Rating();
        rating.setGrade(dto.getGrade());
        CulturalHeritage ch = chMapper.toEntity(dto.getCulturalHeritage());
        rating.setCulturalHeritage(ch);

        return rating;
    }

    @Override
    public RatingRequestDTO toDto(Rating entity) {
        CulturalHeritageResponseDTO chDTO = chMapper.toDto(entity.getCulturalHeritage());

        return new RatingRequestDTO(entity.getGrade(),chDTO);
    }

    @Override
    public List<RatingRequestDTO> toDtoList(List<Rating> entityList) {
        List<RatingRequestDTO> ratingDTOList = new ArrayList<>();
        for (Rating u : entityList) {
            ratingDTOList.add(toDto(u));
        }

        return ratingDTOList;
    }
}
