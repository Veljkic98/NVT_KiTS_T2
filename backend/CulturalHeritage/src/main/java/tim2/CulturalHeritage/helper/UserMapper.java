package tim2.CulturalHeritage.helper;


import tim2.CulturalHeritage.dto.UserResponseDTO;
import tim2.CulturalHeritage.model.AuthenticatedUser;

import java.util.ArrayList;
import java.util.List;

public class UserMapper implements MapperInterface<AuthenticatedUser, UserResponseDTO> {

    @Override
    public AuthenticatedUser toEntity(UserResponseDTO dto) {
        return null;
    }

    @Override
    public UserResponseDTO toDto(AuthenticatedUser entity) {
        return new UserResponseDTO(entity.getId(), entity.getFirstName(),
                entity.getFirstName(), entity.getEmail(), entity.isApproved());
    }

    @Override
    public List<UserResponseDTO> toDtoList(List<AuthenticatedUser> entityList) {
        System.out.println(entityList.size());
        List<UserResponseDTO> usersDTOList = new ArrayList<>();
        for(AuthenticatedUser u: entityList){
            usersDTOList.add(toDto(u));
        }

        return usersDTOList;
    }
}
