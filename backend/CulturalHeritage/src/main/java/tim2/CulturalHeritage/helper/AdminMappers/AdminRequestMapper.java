package tim2.CulturalHeritage.helper.AdminMappers;

import tim2.CulturalHeritage.dto.requestDTO.AdminRequestDTO;
import tim2.CulturalHeritage.helper.MapperInterface;
import tim2.CulturalHeritage.model.Admin;

import java.util.ArrayList;
import java.util.List;

public class AdminRequestMapper implements MapperInterface<Admin, AdminRequestDTO> {

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
    public AdminRequestDTO toDto(Admin entity) {
        return new AdminRequestDTO(entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPassword());
    }

    @Override
    public List<AdminRequestDTO> toDtoList(List<Admin> entityList) {
        List<AdminRequestDTO> adminsDTOList = new ArrayList<>();
        for (Admin u : entityList) {
            adminsDTOList.add(toDto(u));
        }

        return adminsDTOList;
    }
}