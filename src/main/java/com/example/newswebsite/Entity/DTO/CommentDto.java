package com.example.newswebsite.Entity.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentDto {
    @NotNull
    private String text;
    private Long postId;
}