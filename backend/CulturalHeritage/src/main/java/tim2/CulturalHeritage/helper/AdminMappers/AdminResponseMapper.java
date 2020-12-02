package tim2.CulturalHeritage.helper.AdminMappers;

import tim2.CulturalHeritage.dto.responseDTO.AdminResponseDTO;
import tim2.CulturalHeritage.helper.MapperInterface;
import tim2.CulturalHeritage.model.Admin;

import java.util.ArrayList;
import java.util.List;

public class AdminResponseMapper  implements MapperInterface<Admin, AdminResponseDTO> {

    @Override
    public Admin toEntity(AdminResponseDTO dto) {
        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setFirstName(dto.getFirstName());
        admin.setLastName(dto.getLastName());
        admin.setEmail(dto.getEmail());

        return admin;
    }

    @Override
    public AdminResponseDTO toDto(Admin entity) {
        return new AdminResponseDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail());
    }

    @Override
    public List<AdminResponseDTO> toDtoList(List<Admin> entityList) {
        List<AdminResponseDTO> adminsDTOList = new ArrayList<>();
        for(Admin u: entityList){
            adminsDTOList.add(toDto(u));
        }

        return adminsDTOList;
    }
}
