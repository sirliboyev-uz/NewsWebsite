package com.example.newswebsite.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Valid
public class RegisterDTO {

//    @NotNull(message = "fullName not null")
    private String fullName;

//    @NotNull(message = "username not null")
    private String username;

//    @NotNull(message = "password not null")
    private String password;

//    @NotNull(message = "")
    private String rePassword;
}
