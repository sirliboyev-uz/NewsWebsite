package com.example.newswebsite.Entity.DTO;

import com.example.newswebsite.Entity.Enums.RoleTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleRegisterDTO {
    @NotBlank
    private String name;
    private String description;
    @NotEmpty
    private List<RoleTypes> roleTypes;
}