package com.example.newswebsite.Entity.Service;
import com.example.newswebsite.Entity.Comment;
import com.example.newswebsite.Entity.DTO.ApiResponse;
import com.example.newswebsite.Entity.DTO.PostDto;
import com.example.newswebsite.Entity.Post;
import com.example.newswebsite.Entity.Repository.CommentRepository;
import com.example.newswebsite.Entity.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    public ApiResponse addPost(PostDto dto) {
        if(postRepository.existsByUrl(dto.getUrl()))
            return new ApiResponse("Already post",false);
        Post post=new Post(dto.getTitle(),dto.getText(),dto.getUrl());
        postRepository.save(post);
        return new ApiResponse("Saved",true);
    }

    public ApiResponse updatePost(Long id, PostDto dto) {
        Optional<Post> byId = postRepository.findById(id);
        if(byId.isPresent()){
            if(postRepository.existsByUrlAndIdNot(dto.getUrl(),id))
                return new ApiResponse("Already post",false);
            Post post=byId.get();
            post.setTitle(dto.getTitle());
            post.setText(dto.getText());
            post.setUrl(dto.getUrl());
            postRepository.save(post);
            return new ApiResponse("Updated",true);
        }
        return new ApiResponse("Not found post",false);
    }

    public ApiResponse deletePost(Long id) {
        Optional<Post> byId = postRepository.findById(id);
        if(byId.isPresent()){
            for (Comment comment : commentRepository.findAll()) {
                if (comment.getPost().getId().equals(byId.get().getId())) {
                    commentRepository.deleteById(comment.getId());
                }
            }
            postRepository.deleteById(byId.get().getId());
            return new ApiResponse("Deleted",true);
        }
        return new ApiResponse("Not found post",false);
    }

    public ApiResponse postView(String text, Timestamp timestamp) {
        Optional<Post> byId = postRepository.findByTitleAndCreatedTime(text, timestamp);
        return byId.map(post -> new ApiResponse("Post", true, post)).orElseGet(() -> new ApiResponse("Not found post", false));
    }

    public ApiResponse postView() {
        List<Post> posts= postRepository.findAll();
        return new ApiResponse("Post lists",true,posts);
    }
}
