package tim2.CulturalHeritage.helper;


import tim2.CulturalHeritage.dto.UserResponseDTO;
import tim2.CulturalHeritage.model.AuthenticatedUser;

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
}
