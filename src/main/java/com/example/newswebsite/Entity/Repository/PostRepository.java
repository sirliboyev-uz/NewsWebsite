package com.example.newswebsite.Entity.Repository;

import com.example.newswebsite.Entity.Post;
import javafx.geometry.Pos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    boolean existsByUrl(String url);
    boolean existsByUrlAndIdNot(String url, Long id);
    Optional<Post> findByTitleAndCreatedTime(String title, Timestamp createdTime);
}
