package com.example.newswebsite.Entity.Component;

import com.example.newswebsite.Entity.Enums.RoleTypes;
import com.example.newswebsite.Entity.Repository.RoleRepository;
import com.example.newswebsite.Entity.Repository.UserRepository;
import com.example.newswebsite.Entity.Role;
import com.example.newswebsite.Entity.Users;
import com.example.newswebsite.Entity.Utilities.Constanta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.example.newswebsite.Entity.Enums.RoleTypes.*;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Value(value = "${spring.sql.init.mode}")
    String initMode;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("Successfully runner");
        if (initMode.equals("always")){
            RoleTypes[] roleTypes=RoleTypes.values();
            Role adminRole = roleRepository.save(new Role(
                    Constanta.ADMIN,
                    Arrays.asList(roleTypes),
                    "Owner"
            ));
            Role userRole = roleRepository.save(new Role(
                    Constanta.USER,
                    Arrays.asList(ADD_COMMENT, DELETE_MY_COMMENT, EDIT_MY_COMMENT, READ_POST, READ_COMMENT),
                    "User"
            ));
            userRepository.save(new Users(
                    "Admin", "admin", passwordEncoder.encode("admin"), adminRole, true, null
            ));
            userRepository.save(new Users(
                    "User", "user1", passwordEncoder.encode("user1"), userRole, true, null
            ));
        }
    }
}
