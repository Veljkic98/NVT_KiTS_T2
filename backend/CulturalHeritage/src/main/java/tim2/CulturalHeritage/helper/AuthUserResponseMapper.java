package tim2.CulturalHeritage.helper;


import tim2.CulturalHeritage.dto.responseDTO.AdminResponseDTO;
import tim2.CulturalHeritage.dto.responseDTO.AuthUserResponseDTO;
import tim2.CulturalHeritage.model.Admin;
import tim2.CulturalHeritage.model.AuthenticatedUser;

import java.util.ArrayList;
import java.util.List;

public class AuthUserResponseMapper implements MapperInterface<AuthenticatedUser, AuthUserResponseDTO> {

    @Override
    public AuthenticatedUser toEntity(AuthUserResponseDTO dto) {
        AuthenticatedUser user = new AuthenticatedUser();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setApproved(dto.isApproved());

        return user;
    }

    @Override
    public AuthUserResponseDTO toDto(AuthenticatedUser entity) {
        return new AuthUserResponseDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.isApproved());
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
