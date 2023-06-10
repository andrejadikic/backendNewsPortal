package com.example.backend.model.comment.repository;

import rs.raf.demo.enums.Constants;
import rs.raf.demo.models.Comment;
import rs.raf.demo.models.User;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {
    @Override
    public Comment addOne(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO comments (author, text, articleId, created_at, likeCount, dislikeCount) VALUES (?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, comment.getAuthor());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setInt(3, comment.getArticleId());
            preparedStatement.setLong(4, System.currentTimeMillis());
            preparedStatement.setLong(5, 0L);
            preparedStatement.setLong(6, 0L);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                comment.setId(resultSet.getInt(1));
                comment.setCreated_at(resultSet.getLong(5));
                comment.setLikeCount(0L);
                comment.setDislikeCount(0L);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM comments");
            while (resultSet.next()){
                comments.add(new Comment(resultSet.getInt("id"), resultSet.getString("author"),
                        resultSet.getString("text"), resultSet.getInt("articleId"), resultSet.getLong("created_at"),
                        resultSet.getLong("likeCount"), resultSet.getLong("dislikeCount")));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            this.closeConnection(connection);
            this.closeResultSet(resultSet);
            this.closeStatement(statement);
        }
        return comments;
    }

    @Override
    public List<Comment> getAll(Integer articleId) {
        List<Comment> comments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM comments where articleId = ?");
            preparedStatement.setInt(1, articleId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                comments.add(new Comment(resultSet.getInt("id"), resultSet.getString("author"),
                        resultSet.getString("text"),resultSet.getInt("articleId"), resultSet.getLong("created_at"),
                        resultSet.getLong("likeCount"), resultSet.getLong("dislikeCount")));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comments;
    }

    @Override
    public Comment getOne(Integer id) {
        Comment comment = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM comments where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                comment = new Comment(resultSet.getInt("id"), resultSet.getString("author"),
                        resultSet.getString("text"),resultSet.getInt("articleId"), resultSet.getLong("created_at"),
                        resultSet.getLong("likeCount"), resultSet.getLong("dislikeCount"));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comment;
    }

    @Override
    public void deleteOne(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM comments where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void updateOne(Long count, String likeType, Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = this.newConnection();
            if(likeType.equals(Constants.LikeType.LIKE)){
                preparedStatement = connection.prepareStatement("UPDATE comments SET likeCount = ? WHERE id = ?");
            }else if(likeType.equals(Constants.LikeType.DISLIKE)){
                preparedStatement = connection.prepareStatement("UPDATE comments SET dislikeCount = ? WHERE id = ?");
            }else return;
            preparedStatement.setLong(1, count);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            this.closeConnection(connection);
            this.closeStatement(preparedStatement);
        }
    }
}
