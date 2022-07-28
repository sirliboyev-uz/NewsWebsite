package com.example.newswebsite.Entity.Service;

import com.example.newswebsite.Entity.DTO.ApiResponse;
import com.example.newswebsite.Entity.DTO.RegisterDTO;
import com.example.newswebsite.Entity.DTO.RoleRegisterDTO;
import com.example.newswebsite.Entity.Repository.RoleRepository;
import com.example.newswebsite.Entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public ApiResponse roleRegister(RoleRegisterDTO roleRegisterDTO) {
        if(roleRepository.existsByRoleName(roleRegisterDTO.getName())) return new ApiResponse("Role already registered",false);
        Role role=new Role(
                roleRegisterDTO.getName(),
                roleRegisterDTO.getRoleTypes(),
                roleRegisterDTO.getDescription()
        );
        roleRepository.save(role);
        return new ApiResponse("Role Successfully registered!",true);
    }

    public ApiResponse roleUpdate(Long id, RoleRegisterDTO dto) {
        Optional<Role> optionalRole=roleRepository.findById(id);
        if(optionalRole.isPresent()){
            if(roleRepository.existsByRoleNameAndIdNot(dto.getName(),id))
                return new ApiResponse("Role already registered",false);
            Role role=optionalRole.get();
            role.setRoleName(dto.getName());
            role.setDescription(dto.getDescription());
            role.setRoleTypes(dto.getRoleTypes());
            roleRepository.save(role);
            return new ApiResponse("Successfully updated",true);
        }
        return new ApiResponse("Role not found",false);
    }

    public ApiResponse roleDelete(Long id) {
        Optional<Role> optionalRole=roleRepository.findById(id);
        if(optionalRole.isPresent()){
            roleRepository.deleteById(id);
            return new ApiResponse("Successfully deleted",true);
        }
        return new ApiResponse("Role not found",false);
    }

    public ApiResponse lavozimSelect(Long id) {
        Optional<Role> lavozim=roleRepository.findById(id);
        if(lavozim.isPresent()){
            return new ApiResponse("Success",true,lavozim.get());
        }
        return new ApiResponse("Not found",false);
    }

    public ApiResponse lavozimSelect() {
        List<Role> lavozims=roleRepository.findAll();
        return new ApiResponse("Success",true,lavozims);
    }
}
