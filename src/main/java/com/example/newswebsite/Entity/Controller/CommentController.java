package com.example.newswebsite.Entity.Controller;

import com.example.newswebsite.Entity.DTO.ApiResponse;
import com.example.newswebsite.Entity.DTO.CommentDto;
import com.example.newswebsite.Entity.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Time;
import java.sql.Timestamp;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PreAuthorize("hasAnyAuthority('ADD_COMMENT','ADD_MY_COMMENT')")
    @PostMapping(   "/insert")
    public ResponseEntity<String> insert(@Valid @RequestBody CommentDto dto){
        ApiResponse apiResponse=commentService.addComment(dto);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }

    @PreAuthorize("hasAnyAuthority('EDIT_COMMENT','EDIT_MY_COMMENT')")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,  @Valid @RequestBody CommentDto dto){
        ApiResponse apiResponse=commentService.updateComment(id, dto);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage());
    }

//    @PreAuthorize("hasAnyAuthority('DELETE_COMMENT','DELETE_MY_COMMENT')")
//    @DeleteMapping("/delete/{text}/{time}")
//    public ResponseEntity<String> delete(@PathVariable String text, @PathVariable Timestamp time){
//        ApiResponse apiResponse=commentService.deleteComment(text, time);
//        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage()+"\n"+apiResponse.getObject());
//    }

    @PreAuthorize("hasAnyAuthority('DELETE_COMMENT','DELETE_MY_COMMENT')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        ApiResponse apiResponse=commentService.deleteComment(id);
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage()+"\n"+apiResponse.getObject());
    }

    @PreAuthorize("hasAuthority('READ_COMMENT')")
    @GetMapping("/select")
    public ResponseEntity<String> select(){
        ApiResponse apiResponse=commentService.commentView();
        return ResponseEntity.status(apiResponse.getType()?200:409).body(apiResponse.getMessage()+"\n"+apiResponse.getObject());
    }
}