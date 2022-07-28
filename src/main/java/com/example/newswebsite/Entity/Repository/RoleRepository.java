package com.example.newswebsite.Entity.Repository;

import com.example.newswebsite.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
    boolean existsByRoleName(String roleName);
    boolean existsByRoleNameAndIdNot(String roleName, Long id);
}
