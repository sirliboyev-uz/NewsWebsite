package com.example.newswebsite.Entity.Controller;

import com.example.newswebsite.Entity.DTO.ApiResponse;
import com.example.newswebsite.Entity.DTO.PostDto;
import com.example.newswebsite.Entity.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;

@RestController
@RequestMapping(value = "/post")

public class PostController {
    @Autowired
    PostService postService;

    @PreAuthorize("hasAuthority('ADD_POST')")
    @PostMapping("/insert")
    public ResponseEntity<String> insert(@Valid @RequestBody PostDto dto){
        ApiResponse apiResponse=postService.addPost(dto);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }

    @PreAuthorize("hasAuthority('EDIT_POST')")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,@Valid @RequestBody PostDto dto){
        ApiResponse apiResponse=postService.updatePost(id,dto);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }

    @PreAuthorize("hasAuthority('DELETE_POST')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        ApiResponse apiResponse=postService.deletePost(id);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }

    @PreAuthorize("hasAuthority('READ_POST')")
    @GetMapping("/select/{title}/{timestamp}")
    public ResponseEntity<String> select(@PathVariable String title, @PathVariable Timestamp timestamp){
        ApiResponse apiResponse=postService.postView(title, timestamp);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getType()?apiResponse.getMessage()+"\n"+apiResponse.getObject():apiResponse.getMessage());
    }

    @PreAuthorize("hasAuthority('READ_POST')")
    @GetMapping("/select")
    public ResponseEntity<String> select(){
        ApiResponse apiResponse=postService.postView();
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage()+"\n"+apiResponse.getObject());
    }
}
