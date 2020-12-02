package tim2.CulturalHeritage.helper.AuthenticatedUserMappers;

import tim2.CulturalHeritage.dto.requestDTO.AuthUserRequestDTO;
import tim2.CulturalHeritage.helper.MapperInterface;
import tim2.CulturalHeritage.model.AuthenticatedUser;

import java.util.ArrayList;
import java.util.List;

public class AuthUserRequestMapper implements MapperInterface<AuthenticatedUser, AuthUserRequestDTO> {

    @Override
    public AuthenticatedUser toEntity(AuthUserRequestDTO dto) {
        AuthenticatedUser user = new AuthenticatedUser();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }

    @Override
    public AuthUserRequestDTO toDto(AuthenticatedUser entity) {
        return new AuthUserRequestDTO(entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPassword());
    }

    @Override
    public List<AuthUserRequestDTO> toDtoList(List<AuthenticatedUser> entityList) {
        List<AuthUserRequestDTO> usersDTOList = new ArrayList<>();
        for (AuthenticatedUser u : entityList) {
            usersDTOList.add(toDto(u));
        }

        return usersDTOList;
    }
}
