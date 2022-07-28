package com.example.newswebsite.Entity.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostDto {
    @NotNull
    private String title;
    private String text;
    private String url;
}
