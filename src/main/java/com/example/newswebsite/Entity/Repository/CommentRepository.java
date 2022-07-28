package com.example.newswebsite.Entity.Repository;

import com.example.newswebsite.Entity.Comment;
import com.example.newswebsite.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findByTextAndCreatedTime(String text, Timestamp createdTime);
    Optional<Comment> findByPost(Post post);
}
