package tim2.CulturalHeritage.helper.RatingMappers;

import tim2.CulturalHeritage.dto.requestDTO.RatingRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.RatingResponseDTO;
import tim2.CulturalHeritage.helper.MapperInterface;
import tim2.CulturalHeritage.model.Rating;

import java.util.ArrayList;
import java.util.List;

public class RatingResponseMapper implements MapperInterface<Rating, RatingResponseDTO> {

    @Override
    public Rating toEntity(RatingResponseDTO dto) {
        Rating rating = new Rating();

        return rating;
    }

    @Override
    public RatingResponseDTO toDto(Rating entity) {

        return new RatingResponseDTO(entity.getId(), entity.getGrade(), entity.getCulturalHeritage().getId(), entity.getAuthenticatedUser().getId());
    }

    @Override
    public List<RatingResponseDTO> toDtoList(List<Rating> entityList) {
        List<RatingResponseDTO> ratingDTOList = new ArrayList<>();
        for (Rating u : entityList) {
            ratingDTOList.add(toDto(u));
        }

        return ratingDTOList;
    }
}

