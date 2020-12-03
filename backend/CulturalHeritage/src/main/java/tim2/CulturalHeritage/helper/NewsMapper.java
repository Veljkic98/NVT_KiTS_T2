package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.requestDTO.NewsRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.CHSubtypeResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.NewsResponseDTO;
import tim2.CulturalHeritage.model.CHSubtype;
import tim2.CulturalHeritage.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsMapper implements MapperInterfaceEnhanced<News, NewsResponseDTO, NewsRequestDTO >{
    @Override
    public News toEntity(NewsRequestDTO dto) {
        News news = new News();
        // zavisi da li cemo pri kreiranju slati samo ID admina i CHa ili mozda cijeli DTO
        news.setContent(dto.getContent());
        news.setHeading(dto.getHeading());

        return news;
    }

    public News toEntity(NewsResponseDTO dto) {
        News news = new News();
        news.setId(dto.getId());
        // zavisi da li cemo pri kreiranju slati samo ID admina i CHa ili mozda cijeli DTO
        news.setContent(dto.getContent());
        news.setHeading(dto.getHeading());

        return news;
    }

    @Override
    public NewsResponseDTO toDto(News entity) {
        return new NewsResponseDTO(entity.getId(), entity.getHeading(), entity.getContent(),
                entity.getCulturalHeritage().getId(), entity.getAdmin().getId());
    }

    @Override
    public List<NewsResponseDTO> toDtoList(List<News> entityList) {
        List<NewsResponseDTO> results = new ArrayList<>();

        if(entityList == null){
            return results;
        }

        for(News news: entityList ){
            results.add(toDto(news));
        }

        return results;
    }
}
