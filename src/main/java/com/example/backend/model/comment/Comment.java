package com.example.backend.model.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Integer id;
    private String author;
    @NotNull(message = "Text field is required")
    @NotEmpty(message = "Text field is required")
    private String text;
    @NotNull(message = "Article id is required")
    private Integer articleId;
    private Long created_at;

    public Comment(Integer id, String author, String text, Integer articleId) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.articleId = articleId;
        this.created_at = System.currentTimeMillis();
    }

}
