package tim2.CulturalHeritage.helper;

import tim2.CulturalHeritage.dto.requestDTO.AdminRequestDTO;
import tim2.CulturalHeritage.dto.responseDTO.AdminResponseDTO;
import tim2.CulturalHeritage.model.Admin;

import java.util.ArrayList;
import java.util.List;

public class AdminMapper implements MapperInterface<Admin, AdminResponseDTO, AdminRequestDTO> {
    @Override
    public Admin toEntity(AdminRequestDTO dto) {
        Admin admin = new Admin();
        admin.setFirstName(dto.getFirstName());
        admin.setLastName(dto.getLastName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(dto.getPassword());

        return admin;
    }

    @Override
    public AdminResponseDTO toDto(Admin entity) {
        return new AdminResponseDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail());
    }

    @Override
    public List<AdminResponseDTO> toDtoList(List<Admin> entityList) {
        List<AdminResponseDTO> adminsDTOList = new ArrayList<>();
        for (Admin u : entityList) {
            adminsDTOList.add(toDto(u));
        }

        return adminsDTOList;
    }
}
