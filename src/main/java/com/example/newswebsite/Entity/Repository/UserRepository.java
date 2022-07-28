package com.example.newswebsite.Entity.Repository;

import com.example.newswebsite.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByUsername(String username);
    Optional<Users> findByUsername(String username);
    Optional<Users> findByUsernameAndEmailCode(String username, String emailCode);
}
