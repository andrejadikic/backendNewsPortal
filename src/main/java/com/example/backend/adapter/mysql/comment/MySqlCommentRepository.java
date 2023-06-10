package com.example.backend.adapter.mysql.comment;


import com.example.backend.application.MySqlAbstractRepository;
import com.example.backend.model.comment.Comment;
import com.example.backend.model.comment.CommentRepository;

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

            preparedStatement = connection.prepareStatement("INSERT INTO comments (author, text, articleId, created_at) VALUES (?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, comment.getAuthor());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setInt(3, comment.getArticleId());
            preparedStatement.setLong(4, System.currentTimeMillis());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if(resultSet.next()){
                comment.setId(resultSet.getInt(1));
                comment.setCreated_at(resultSet.getLong(5));
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
                        resultSet.getString("text"), resultSet.getInt("articleId"), resultSet.getLong("created_at")));
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
                        resultSet.getString("text"),resultSet.getInt("articleId"), resultSet.getLong("created_at")));
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
                        resultSet.getString("text"),resultSet.getInt("articleId"), resultSet.getLong("created_at"));
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
}
