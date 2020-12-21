package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.requestDTO.NewsRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.NewsResponseDTO;
import tim2.CulturalHeritage.model.Admin;
import tim2.CulturalHeritage.model.CulturalHeritage;
import tim2.CulturalHeritage.model.News;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class NewsMapper implements MapperInterface<News, NewsResponseDTO, NewsRequestDTO> {

    @Override
    public News toEntity(NewsRequestDTO newsRequestDTO) {

        News news = new News();
        news.setContent(newsRequestDTO.getContent());
        news.setHeading(newsRequestDTO.getHeading());

        Admin user = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        news.setAdmin(user);

        CulturalHeritage ch = new CulturalHeritage();
        ch.setId(newsRequestDTO.getCulturalHeritageID());
        news.setCulturalHeritage(ch);
        
        return news;
    }

    @Override
    public NewsResponseDTO toDto(News entity) {

        String imageUri;
        
        try {
            imageUri = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("api/files/")
            .path(entity.getImages().getId() + "")
            .toUriString();
        } catch (NullPointerException e) {
            imageUri = null;
        }

        return new NewsResponseDTO(entity.getId(), entity.getHeading(), entity.getContent(),
                entity.getAdmin().getId(), entity.getCulturalHeritage().getId(), imageUri);
    }

    @Override
    public List<NewsResponseDTO> toDtoList(List<News> entityList) {
        List<NewsResponseDTO> results = new ArrayList<>();

        if (entityList == null) {
            return results;
        }

        for (News news : entityList) {
            results.add(toDto(news));
        }

        return results;
    }
}
