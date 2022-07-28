package com.example.newswebsite.Entity.Service;

import com.example.newswebsite.Entity.Comment;
import com.example.newswebsite.Entity.DTO.ApiResponse;
import com.example.newswebsite.Entity.DTO.CommentDto;
import com.example.newswebsite.Entity.Enums.RoleTypes;
import com.example.newswebsite.Entity.Post;
import com.example.newswebsite.Entity.Repository.CommentRepository;
import com.example.newswebsite.Entity.Repository.PostRepository;
import com.example.newswebsite.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    public ApiResponse addComment(CommentDto dto) {
        Optional<Post> byId = postRepository.findById(dto.getPostId());
        if(byId.isPresent()) {
            Comment comment = new Comment(dto.getText(), byId.get());
            commentRepository.save(comment);
            return new ApiResponse("Comment added", true);
        }
        return new ApiResponse("No post",false);
    }

    public ApiResponse updateComment(Long id, CommentDto dto) {
        Optional<Comment> byId = commentRepository.findById(id);
        if(byId.isPresent()){
            Comment comment=byId.get();
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            Users user=(Users) authentication.getPrincipal();
            if(user.getId().equals(comment.getCreatedBy())) {
                comment.setText(dto.getText());
                commentRepository.save(comment);
                return new ApiResponse("Updated", true);
            }
            for (GrantedAuthority authority : user.getAuthorities()) {
                String s="EDIT_COMMENT";
                if(authority.getAuthority().equals(s)){
                    comment.setText(dto.getText());
                    commentRepository.save(comment);
                    return new ApiResponse("Update", true);
                }
            }
            return new ApiResponse("No update", false);
        }
        return new ApiResponse("Not comment",false);
    }

//    public ApiResponse deleteComment(String text, Timestamp date) {
//        Optional<Comment> byId = commentRepository.findByTextAndCreatedTime(text,date);
//        if(byId.isPresent()){
//            Comment comment=byId.get();
//            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
//            Users user=(Users) authentication.getPrincipal();
//            if(user.getId().equals(comment.getCreatedBy())) {
//                commentRepository.deleteById(comment.getId());
//                return new ApiResponse("Deleted", true);
//            }
//            for (GrantedAuthority authority : user.getAuthorities()) {
//                String s="DELETE_COMMENT";
//                if(authority.getAuthority().equals(s)){
//                    commentRepository.deleteById(comment.getId());
//                    return new ApiResponse("Deleted", true);
//                }
//            }
//            return new ApiResponse("Not comment delete", false);
//        }
//        return new ApiResponse("No comment",false);
//    }


    public ApiResponse deleteComment(Long id) {
        Optional<Comment> byId = commentRepository.findById(id);
        if(byId.isPresent()){
            Comment comment=byId.get();
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            Users user=(Users) authentication.getPrincipal();
            if(user.getId().equals(comment.getCreatedBy())) {
                commentRepository.deleteById(comment.getId());
                return new ApiResponse("Deleted", true);
            }
            for (GrantedAuthority authority : user.getAuthorities()) {
                String s="DELETE_COMMENT";
                if(authority.getAuthority().equals(s)){
                    commentRepository.deleteById(comment.getId());
                    return new ApiResponse("Deleted", true);
                }
            }
            return new ApiResponse("Not comment delete", false);
        }
        return new ApiResponse("No comment",false);
    }
    public ApiResponse commentView() {
        List<Comment> comments=commentRepository.findAll();
        return new ApiResponse("Comment list",true,comments);
    }
}
