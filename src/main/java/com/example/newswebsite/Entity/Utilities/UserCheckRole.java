package com.example.newswebsite.Entity.Utilities;

import com.example.newswebsite.Entity.Exceptions.RuntimeException;
import com.example.newswebsite.Entity.Users;
import lombok.SneakyThrows;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserCheckRole {

    @Before(value = "@annotation(roleCheckName)")

    public String permissionCheck(RoleCheckName roleCheckName) throws RuntimeException {
        Users users = (Users)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean is=false;
        for (GrantedAuthority authority : users.getAuthorities()) {
            if (authority.getAuthority().equals(roleCheckName.value())){
                is=true;
                break;
            }
        }
        if (!is) {
            throw new RuntimeException("403","Role not found");
        }
        return "aa";
    }
}
