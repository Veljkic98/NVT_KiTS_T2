package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.requestDTO.RatingRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.RatingResponseDTO;
import tim2.CulturalHeritage.model.Rating;

import java.util.ArrayList;
import java.util.List;

public class RatingMapper implements MapperInterface<Rating, RatingResponseDTO, RatingRequestDTO> {
    @Override
    public Rating toEntity(RatingRequestDTO dto) {
        Rating rating = new Rating();
        rating.setGrade(dto.getGrade());

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
