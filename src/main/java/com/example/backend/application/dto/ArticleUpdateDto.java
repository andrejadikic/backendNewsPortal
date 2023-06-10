package com.example.backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleUpdateDto {

    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title is required")
    private String title;
    @NotNull(message = "Text is required")
    @NotEmpty(message = "Text is required")
    private String text;
    @NotNull(message = "Author is required")
    @NotEmpty(message = "Author is required")
    private String author;
    @NotNull(message = "Tags are required")
    @NotEmpty(message = "Tags are required")
    private String tags;
    @NotNull(message = "Category is required")
    private Integer categoryId;
}
