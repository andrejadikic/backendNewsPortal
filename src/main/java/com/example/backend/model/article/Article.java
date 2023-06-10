package com.example.backend.model.article;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    private Integer id;
    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;
    @NotNull(message = "Text field is required")
    @NotEmpty(message = "Text field is required")
    private String text;

    @NotNull(message = "Author field is required")
    @NotEmpty(message = "Author field is required")
    private String author;
    @NotNull(message = "CategoryId is required")
    private Integer categoryId;
    @NotNull(message = "Tags field is required")
    @NotEmpty(message = "Tags field is required")
    private String tags;
    private Long created_At;
    private Long viewCount;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", categoryId=" + categoryId +
                ", tags='" + tags + '\'' +
                ", created_At=" + created_At +
                ", viewCount=" + viewCount +
                '}';
    }
}
