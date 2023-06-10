package com.example.backend.model.comment;


import com.example.backend.model.comment.Comment;

import java.util.List;

public interface CommentRepository {
    Comment addOne(Comment comment);
    List<Comment> getAll();
    List<Comment> getAll(Integer articleId);
    Comment getOne(Integer id);
    void deleteOne(Integer id);
}
