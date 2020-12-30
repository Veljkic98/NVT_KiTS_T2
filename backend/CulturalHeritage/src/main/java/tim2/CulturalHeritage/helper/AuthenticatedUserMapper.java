package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.requestDTO.AuthUserRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserResponseDTO;
import tim2.CulturalHeritage.model.AuthenticatedUser;
import tim2.CulturalHeritage.model.Person;

import java.util.ArrayList;
import java.util.List;

public class AuthenticatedUserMapper implements MapperInterface<AuthenticatedUser, AuthUserResponseDTO, AuthUserRequestDTO> {

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
    public AuthUserResponseDTO toDto(AuthenticatedUser entity) {
        return new AuthUserResponseDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.isApproved());
    }
    
    public AuthUserResponseDTO toDto(Person entity) {
        return new AuthUserResponseDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail());
    }

    @Override
    public List<AuthUserResponseDTO> toDtoList(List<AuthenticatedUser> entityList) {
        List<AuthUserResponseDTO> usersDTOList = new ArrayList<>();
        for(AuthenticatedUser u: entityList){
            usersDTOList.add(toDto(u));
        }

        return usersDTOList;
    }
}
