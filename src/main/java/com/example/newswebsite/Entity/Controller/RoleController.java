package com.example.newswebsite.Entity.Controller;

import com.example.newswebsite.Entity.DTO.ApiResponse;
import com.example.newswebsite.Entity.DTO.RoleRegisterDTO;
import com.example.newswebsite.Entity.Service.RoleService;
import com.example.newswebsite.Entity.Utilities.RoleCheckName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

//    @PreAuthorize(value = "hasAuthority('ADD_USER')")
    @RoleCheckName(value = "ADD_ROLE")
    @PostMapping("/registerRole")
    public HttpEntity<?> create(@Valid @RequestBody RoleRegisterDTO roleRegisterDTO){
        ApiResponse apiResponse = roleService.roleRegister(roleRegisterDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }

    @PreAuthorize("hasAuthority('EDIT_ROLE')")
    @PutMapping("/update/{id}")
    public HttpEntity<?> update(@PathVariable Long id,@Valid @RequestBody RoleRegisterDTO roleRegisterDTO){
        ApiResponse apiResponse=roleService.roleUpdate(id, roleRegisterDTO);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }

    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Long id){
        ApiResponse apiResponse=roleService.roleDelete(id);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }

    @PreAuthorize("hasAuthority('READ_ROLE')")
    @GetMapping("/select/{id}")
    public HttpEntity<?> select(@PathVariable Long id){
        ApiResponse apiResponse=roleService.lavozimSelect(id);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getType()?apiResponse.getMessage()+"\n"+apiResponse.getObject():apiResponse.getMessage());
    }

    @PreAuthorize("hasAuthority('READ_ROLE')")
    @GetMapping("/select")
    public HttpEntity<?> select(){
        ApiResponse apiResponse=roleService.lavozimSelect();
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage()+"\n"+apiResponse.getObject());
    }
}