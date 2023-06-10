package com.example.backend.model.comment.repository;

import rs.raf.demo.models.Comment;

import java.util.List;

public interface CommentRepository {
    Comment addOne(Comment comment);
    List<Comment> getAll();
    List<Comment> getAll(Integer articleId);
    Comment getOne(Integer id);
    void deleteOne(Integer id);
    void updateOne(Long count, String likeType, Integer id);
}
